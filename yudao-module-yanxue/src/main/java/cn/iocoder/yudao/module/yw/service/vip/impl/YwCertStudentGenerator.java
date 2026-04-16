package cn.iocoder.yudao.module.yw.service.vip.impl;

import cn.iocoder.yudao.module.infra.api.file.FileApi;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwCertStudentDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwStudentApplyBatchDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwStudentApplyDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoDO;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwCertStudentMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwStudentApplyBatchMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwStudentApplyMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwVipInfoMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_TEMPLATE_NOT_CONFIG;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_TOKEN_NOT_ENOUGH;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIPINFO_NOT_EXISTS;

@Component
public class YwCertStudentGenerator {

    private static final String FULL_WIDTH_INDENT = "　　";
    private final Object imageCacheLock = new Object();

    private volatile String cachedTemplateUrl;
    private volatile BufferedImage cachedTemplateImage;
    private volatile String cachedSealUrl;
    private volatile BufferedImage cachedSealImage;

    @Value("${yw.cert.student.template-url:https://gdyx-edu-winxapp-resource.oss-cn-guangzhou.aliyuncs.com/yanxue2026/%E8%AF%81%E4%B9%A6%E6%A8%A1%E7%89%88.png}")
    private String templateUrl;
    @Value("${yw.cert.student.seal-url:https://gdyx-edu-winxapp-resource.oss-cn-guangzhou.aliyuncs.com/yanxue2026/%E7%94%B5%E5%AD%90%E7%AB%A0.png}")
    private String sealUrl;

    @Resource
    private YwStudentApplyBatchMapper studentApplyBatchMapper;
    @Resource
    private YwStudentApplyMapper studentApplyMapper;
    @Resource
    private YwCertStudentMapper certStudentMapper;
    @Resource
    private YwVipInfoMapper vipInfoMapper;
    @Resource
    private FileApi fileApi;

    public boolean hasConfig() {
        return StringUtils.hasText(templateUrl) && StringUtils.hasText(sealUrl);
    }

    public void generate(Long batchId) {
        if (!hasConfig()) {
            throw exception(YW_CERT_STUDENT_APPLY_TEMPLATE_NOT_CONFIG);
        }
        YwStudentApplyBatchDO batch = studentApplyBatchMapper.selectById(batchId);
        if (batch == null) {
            return;
        }
        List<YwStudentApplyDO> details = studentApplyMapper.selectListByBatchId(batchId);
        if (details.isEmpty()) {
            return;
        }
        YwVipInfoDO vipInfo = vipInfoMapper.selectById(batch.getVipinfoId());
        if (vipInfo == null) {
            throw exception(YW_VIPINFO_NOT_EXISTS);
        }
        BigDecimal balance = vipInfo.getTokenBalance() == null ? BigDecimal.ZERO : vipInfo.getTokenBalance();
        BigDecimal needToken = BigDecimal.valueOf(details.size());
        if (balance.compareTo(needToken) < 0) {
            throw exception(YW_CERT_STUDENT_APPLY_TOKEN_NOT_ENOUGH);
        }
        vipInfo.setTokenBalance(balance.subtract(needToken));
        vipInfoMapper.updateById(vipInfo);

        List<Long> detailIds = new ArrayList<>();
        for (YwStudentApplyDO detail : details) {
            detailIds.add(detail.getId());
        }
        certStudentMapper.deleteByApplyDetailIds(detailIds);

        List<ZipFileItem> zipItems = new ArrayList<>();
        int certYear = Year.now().getValue();
        int nextNo = resolveNextNo(certYear);
        for (YwStudentApplyDO detail : details) {
            String certNo = buildCertNo(certYear, nextNo++);
            try {
                byte[] certBytes = renderCertImage(detail, certNo);
                String certPath = fileApi.createFile(certBytes, certNo + ".png", "yw/cert/student", "image/png");
                YwCertStudentDO cert = new YwCertStudentDO();
                cert.setApplyDetailId(detail.getId());
                cert.setUserId(detail.getUserId());
                cert.setVipinfoId(detail.getVipinfoId());
                cert.setCertYear(certYear);
                cert.setCertNo(certNo);
                cert.setStudentName(detail.getStudentName());
                cert.setIdCard(detail.getIdCard());
                cert.setSchoolName(detail.getSchoolName());
                cert.setClassName(detail.getClassName());
                cert.setCourseName(detail.getCourseName());
                cert.setCourseHours(detail.getCourseHours());
                cert.setCourseProvider(detail.getCourseProvider());
                cert.setCertDate(detail.getCertDate());
                cert.setStampUnit(detail.getStampUnit());
                cert.setCertImageUrl(certPath);
                cert.setIssueTime(LocalDateTime.now());
                certStudentMapper.insert(cert);
                zipItems.add(new ZipFileItem(certNo + "-" + safeName(detail.getStudentName()) + ".png", certBytes));
            } catch (Exception e) {
                throw new RuntimeException(limitMsg(e.getMessage()), e);
            }
        }
        if (!zipItems.isEmpty()) {
            try {
                byte[] zipBytes = buildZip(zipItems);
                String downloadUrl = fileApi.createFile(zipBytes, batch.getApplyNo() + ".zip", "yw/cert/student", "application/zip");
                batch.setDownloadUrl(downloadUrl);
                studentApplyBatchMapper.updateById(batch);
            } catch (Exception e) {
                throw new RuntimeException(limitMsg(e.getMessage()), e);
            }
        }
    }

    private int resolveNextNo(int certYear) {
        YwCertStudentDO latest = certStudentMapper.selectLatestByYear(certYear);
        if (latest == null || !StringUtils.hasText(latest.getCertNo())) {
            return 1;
        }
        String certNo = latest.getCertNo();
        return Integer.parseInt(certNo.substring(("CCPST" + certYear).length())) + 1;
    }

    private String buildCertNo(int certYear, int no) {
        return "CCPST" + certYear + String.format("%06d", no);
    }

    private byte[] renderCertImage(YwStudentApplyDO detail, String certNo) throws Exception {
        BufferedImage template = deepCopy(loadTemplateImage());
        BufferedImage seal = loadSealImage();
        Graphics2D g = template.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = template.getWidth();
            int height = template.getHeight();

            g.setColor(new Color(45, 45, 45));
            int bodyFontSize = Math.max(20, width / 48);
            Font plainFont = new Font("SimHei", Font.PLAIN, bodyFontSize);
            Font boldFont = new Font("SimHei", Font.BOLD, bodyFontSize);
            List<List<TextSegment>> paragraphs = buildBodyParagraphs(detail);
            int lineHeight = Math.max(34, bodyFontSize + 10);
            drawStyledParagraphs(g, paragraphs, plainFont, boldFont,
                    (int) (width * 0.17), (int) (height * 0.47), (int) (width * 0.54), lineHeight, 18);

            g.setFont(new Font("Monospaced", Font.PLAIN, Math.max(16, width / 60)));
            g.drawString("证书编号：" + certNo, (int) (width * 0.12), (int) (height * 0.78));

            g.setFont(new Font("Serif", Font.PLAIN, Math.max(16, width / 68)));
            String stampUnit = StringUtils.hasText(detail.getStampUnit()) ? detail.getStampUnit() : detail.getCourseProvider();
            if (StringUtils.hasText(stampUnit)) {
                drawCentered(g, stampUnit, (int) (width * 0.76), height * 0.75);
            }
            LocalDate certDate = detail.getCertDate();
            if (certDate != null) {
                drawCentered(g, certDate.format(DateTimeFormatter.ofPattern("yyyy年M月d日")), (int) (width * 0.76), height * 0.79);
            }
            g.drawImage(seal, (int) (width * 0.685), (int) (height * 0.64), (int) (width * 0.14), (int) (width * 0.14), null);
        } finally {
            g.dispose();
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(template, "png", outputStream);
        return outputStream.toByteArray();
    }

    private void drawStyledParagraphs(Graphics2D g, List<List<TextSegment>> paragraphs, Font plainFont, Font boldFont,
                                      int startX, int startY, int maxWidth, int lineHeight, int paragraphSpacing) {
        int currentY = startY;
        for (List<TextSegment> paragraph : paragraphs) {
            List<StyledLine> lines = wrapStyledSegments(g, paragraph, plainFont, boldFont, maxWidth);
            for (StyledLine line : lines) {
                drawStyledLine(g, line, plainFont, boldFont, startX, currentY);
                currentY += lineHeight;
            }
            currentY += paragraphSpacing;
        }
    }

    private List<StyledLine> wrapStyledSegments(Graphics2D g, List<TextSegment> segments, Font plainFont, Font boldFont, int maxWidth) {
        List<StyledLine> lines = new ArrayList<>();
        StyledLine currentLine = new StyledLine();
        int currentWidth = 0;
        for (TextSegment segment : segments) {
            for (int i = 0; i < segment.text.length(); i++) {
                String ch = String.valueOf(segment.text.charAt(i));
                Font font = segment.bold ? boldFont : plainFont;
                int charWidth = g.getFontMetrics(font).stringWidth(ch);
                if (currentWidth + charWidth > maxWidth && !currentLine.segments.isEmpty()) {
                    lines.add(currentLine);
                    currentLine = new StyledLine();
                    currentWidth = 0;
                }
                currentLine.append(ch, segment.bold, segment.underline);
                currentWidth += charWidth;
            }
        }
        if (!currentLine.segments.isEmpty()) {
            lines.add(currentLine);
        }
        return lines;
    }

    private void drawStyledLine(Graphics2D g, StyledLine line, Font plainFont, Font boldFont, int startX, int baselineY) {
        int currentX = startX;
        for (TextSegment segment : line.segments) {
            Font font = segment.bold ? boldFont : plainFont;
            g.setFont(font);
            g.drawString(segment.text, currentX, baselineY);
            int textWidth = g.getFontMetrics(font).stringWidth(segment.text);
            if (segment.underline) {
                int underlineY = baselineY + 4;
                g.drawLine(currentX, underlineY, currentX + textWidth, underlineY);
            }
            currentX += textWidth;
        }
    }

    private BufferedImage loadTemplateImage() throws Exception {
        BufferedImage image = cachedTemplateImage;
        if (image != null && templateUrl.equals(cachedTemplateUrl)) {
            return image;
        }
        synchronized (imageCacheLock) {
            if (cachedTemplateImage == null || !templateUrl.equals(cachedTemplateUrl)) {
                cachedTemplateImage = readImage(templateUrl);
                cachedTemplateUrl = templateUrl;
            }
            return cachedTemplateImage;
        }
    }

    private BufferedImage loadSealImage() throws Exception {
        BufferedImage image = cachedSealImage;
        if (image != null && sealUrl.equals(cachedSealUrl)) {
            return image;
        }
        synchronized (imageCacheLock) {
            if (cachedSealImage == null || !sealUrl.equals(cachedSealUrl)) {
                cachedSealImage = readImage(sealUrl);
                cachedSealUrl = sealUrl;
            }
            return cachedSealImage;
        }
    }

    private BufferedImage deepCopy(BufferedImage source) {
        ColorModel colorModel = source.getColorModel();
        boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
        WritableRaster raster = source.copyData(null);
        return new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
    }

    private void drawCentered(Graphics2D g, String text, double centerX, double y) {
        int width = g.getFontMetrics().stringWidth(text);
        g.drawString(text, (int) centerX - width / 2, (int) y);
    }

    private List<List<TextSegment>> buildBodyParagraphs(YwStudentApplyDO detail) {
        List<List<TextSegment>> paragraphs = new ArrayList<>();
        List<TextSegment> first = new ArrayList<>();
        first.add(new TextSegment(FULL_WIDTH_INDENT + "兹有 ", false, false));
        first.add(new TextSegment(defaultText(detail.getStudentName(), "学生"), true, true));
        first.add(new TextSegment("，", false, false));
        first.add(new TextSegment(defaultText(detail.getSchoolName(), "学校"), true, true));
        first.add(new TextSegment(" 学生，于", false, false));
        first.add(new TextSegment(formatCertDate(detail.getCertDate()), true, true));
        first.add(new TextSegment(" 参加研学认定课程", false, false));
        first.add(new TextSegment(defaultText(detail.getCourseName(), "研学课程"), true, true));
        first.add(new TextSegment(" 实践活动，", false, false));
        first.add(new TextSegment("共计", false, false));
        first.add(new TextSegment(defaultText(detail.getCourseHours(), "0"), true, true));
        first.add(new TextSegment("课时。", false, false));
        paragraphs.add(first);

        List<TextSegment> second = new ArrayList<>();
        second.add(new TextSegment(FULL_WIDTH_INDENT + "特发此证。", false, false));
        paragraphs.add(second);
        return paragraphs;
    }

    private byte[] buildZip(List<ZipFileItem> items) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream, StandardCharsets.UTF_8)) {
            for (ZipFileItem item : items) {
                zipOutputStream.putNextEntry(new ZipEntry(item.fileName));
                zipOutputStream.write(item.content);
                zipOutputStream.closeEntry();
            }
        }
        return outputStream.toByteArray();
    }

    private BufferedImage readImage(String path) throws Exception {
        try (InputStream inputStream = openStream(path)) {
            return ImageIO.read(inputStream);
        }
    }

    private InputStream openStream(String path) throws Exception {
        if (path.startsWith("http://") || path.startsWith("https://") || path.startsWith("file://")) {
            return new URL(path).openStream();
        }
        return new FileInputStream(path);
    }

    private String safeName(String text) {
        if (!StringUtils.hasText(text)) {
            return "student";
        }
        return text.replaceAll("[\\\\/:*?\"<>|]", "_");
    }

    private String defaultText(String value, String defaultValue) {
        return StringUtils.hasText(value) ? value : defaultValue;
    }

    private String formatCertDate(LocalDate certDate) {
        return certDate == null ? "指定日期" : certDate.format(DateTimeFormatter.ofPattern("yyyy年M月d日"));
    }

    private String limitMsg(String msg) {
        if (!StringUtils.hasText(msg)) {
            return "证书生成失败";
        }
        return msg.length() > 500 ? msg.substring(0, 500) : msg;
    }

    private static class ZipFileItem {
        private final String fileName;
        private final byte[] content;

        private ZipFileItem(String fileName, byte[] content) {
            this.fileName = fileName;
            this.content = content;
        }
    }

    private static class TextSegment {
        private final String text;
        private final boolean bold;
        private final boolean underline;

        private TextSegment(String text, boolean bold, boolean underline) {
            this.text = text;
            this.bold = bold;
            this.underline = underline;
        }
    }

    private static class StyledLine {
        private final List<TextSegment> segments = new ArrayList<>();

        private void append(String text, boolean bold, boolean underline) {
            if (segments.isEmpty()) {
                segments.add(new TextSegment(text, bold, underline));
                return;
            }
            TextSegment last = segments.get(segments.size() - 1);
            if (last.bold == bold && last.underline == underline) {
                segments.set(segments.size() - 1, new TextSegment(last.text + text, bold, underline));
                return;
            }
            segments.add(new TextSegment(text, bold, underline));
        }
    }
}
