package cn.iocoder.yudao.module.yw.service.impl;

import cn.iocoder.yudao.module.infra.api.file.FileApi;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwTutorCertDO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class YwTutorCertImageGenerator {

    private final Object imageCacheLock = new Object();

    private volatile String cachedTemplatePath;
    private volatile BufferedImage cachedTemplateImage;
    private volatile String cachedSealPath;
    private volatile BufferedImage cachedSealImage;

    @Value("${yw.cert.tutor.template-path:imgs/cert-tutor.png}")
    private String templatePath;
    @Value("${yw.cert.student.seal-url:https://gdyx-edu-winxapp-resource.oss-cn-guangzhou.aliyuncs.com/yanxue2026/%E7%94%B5%E5%AD%90%E7%AB%A0.png}")
    private String sealPath;
    @Value("${yw.cert.tutor.avatar-base-path:/www/taotao/StudyTravelSystem/upload/avatar}")
    private String avatarBasePath;

    @Resource
    private FileApi fileApi;

    public String generateAndUpload(YwTutorCertDO cert) {
        try {
            byte[] imageBytes = render(cert);
            return fileApi.createFile(imageBytes, safeName(cert.getCertificateNo()) + ".png", "yw/cert/tutor", "image/png");
        } catch (Exception e) {
            throw new RuntimeException(limitMsg(e.getMessage()), e);
        }
    }

    private byte[] render(YwTutorCertDO cert) throws Exception {
        BufferedImage image = deepCopy(loadTemplateImage());
        Graphics2D g = image.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int width = image.getWidth();
            int height = image.getHeight();

            drawPhoto(g, cert, width, height);
            drawTextFields(g, cert, width, height);
            drawQrCode(g, cert, width, height);
            drawSeal(g, width, height);
        } finally {
            g.dispose();
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        return outputStream.toByteArray();
    }

    private void drawTextFields(Graphics2D g, YwTutorCertDO cert, int width, int height) {
        g.setColor(Color.BLACK);
        Font valueFont = new Font("SimHei", Font.BOLD, Math.max(34, width / 48));
        Font dateFont = new Font("SimHei", Font.BOLD, Math.max(30, width / 58));
        g.setFont(valueFont);

        drawCentered(g, defaultText(cert.getName()), width * 0.366, height * 0.574);
        g.drawString(maskIdCardForImage(cert.getPeopleId()), (int) (width * 0.245), (int) (height * 0.632));
        g.drawString(defaultText(StringUtils.hasText(cert.getPost()) ? cert.getPost() : cert.getGrade()),
                (int) (width * 0.245), (int) (height * 0.658));
        g.drawString(defaultText(cert.getCertificateNo()), (int) (width * 0.245), (int) (height * 0.705));

        g.setFont(dateFont);
        g.drawString(formatIssueDate(cert), (int) (width * 0.683), (int) (height * 0.645));
        g.drawString(defaultText(cert.getEffectiveData()), (int) (width * 0.683), (int) (height * 0.692));
    }

    private void drawPhoto(Graphics2D g, YwTutorCertDO cert, int width, int height) {
        BufferedImage photo = readOptionalImage(resolveAvatarPath(cert.getAvatar()));
        if (photo == null) {
            return;
        }
        int x = (int) (width * 0.229);
        int y = (int) (height * 0.292);
        int targetWidth = (int) (width * 0.128);
        int targetHeight = (int) (height * 0.236);
        g.drawImage(photo.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH), x, y, targetWidth, targetHeight, null);
    }

    private void drawQrCode(Graphics2D g, YwTutorCertDO cert, int width, int height) {
        BufferedImage qrCode = readOptionalImage(cert.getQrCode());
        if (qrCode == null) {
            return;
        }
        int x = (int) (width * 0.140);
        int y = (int) (height * 0.771);
        int size = (int) (width * 0.092);
        g.drawImage(qrCode.getScaledInstance(size, size, Image.SCALE_SMOOTH), x, y, size, size, null);
    }

    private void drawSeal(Graphics2D g, int width, int height) throws Exception {
        BufferedImage seal = loadSealImage();
        int size = (int) (width * 0.225);
        int x = (int) (width * 0.663);
        int y = (int) (height * 0.493);
        g.drawImage(seal.getScaledInstance(size, size, Image.SCALE_SMOOTH), x, y, size, size, null);
    }

    private BufferedImage loadTemplateImage() throws Exception {
        BufferedImage image = cachedTemplateImage;
        if (image != null && templatePath.equals(cachedTemplatePath)) {
            return image;
        }
        synchronized (imageCacheLock) {
            if (cachedTemplateImage == null || !templatePath.equals(cachedTemplatePath)) {
                cachedTemplateImage = readImage(templatePath);
                cachedTemplatePath = templatePath;
            }
            return cachedTemplateImage;
        }
    }

    private BufferedImage loadSealImage() throws Exception {
        BufferedImage image = cachedSealImage;
        if (image != null && sealPath.equals(cachedSealPath)) {
            return image;
        }
        synchronized (imageCacheLock) {
            if (cachedSealImage == null || !sealPath.equals(cachedSealPath)) {
                cachedSealImage = readImage(sealPath);
                cachedSealPath = sealPath;
            }
            return cachedSealImage;
        }
    }

    private BufferedImage readImage(String path) throws Exception {
        try (InputStream inputStream = openStream(path)) {
            return ImageIO.read(inputStream);
        }
    }

    private BufferedImage readOptionalImage(String path) {
        if (!StringUtils.hasText(path)) {
            return null;
        }
        try {
            return readImage(path);
        } catch (Exception ignored) {
            return null;
        }
    }

    private InputStream openStream(String path) throws Exception {
        if (path.startsWith("http://") || path.startsWith("https://") || path.startsWith("file://")) {
            return new URL(path).openStream();
        }
        if (path.startsWith("classpath:")) {
            return new ClassPathResource(path.substring("classpath:".length())).getInputStream();
        }
        File file = new File(path);
        if (!file.isAbsolute()) {
            file = new File(System.getProperty("user.dir"), path);
        }
        if (!file.exists() && "imgs/cert-tutor.png".equals(path)) {
            file = new File("D:/IdeaProject/yanxue2026_admin/src/assets/imgs/cert-tutor.png");
        }
        return new FileInputStream(file);
    }

    private String resolveAvatarPath(String avatar) {
        if (!StringUtils.hasText(avatar)) {
            return null;
        }
        if (avatar.startsWith("http://") || avatar.startsWith("https://") || avatar.startsWith("file://")) {
            return avatar;
        }
        File avatarFile = new File(avatar);
        if (avatarFile.isAbsolute()) {
            return avatarFile.getAbsolutePath();
        }
        return new File(avatarBasePath, avatar).getAbsolutePath();
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

    private String maskIdCardForImage(String idCard) {
        if (!StringUtils.hasText(idCard) || idCard.length() <= 10) {
            return defaultText(idCard);
        }
        return idCard.substring(0, 4) + repeat("X", idCard.length() - 10) + idCard.substring(idCard.length() - 6);
    }

    private String formatIssueDate(YwTutorCertDO cert) {
        LocalDateTime time = cert.getCreateTime() != null ? cert.getCreateTime() : cert.getUpdateTime();
        return time == null ? "" : time.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"));
    }

    private String repeat(String text, int count) {
        StringBuilder builder = new StringBuilder(text.length() * count);
        for (int i = 0; i < count; i++) {
            builder.append(text);
        }
        return builder.toString();
    }

    private String defaultText(String text) {
        return StringUtils.hasText(text) ? text : "";
    }

    private String safeName(String text) {
        if (!StringUtils.hasText(text)) {
            return "tutor-cert";
        }
        return text.replaceAll("[\\\\/:*?\"<>|]", "_");
    }

    private String limitMsg(String msg) {
        if (!StringUtils.hasText(msg)) {
            return "导师证书图片生成失败";
        }
        return msg.length() > 500 ? msg.substring(0, 500) : msg;
    }
}
