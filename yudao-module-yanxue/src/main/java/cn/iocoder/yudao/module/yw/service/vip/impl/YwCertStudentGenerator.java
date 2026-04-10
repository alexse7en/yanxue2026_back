package cn.iocoder.yudao.module.yw.service.vip.impl;

import cn.iocoder.yudao.module.infra.api.file.FileApi;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwCertStudentApplyDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwCertStudentDO;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwCertStudentApplyMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwCertStudentMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class YwCertStudentGenerator {

    private static final String CERT_NAME = "研学旅行实践活动证书";

    @Value("${yw.cert.student.template-url:}")
    private String templateUrl;
    @Value("${yw.cert.student.seal-url:}")
    private String sealUrl;

    @Resource
    private YwCertStudentApplyMapper certStudentApplyMapper;
    @Resource
    private YwCertStudentMapper certStudentMapper;
    @Resource
    private FileApi fileApi;

    public boolean hasConfig() {
        return StringUtils.hasText(templateUrl) && StringUtils.hasText(sealUrl);
    }

    @Async
    public void generateAsync(Long applyId) {
        YwCertStudentApplyDO apply = certStudentApplyMapper.selectById(applyId);
        if (apply == null) {
            return;
        }
        List<YwCertStudentDO> details = certStudentMapper.selectListByUserAndFilePath(apply.getUserId(), apply.getFilePath());
        if (details.isEmpty()) {
            apply.setCertStatus(3);
            apply.setParseError("未找到可生成的学生证书明细");
            apply.setFinishTime(LocalDateTime.now());
            certStudentApplyMapper.updateById(apply);
            return;
        }

        List<ZipFileItem> zipItems = new ArrayList<>();
        YwCertStudentDO firstSuccess = null;
        int successCount = 0;
        for (YwCertStudentDO detail : details) {
            try {
                String certNo = buildCertNo(detail.getId());
                byte[] certBytes = renderCertImage(detail, certNo);
                String certPath = fileApi.createFile(certBytes, certNo + ".png", "yw/cert/student", "image/png");
                detail.setCertNo(certNo);
                detail.setCertImageUrl(certPath);
                detail.setStatus(1);
                detail.setErrorMsg(null);
                certStudentMapper.updateById(detail);
                zipItems.add(new ZipFileItem(certNo + "-" + safeName(detail.getStudentName()) + ".png", certBytes));
                successCount++;
                if (firstSuccess == null) {
                    firstSuccess = detail;
                }
            } catch (Exception e) {
                detail.setStatus(2);
                detail.setErrorMsg(limitMsg(e.getMessage()));
                certStudentMapper.updateById(detail);
            }
        }

        apply.setCertName(CERT_NAME);
        apply.setFinishTime(LocalDateTime.now());
        if (firstSuccess != null) {
            apply.setCertNo(firstSuccess.getCertNo());
            apply.setCertUrl(firstSuccess.getCertImageUrl());
        }
        if (!zipItems.isEmpty()) {
            try {
                byte[] zipBytes = buildZip(zipItems);
                String downloadUrl = fileApi.createFile(zipBytes, apply.getApplyNo() + ".zip", "yw/cert/student", "application/zip");
                apply.setDownloadUrl(downloadUrl);
            } catch (Exception e) {
                apply.setParseError(limitMsg(e.getMessage()));
            }
        }
        apply.setCertStatus(successCount == details.size() ? 2 : 3);
        certStudentApplyMapper.updateById(apply);
    }

    private byte[] renderCertImage(YwCertStudentDO detail, String certNo) throws Exception {
        BufferedImage template = readImage(templateUrl);
        BufferedImage seal = readImage(sealUrl);
        Graphics2D g = template.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = template.getWidth();
            int height = template.getHeight();

            g.setColor(Color.WHITE);
            g.setFont(new Font("Serif", Font.BOLD, Math.max(24, width / 24)));
            drawCentered(g, CERT_NAME, width / 2, height * 0.26);

            g.setColor(new Color(45, 45, 45));
            g.setFont(new Font("Serif", Font.BOLD, Math.max(26, width / 30)));
            String body = buildBody(detail);
            drawParagraph(g, body, (int) (width * 0.18), (int) (height * 0.44), (int) (width * 0.58), height / 16);

            g.setFont(new Font("Monospaced", Font.PLAIN, Math.max(18, width / 55)));
            g.drawString("证书编号：" + certNo, (int) (width * 0.12), (int) (height * 0.78));

            g.setFont(new Font("Serif", Font.PLAIN, Math.max(18, width / 60)));
            String stampUnit = StringUtils.hasText(detail.getStampUnit()) ? detail.getStampUnit() : detail.getCourseProvider();
            if (StringUtils.hasText(stampUnit)) {
                drawCentered(g, stampUnit, (int) (width * 0.78), height * 0.75);
            }
            LocalDate certDate = detail.getCertDate();
            if (certDate != null) {
                drawCentered(g, certDate.format(DateTimeFormatter.ofPattern("yyyy年M月d日")), (int) (width * 0.78), height * 0.79);
            }
            g.drawImage(seal, (int) (width * 0.70), (int) (height * 0.63), (int) (width * 0.16), (int) (width * 0.16), null);
        } finally {
            g.dispose();
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(template, "png", outputStream);
        return outputStream.toByteArray();
    }

    private void drawParagraph(Graphics2D g, String text, int x, int y, int maxWidth, int lineHeight) {
        List<String> lines = wrapText(g, text, maxWidth);
        int currentY = y;
        for (String line : lines) {
            g.drawString(line, x, currentY);
            currentY += lineHeight;
        }
    }

    private List<String> wrapText(Graphics2D g, String text, int maxWidth) {
        List<String> lines = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            current.append(text.charAt(i));
            if (g.getFontMetrics().stringWidth(current.toString()) > maxWidth) {
                current.deleteCharAt(current.length() - 1);
                lines.add(current.toString());
                current = new StringBuilder().append(text.charAt(i));
            }
        }
        if (current.length() > 0) {
            lines.add(current.toString());
        }
        return lines;
    }

    private void drawCentered(Graphics2D g, String text, double centerX, double y) {
        int width = g.getFontMetrics().stringWidth(text);
        g.drawString(text, (int) centerX - width / 2, (int) y);
    }

    private String buildBody(YwCertStudentDO detail) {
        StringBuilder builder = new StringBuilder("兹有 ");
        builder.append(defaultText(detail.getStudentName(), "学生"));
        if (StringUtils.hasText(detail.getSchoolName())) {
            builder.append("，").append(detail.getSchoolName());
        }
        builder.append(" 学生");
        if (detail.getCertDate() != null) {
            builder.append("，于").append(detail.getCertDate().format(DateTimeFormatter.ofPattern("yyyy年M月d日")));
        }
        builder.append(" 参加");
        builder.append(defaultText(detail.getCourseName(), "研学课程"));
        builder.append(" 实践活动");
        if (StringUtils.hasText(detail.getCourseHours())) {
            builder.append("，共计").append(detail.getCourseHours()).append("课时");
        }
        builder.append("，特发此证。");
        return builder.toString();
    }

    private String buildCertNo(Long detailId) {
        return "YXCERT" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + detailId;
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
}
