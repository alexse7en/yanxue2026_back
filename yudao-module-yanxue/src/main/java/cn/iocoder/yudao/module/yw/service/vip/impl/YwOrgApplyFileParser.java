package cn.iocoder.yudao.module.yw.service.vip.impl;

import cn.iocoder.yudao.module.yw.vo.vip.YwOrgApplyParseReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgApplySaveReqVO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class YwOrgApplyFileParser {

    public YwOrgApplySaveReqVO parse(YwOrgApplyParseReqVO reqVO) throws Exception {
        String fileType = resolveFileType(reqVO).toLowerCase();
        if ("xlsx".equals(fileType) || "xls".equals(fileType)) {
            return parseExcel(reqVO.getFilePath(), reqVO.getApplyType());
        }
        if ("docx".equals(fileType)) {
            return parseDocx(reqVO.getFilePath(), reqVO.getApplyType());
        }
        throw new IllegalArgumentException("暂不支持该文件类型：" + fileType);
    }

    private YwOrgApplySaveReqVO parseExcel(String filePath, String applyType) throws Exception {
        YwOrgApplySaveReqVO result = new YwOrgApplySaveReqVO();
        result.setApplyType(applyType);
        StringBuilder allText = new StringBuilder();

        try (InputStream inputStream = openStream(filePath);
             Workbook workbook = WorkbookFactory.create(inputStream)) {
            for (int s = 0; s < workbook.getNumberOfSheets(); s++) {
                Sheet sheet = workbook.getSheetAt(s);
                if (sheet == null) {
                    continue;
                }
                int firstRow = sheet.getFirstRowNum();
                int lastRow = sheet.getLastRowNum();
                for (int r = firstRow; r <= lastRow; r++) {
                    Row row = sheet.getRow(r);
                    if (row == null) {
                        continue;
                    }
                    short firstCell = row.getFirstCellNum();
                    short lastCell = row.getLastCellNum();
                    if (firstCell < 0 || lastCell < 0) {
                        continue;
                    }
                    for (int c = firstCell; c < lastCell; c++) {
                        String text = normalize(cellToString(row.getCell(c)));
                        if (!StringUtils.hasText(text)) {
                            continue;
                        }
                        allText.append(text).append("\n");
                        parseSingleLine(text, result);
                        if (!isLikelyLabel(text)) {
                            continue;
                        }
                        String leftLabel = c > firstCell ? normalize(cellToString(row.getCell(c - 1))) : "";
                        String value = findExcelValue(sheet, r, c, text);
                        if (StringUtils.hasText(value)) {
                            fillByLabel(text, value, leftLabel, result);
                        }
                    }
                }
            }
        }
        fillFromWholeText(allText.toString(), result);
        return result;
    }

    private YwOrgApplySaveReqVO parseDocx(String filePath, String applyType) throws Exception {
        YwOrgApplySaveReqVO result = new YwOrgApplySaveReqVO();
        result.setApplyType(applyType);
        StringBuilder allText = new StringBuilder();

        try (InputStream inputStream = openStream(filePath);
             XWPFDocument document = new XWPFDocument(inputStream)) {
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String text = normalize(paragraph.getText());
                if (!StringUtils.hasText(text)) {
                    continue;
                }
                allText.append(text).append("\n");
                parseSingleLine(text, result);
            }
            for (XWPFTable table : document.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    List<String> cells = new ArrayList<>();
                    for (XWPFTableCell cell : row.getTableCells()) {
                        String text = normalize(cell.getText());
                        if (StringUtils.hasText(text)) {
                            cells.add(text);
                            allText.append(text).append("\n");
                        }
                    }
                    parseCellPairs(cells, result);
                }
            }
        }

        fillFromWholeText(allText.toString(), result);
        return result;
    }

    private void parseCellPairs(List<String> cells, YwOrgApplySaveReqVO result) {
        if (cells == null || cells.isEmpty()) {
            return;
        }
        for (String cell : cells) {
            parseSingleLine(cell, result);
        }
        for (int i = 0; i < cells.size() - 1; i++) {
            String leftLabel = i > 0 ? cells.get(i - 1) : "";
            fillByLabel(cells.get(i), cells.get(i + 1), leftLabel, result);
        }
    }

    private void parseSingleLine(String line, YwOrgApplySaveReqVO result) {
        String text = normalize(line);
        if (!StringUtils.hasText(text)) {
            return;
        }
        String[] splitters = new String[] {"：", ":"};
        for (String splitter : splitters) {
            int index = text.indexOf(splitter);
            if (index > 0 && index < text.length() - 1) {
                String label = normalize(text.substring(0, index));
                String value = normalize(text.substring(index + 1));
                fillByLabel(label, value, "", result);
                return;
            }
        }
    }

    private void fillByLabel(String label, String value, String leftLabel, YwOrgApplySaveReqVO result) {
        String key = normalize(label);
        String val = normalize(value);
        String left = normalize(leftLabel);
        if (!StringUtils.hasText(key) || !StringUtils.hasText(val)) {
            return;
        }
        if (isLikelyLabel(val) || !isValueAcceptable(key, val)) {
            return;
        }

        if (containsAny(key, "单位名称", "申请主体")) {
            result.setUnitName(val);
        } else if (containsAny(key, "目的地名称")) {
            result.setDestinationName(val);
        } else if (containsAny(key, "基地主题")) {
            result.setBaseTheme(val);
        } else if (containsAny(key, "单位性质")) {
            result.setUnitType(val);
        } else if (containsAny(key, "通讯地址", "地址")) {
            result.setAddress(val);
        } else if (containsAny(key, "联系人")) {
            result.setContactPerson(val);
        } else if (containsAny(key, "法人姓名", "法人代表") || "法人".equals(key)) {
            result.setLegalPerson(val);
        } else if (containsAny(key, "法人电话")) {
            result.setLegalPhone(val);
        } else if (containsAny(key, "电话", "联系电话")) {
            if (containsAny(left, "法人") || StringUtils.hasText(result.getLegalPerson())) {
                result.setLegalPhone(val);
            } else {
                result.setContactPhone(val);
            }
        } else if (containsAny(key, "电子信箱", "邮箱")) {
            result.setContactEmail(val);
        } else if (containsAny(key, "占地面积")) {
            result.setAreaCover(parseDecimal(val));
        } else if (containsAny(key, "建筑面积")) {
            result.setAreaBuild(parseDecimal(val));
        } else if (containsAny(key, "展示开放面积")) {
            result.setAreaOpen(parseDecimal(val));
        } else if (containsAny(key, "年开放天数")) {
            result.setOpenDaysPerYear(parseInteger(val));
        } else if (containsAny(key, "预计年接待学生人次")) {
            result.setEstimatedStudentsPerYear(parseInteger(val));
        } else if (containsAny(key, "单次可接待学生人数")) {
            result.setMaxStudentsPerTime(parseInteger(val));
        } else if (containsAny(key, "资金投入")) {
            result.setFundInvestment(parseDecimal(val));
        } else if (containsAny(key, "成立时间")) {
            result.setEstablishmentDate(parseDate(val));
        } else if (containsAny(key, "营业执照号")) {
            result.setBusinessLicenseNo(val);
        } else if (containsAny(key, "旅行社经营许可证号")) {
            result.setTravelLicenseNo(val);
        } else if (containsAny(key, "是否成立研学部门")) {
            result.setHasStudyDepartment(parseBoolean(val));
        } else if (containsAny(key, "研学板块专职人员人数")) {
            result.setStudyDeptStaffCount(parseInteger(val));
        } else if (containsAny(key, "专职研学指导师人数")) {
            result.setFulltimeTutorCount(parseInteger(val));
        } else if (containsAny(key, "兼职研学指导师人数")) {
            result.setParttimeTutorCount(parseInteger(val));
        } else if (containsAny(key, "单位概况")) {
            result.setUnitProfile(val);
        }
    }

    private void fillFromWholeText(String text, YwOrgApplySaveReqVO result) {
        if (!StringUtils.hasText(text)) {
            return;
        }
        if (!StringUtils.hasText(result.getUnitName())) {
            result.setUnitName(firstGroup(text, "单位名称[：:]\\s*([^\\n]+)"));
        }
        if (!StringUtils.hasText(result.getAddress())) {
            result.setAddress(firstGroup(text, "(?:通讯地址|地址)[：:]\\s*([^\\n]+)"));
        }
        if (!StringUtils.hasText(result.getLegalPerson())) {
            result.setLegalPerson(firstGroup(text, "(?:法人姓名|法人代表|法人)[：:]\\s*([^\\n]+)"));
        }
        if (!StringUtils.hasText(result.getContactPhone())) {
            String phone = firstGroup(text, "(1\\d{10}|\\d{7,})");
            if (StringUtils.hasText(phone)) {
                result.setContactPhone(phone);
            }
        }
    }

    private String findExcelValue(Sheet sheet, int rowIndex, int colIndex, String label) {
        String down = findDownValue(sheet, rowIndex, colIndex, label);
        if (StringUtils.hasText(down)) {
            return down;
        }
        String right = findRightValue(sheet, rowIndex, colIndex, label);
        if (StringUtils.hasText(right)) {
            return right;
        }
        return null;
    }

    private String findRightValue(Sheet sheet, int rowIndex, int colIndex, String label) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            return null;
        }
        for (int offset = 1; offset <= 3; offset++) {
            String text = normalize(cellToString(row.getCell(colIndex + offset)));
            if (!StringUtils.hasText(text)) {
                continue;
            }
            if (!isLikelyLabel(text) && isValueAcceptable(label, text)) {
                return text;
            }
            break;
        }
        return null;
    }

    private String findDownValue(Sheet sheet, int rowIndex, int colIndex, String label) {
        for (int rowOffset = 1; rowOffset <= 2; rowOffset++) {
            Row row = sheet.getRow(rowIndex + rowOffset);
            if (row == null) {
                continue;
            }
            for (int colOffset = 0; colOffset <= 1; colOffset++) {
                String text = normalize(cellToString(row.getCell(colIndex + colOffset)));
                if (!StringUtils.hasText(text)) {
                    continue;
                }
                if (!isLikelyLabel(text) && isValueAcceptable(label, text)) {
                    return text;
                }
            }
        }
        return null;
    }

    private InputStream openStream(String filePath) throws Exception {
        if (filePath.startsWith("http://") || filePath.startsWith("https://")) {
            return new URL(filePath).openStream();
        }
        if (filePath.startsWith("file://")) {
            return new URL(filePath).openStream();
        }
        return new FileInputStream(filePath);
    }

    private String resolveFileType(YwOrgApplyParseReqVO reqVO) {
        if (StringUtils.hasText(reqVO.getFileType())) {
            return reqVO.getFileType().trim();
        }
        String filePath = reqVO.getFilePath();
        int index = filePath.lastIndexOf('.');
        if (index < 0 || index == filePath.length() - 1) {
            return "";
        }
        return filePath.substring(index + 1);
    }

    private String cellToString(Cell cell) {
        if (cell == null) {
            return "";
        }
        CellType type = cell.getCellType();
        if (type == CellType.FORMULA) {
            type = cell.getCachedFormulaResultType();
        }
        if (type == CellType.STRING) {
            return cell.getStringCellValue();
        }
        if (type == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        }
        if (type == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
            }
            double val = cell.getNumericCellValue();
            if (val == Math.rint(val)) {
                return String.valueOf((long) val);
            }
            return String.valueOf(val);
        }
        return "";
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

    private boolean containsAny(String text, String... keys) {
        for (String key : keys) {
            if (text.contains(key)) {
                return true;
            }
        }
        return false;
    }

    private Integer parseInteger(String text) {
        Matcher matcher = Pattern.compile("(-?\\d+)").matcher(text.replace(",", ""));
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : null;
    }

    private BigDecimal parseDecimal(String text) {
        Matcher matcher = Pattern.compile("(-?\\d+(?:\\.\\d+)?)").matcher(text.replace(",", ""));
        return matcher.find() ? new BigDecimal(matcher.group(1)) : null;
    }

    private Boolean parseBoolean(String text) {
        String val = normalize(text).toLowerCase();
        if (containsAny(val, "是", "有", "true", "1", "y")) {
            return true;
        }
        if (containsAny(val, "否", "无", "false", "0", "n")) {
            return false;
        }
        return null;
    }

    private LocalDate parseDate(String text) {
        String val = normalize(text).replace("年", "-").replace("月", "-").replace("日", "")
                .replace("/", "-").replace(".", "-");
        Matcher matcher = Pattern.compile("(\\d{4})-(\\d{1,2})-(\\d{1,2})").matcher(val);
        if (!matcher.find()) {
            return null;
        }
        return LocalDate.of(Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3)));
    }

    private String firstGroup(String text, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(text);
        return matcher.find() ? normalize(matcher.group(1)) : null;
    }

    private boolean isLikelyLabel(String text) {
        String val = normalize(text);
        if (!StringUtils.hasText(val)) {
            return false;
        }
        if (val.endsWith("：") || val.endsWith(":")) {
            return true;
        }
        return containsAny(val,
                "单位名称", "申请主体", "目的地名称", "基地主题", "单位性质", "通讯地址",
                "联系人", "联系电话", "电话", "电子信箱", "邮箱", "占地面积", "建筑面积", "展示开放面积",
                "年开放天数", "预计年接待学生人次", "单次可接待学生人数", "资金投入",
                "成立时间", "法人姓名", "法人电话", "法人", "营业执照号", "旅行社经营许可证号",
                "是否成立研学部门", "研学板块专职人员人数", "专职研学指导师人数", "兼职研学指导师人数", "单位概况");
    }

    private boolean isValueAcceptable(String label, String value) {
        String key = normalize(label);
        String val = normalize(value);
        if (!StringUtils.hasText(val)) {
            return false;
        }
        if (containsAny(key, "电话", "联系电话", "法人电话")) {
            return Pattern.compile("1\\d{10}|\\d{7,}").matcher(val).find();
        }
        if (containsAny(key, "电子信箱", "邮箱")) {
            return val.contains("@");
        }
        if (containsAny(key, "占地面积", "建筑面积", "展示开放面积", "资金投入",
                "年开放天数", "预计年接待学生人次", "单次可接待学生人数", "人数")) {
            return Pattern.compile("-?\\d+(\\.\\d+)?").matcher(val).find();
        }
        return true;
    }
}
