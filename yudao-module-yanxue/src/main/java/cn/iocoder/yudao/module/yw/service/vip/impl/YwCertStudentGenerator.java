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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
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
@Slf4j
public class YwCertStudentGenerator {

    private static final String FULL_WIDTH_INDENT = "　　";
    public static final int GENERATE_STATUS_NONE = 0;
    public static final int GENERATE_STATUS_RUNNING = 1;
    public static final int GENERATE_STATUS_SUCCESS = 2;
    public static final int GENERATE_STATUS_FAIL = 3;

    private final Object imageCacheLock = new Object();

    private volatile String cachedTemplateUrl;
    private volatile BufferedImage cachedTemplateImage;
    private volatile String cachedSealUrl;
    private volatile BufferedImage cachedSealImage;
    private volatile String cachedFontPath;
    private volatile Font cachedBaseFont;

    @Value("${yw.cert.student.template-url:classpath:cert/student/student-template.png}")
    private String templateUrl;
    @Value("${yw.cert.student.seal-url:classpath:cert/student/seal.png}")
    private String sealUrl;
    @Value("${yw.cert.student.image-format:png}")
    private String imageFormat;
    @Value("${yw.cert.student.image-url-connect-timeout-ms:3000}")
    private int imageUrlConnectTimeoutMs;
    @Value("${yw.cert.student.image-url-read-timeout-ms:10000}")
    private int imageUrlReadTimeoutMs;
    @Value("${yw.cert.student.template-max-width:2400}")
    private int templateMaxWidth;
    @Value("${yw.cert.student.font-path:${yw.cert.tutor.font-path:}}")
    private String fontPath;
    @Value("${yw.cert.student.font-name:${yw.cert.tutor.font-name:SimHei}}")
    private String fontName;

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

    @EventListener(ApplicationReadyEvent.class)
    public void preloadImages() {
        if (!hasConfig()) {
            return;
        }
        try {
            loadTemplateImage();
            loadSealImage();
            log.info("[preloadImages][学生证书底版和电子章预热成功]");
        } catch (Exception e) {
            log.warn("[preloadImages][学生证书底版或电子章预热失败，首次生成时会再次尝试：{}]", limitMsg(e.getMessage()), e);
        }
    }

    @Async
    public void generateAsync(Long batchId) {
        try {
            generate(batchId);
        } catch (Exception e) {
            YwStudentApplyBatchDO batch = studentApplyBatchMapper.selectById(batchId);
            if (batch != null) {
                batch.setGenerateStatus(GENERATE_STATUS_FAIL);
                batch.setGenerateError(limitMsg(e.getMessage()));
                studentApplyBatchMapper.updateById(batch);
            }
            log.error("[generateAsync][学生证书生成失败，batchId={}]", batchId, e);
        }
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
            batch.setGenerateStatus(GENERATE_STATUS_FAIL);
            batch.setGenerateError("未查询到待生成的学生证书明细");
            studentApplyBatchMapper.updateById(batch);
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
        batch.setGenerateStatus(GENERATE_STATUS_RUNNING);
        batch.setGenerateError(null);
        studentApplyBatchMapper.updateById(batch);

        vipInfo.setTokenBalance(balance.subtract(needToken));
        vipInfoMapper.updateById(vipInfo);

        List<Long> detailIds = new ArrayList<>();
        for (YwStudentApplyDO detail : details) {
            detailIds.add(detail.getId());
        }
        certStudentMapper.deleteByApplyDetailIds(detailIds);

        int certYear = Year.now().getValue();
        int nextNo = resolveNextNo(certYear);
        ByteArrayOutputStream zipBuffer = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(zipBuffer, StandardCharsets.UTF_8)) {
            for (YwStudentApplyDO detail : details) {
                String certNo = buildCertNo(certYear, nextNo++);
                byte[] certBytes = renderCertImage(detail, certNo);
                String fileExtension = getImageFileExtension();
                String certPath = fileApi.createFile(certBytes, certNo + "." + fileExtension, "yw/cert/student", getImageMimeType());
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
                cert.setCourseDate(resolveCourseDate(detail));
                cert.setStampDate(resolveStampDate(detail));
                cert.setStampUnit(detail.getStampUnit());
                cert.setCertImageUrl(certPath);
                cert.setIssueTime(LocalDateTime.now());
                certStudentMapper.insert(cert);

                zipOutputStream.putNextEntry(new ZipEntry(certNo + "-" + safeName(detail.getStudentName()) + "." + fileExtension));
                zipOutputStream.write(certBytes);
                zipOutputStream.closeEntry();
            }
        } catch (Exception e) {
            throw new RuntimeException(limitMsg(e.getMessage()), e);
        }
        String downloadUrl = fileApi.createFile(zipBuffer.toByteArray(), batch.getApplyNo() + ".zip", "yw/cert/student", "application/zip");
        batch.setDownloadUrl(downloadUrl);
        batch.setGenerateStatus(GENERATE_STATUS_SUCCESS);
        batch.setGenerateError(null);
        studentApplyBatchMapper.updateById(batch);
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
            Font plainFont = buildFont(Font.PLAIN, bodyFontSize);
            Font boldFont = buildFont(Font.BOLD, bodyFontSize);
            List<List<TextSegment>> paragraphs = buildBodyParagraphs(detail);
            int lineHeight = Math.max(34, bodyFontSize + 10);
            drawStyledParagraphs(g, paragraphs, plainFont, boldFont,
                    (int) (width * 0.17), (int) (height * 0.47), (int) (width * 0.54), lineHeight, 18);

            g.setFont(buildFont(Font.PLAIN, Math.max(16, width / 60)));
            g.drawString("证书编号：" + certNo, (int) (width * 0.12), (int) (height * 0.78));

            g.setFont(buildFont(Font.PLAIN, Math.max(16, width / 68)));
            String stampUnit = StringUtils.hasText(detail.getStampUnit()) ? detail.getStampUnit() : detail.getCourseProvider();
            if (StringUtils.hasText(stampUnit)) {
                drawCentered(g, stampUnit, (int) (width * 0.76), height * 0.75);
            }
            LocalDate stampDate = resolveStampDate(detail);
            if (stampDate != null) {
                drawCentered(g, stampDate.format(DateTimeFormatter.ofPattern("yyyy年M月d日")), (int) (width * 0.76), height * 0.79);
            }
            g.drawImage(seal, (int) (width * 0.685), (int) (height * 0.64), (int) (width * 0.14), (int) (width * 0.14), null);
        } finally {
            g.dispose();
        }
        return writeImage(template);
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
                cachedTemplateImage = resizeTemplateIfNecessary(readImage(templateUrl));
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

    private BufferedImage resizeTemplateIfNecessary(BufferedImage image) {
        if (templateMaxWidth <= 0 || image.getWidth() <= templateMaxWidth) {
            return image;
        }
        int targetWidth = templateMaxWidth;
        int targetHeight = (int) Math.round(image.getHeight() * (targetWidth * 1.0 / image.getWidth()));
        BufferedImage resized = new BufferedImage(targetWidth, targetHeight, image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType());
        Graphics2D g = resized.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        } finally {
            g.dispose();
        }
        log.info("[resizeTemplateIfNecessary][学生证书底版从 {}x{} 缩放到 {}x{}]",
                image.getWidth(), image.getHeight(), targetWidth, targetHeight);
        return resized;
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
        first.add(new TextSegment(formatCourseDate(detail), true, true));
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

    private BufferedImage readImage(String path) throws Exception {
        try (InputStream inputStream = openStream(path)) {
            BufferedImage image = ImageIO.read(inputStream);
            if (image == null) {
                throw new IllegalArgumentException("图片读取失败：" + path);
            }
            return image;
        }
    }

    private InputStream openStream(String path) throws Exception {
        if (path.startsWith("http://") || path.startsWith("https://") || path.startsWith("file://")) {
            URLConnection connection = new URL(path).openConnection();
            connection.setConnectTimeout(imageUrlConnectTimeoutMs);
            connection.setReadTimeout(imageUrlReadTimeoutMs);
            return connection.getInputStream();
        }
        if (path.startsWith("classpath:")) {
            return new ClassPathResource(path.substring("classpath:".length())).getInputStream();
        }
        return new FileInputStream(path);
    }

    private Font buildFont(int style, int size) {
        try {
            Font baseFont = loadBaseFont();
            if (baseFont != null) {
                return baseFont.deriveFont(style, (float) size);
            }
        } catch (Exception ignored) {
        }
        return new Font(fontName, style, size);
    }

    private Font loadBaseFont() throws Exception {
        if (!StringUtils.hasText(fontPath)) {
            return null;
        }
        Font font = cachedBaseFont;
        if (font != null && fontPath.equals(cachedFontPath)) {
            return font;
        }
        synchronized (imageCacheLock) {
            if (cachedBaseFont == null || !fontPath.equals(cachedFontPath)) {
                try (InputStream inputStream = openStream(fontPath)) {
                    cachedBaseFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
                    GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(cachedBaseFont);
                    cachedFontPath = fontPath;
                }
            }
            return cachedBaseFont;
        }
    }

    private byte[] writeImage(BufferedImage image) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage outputImage = image;
        if ("jpg".equals(getImageWriteFormat()) && image.getColorModel().hasAlpha()) {
            outputImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = outputImage.createGraphics();
            try {
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, image.getWidth(), image.getHeight());
                g.drawImage(image, 0, 0, null);
            } finally {
                g.dispose();
            }
        }
        if (!ImageIO.write(outputImage, getImageWriteFormat(), outputStream)) {
            throw new IllegalArgumentException("不支持的证书图片格式：" + imageFormat);
        }
        return outputStream.toByteArray();
    }

    private String getImageWriteFormat() {
        String format = StringUtils.hasText(imageFormat) ? imageFormat.trim().toLowerCase() : "png";
        if ("jpg".equals(format) || "jpeg".equals(format)) {
            return "jpg";
        }
        return "png";
    }

    private String getImageFileExtension() {
        return "jpg".equals(getImageWriteFormat()) ? "jpg" : "png";
    }

    private String getImageMimeType() {
        return "jpg".equals(getImageWriteFormat()) ? "image/jpeg" : "image/png";
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

    private String formatCourseDate(YwStudentApplyDO detail) {
        return formatCertDate(resolveCourseDate(detail));
    }

    private LocalDate resolveCourseDate(YwStudentApplyDO detail) {
        return detail.getCourseDate() != null ? detail.getCourseDate() : detail.getCertDate();
    }

    private LocalDate resolveStampDate(YwStudentApplyDO detail) {
        return detail.getStampDate() != null ? detail.getStampDate() : detail.getCertDate();
    }

    private String limitMsg(String msg) {
        if (!StringUtils.hasText(msg)) {
            return "证书生成失败";
        }
        return msg.length() > 500 ? msg.substring(0, 500) : msg;
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
