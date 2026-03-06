package cn.iocoder.yudao.module.yw.service.vip.impl;

import cn.iocoder.yudao.module.yw.vo.vip.YwVipApplySaveReqVO;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class YwVipApplyDocxParser {

    private static final List<String> COMPANY_TYPE_OPTIONS =
            Arrays.asList("教育公司", "旅行社", "基地", "营地", "其他");

    private static final List<String> APPLY_LEVEL_OPTIONS =
            Arrays.asList("副会长单位", "常务理事单位", "理事单位", "会员单位");

    public YwVipApplySaveReqVO parse(String filePath) throws Exception {
        try (InputStream inputStream = new URL(filePath).openStream();
             XWPFDocument document = new XWPFDocument(inputStream)) {

            YwVipApplySaveReqVO result = new YwVipApplySaveReqVO();

            parseTables(document, result);
            parseWholeText(document, result);

            return result;
        }
    }

    private void parseTables(XWPFDocument document, YwVipApplySaveReqVO result) {
        Section currentSection = Section.NONE;

        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                List<String> cells = row.getTableCells().stream()
                        .map(cell -> normalize(cell.getText()))
                        .collect(Collectors.toList());

                if (cells.isEmpty()) {
                    continue;
                }

                String rowText = normalize(String.join(" ", cells));

                if (rowText.contains("法人代表")) {
                    currentSection = Section.LEGAL_REP;
                } else if (rowText.contains("入会代表")) {
                    currentSection = Section.APPLY_REP;
                } else if (rowText.contains("公司联系人")) {
                    currentSection = Section.CONTACT;
                } else if (rowText.contains("单位类型") || rowText.contains("拟申请会员") || rowText.contains("业务范围") || rowText.contains("单位介绍")) {
                    currentSection = Section.NONE;
                }

                // 顶部单位信息
                fillPair(cells, "单位名称", result::setCompanyName);
                fillPair(cells, "单位地址", result::setCompanyAddress);
                fillPair(cells, "单位电话", result::setCompanyPhone);
                fillPair(cells, "网址", result::setWebsite);
                fillPair(cells, "公司成立时间", value -> result.setEstablishedDate(parseDate(value)));

                // 第二页长文本
                fillPair(cells, "业务范围", result::setBusinessScope);
                fillPair(cells, "单位介绍", result::setCompanyIntro);

                // 单位类型
                if (rowText.contains("单位类型")) {
                    String companyType = parseCheckedOption(rowText, COMPANY_TYPE_OPTIONS);
                    if (StringUtils.hasText(companyType)) {
                        result.setCompanyType(companyType);
                    }
                }

                // 申请等级
                if (rowText.contains("拟申请会员")) {
                    String applyLevel = parseCheckedOption(rowText, APPLY_LEVEL_OPTIONS);
                    if (StringUtils.hasText(applyLevel)) {
                        result.setApplyLevel(applyLevel);
                    }
                }

                // 入会代表
                if (currentSection == Section.APPLY_REP || rowText.contains("入会代表")) {
                    fillPair(cells, "姓名", result::setRepName);
                    fillPair(cells, "政治面貌", result::setRepPolitical);
                    fillPair(cells, "性别", result::setRepGender);
                    fillPair(cells, "学历", result::setRepEducation);
                    fillPair(cells, "联系电话", result::setRepPhone);
                    fillPair(cells, "职务", result::setRepPosition);
                    fillPair(cells, "电子邮箱", result::setRepEmail);
                    fillPair(cells, "身份证号", result::setRepIdcard);
                }

                // 联系人
                if (currentSection == Section.CONTACT || rowText.contains("公司联系人")) {
                    fillPair(cells, "姓名", result::setContactName);
                    fillPair(cells, "联系电话", result::setContactPhone);
                }
            }
        }
    }

    private void parseWholeText(XWPFDocument document, YwVipApplySaveReqVO result) {
        StringBuilder sb = new StringBuilder();

        for (XWPFParagraph paragraph : document.getParagraphs()) {
            sb.append(normalize(paragraph.getText())).append("\n");
        }

        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    sb.append(normalize(cell.getText())).append("\n");
                }
            }
        }

        String allText = sb.toString();

        if (!StringUtils.hasText(result.getMemberNo())) {
            String memberNo = firstGroup(allText, "会员编号[:：]\\s*([A-Za-z0-9\\-_/]+)");
            if (StringUtils.hasText(memberNo)) {
                result.setMemberNo(memberNo);
            }
        }

        if (result.getApplyDate() == null) {
            String applyDateStr = firstGroup(allText, "填表日期[:：]\\s*([0-9]{4}[年\\-./][0-9]{1,2}[月\\-./][0-9]{1,2}日?)");
            LocalDate applyDate = parseDate(applyDateStr);
            if (applyDate != null) {
                result.setApplyDate(applyDate);
            }
        }
    }

    private void fillPair(List<String> cells, String label, Consumer<String> setter) {
        for (int i = 0; i < cells.size() - 1; i++) {
            String current = normalize(cells.get(i));
            if (current.contains(label)) {
                String value = normalize(cells.get(i + 1));
                if (StringUtils.hasText(value)) {
                    setter.accept(value);
                }
                return;
            }
        }
    }

    private String parseCheckedOption(String rowText, List<String> options) {
        String normalized = normalize(rowText);
        for (String option : options) {
            String regex = "(☑|✅|✔|√|✓|■|☒|☓|R)\\s*" + Pattern.quote(option);
            if (Pattern.compile(regex).matcher(normalized).find()) {
                return option;
            }
        }
        return null;
    }

    private String firstGroup(String text, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(text);
        return matcher.find() ? normalize(matcher.group(1)) : null;
    }

    private LocalDate parseDate(String text) {
        if (!StringUtils.hasText(text)) {
            return null;
        }
        String normalized = text.replace("年", "-")
                .replace("月", "-")
                .replace("日", "")
                .replace("/", "-")
                .replace(".", "-")
                .replaceAll("\\s+", "");

        Matcher matcher = Pattern.compile("(\\d{4})-(\\d{1,2})-(\\d{1,2})").matcher(normalized);
        if (!matcher.find()) {
            return null;
        }
        return LocalDate.of(
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3))
        );
    }

    private String normalize(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\u00A0", " ")
                .replace("\t", " ")
                .replace("\r", " ")
                .replace("\n", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private enum Section {
        NONE,
        LEGAL_REP,
        APPLY_REP,
        CONTACT
    }
}
