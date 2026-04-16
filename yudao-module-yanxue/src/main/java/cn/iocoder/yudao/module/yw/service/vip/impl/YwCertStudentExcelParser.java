package cn.iocoder.yudao.module.yw.service.vip.impl;

import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwStudentApplyDO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class YwCertStudentExcelParser {

    public List<YwStudentApplyDO> parse(String filePath) throws Exception {
        try (InputStream inputStream = openStream(filePath);
             Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getNumberOfSheets() > 0 ? workbook.getSheetAt(0) : null;
            if (sheet == null) {
                return new ArrayList<>();
            }
            int headerRowIndex = findHeaderRowIndex(sheet);
            if (headerRowIndex < 0) {
                return new ArrayList<>();
            }
            Map<Integer, String> headerMap = buildHeaderMap(sheet.getRow(headerRowIndex));
            List<YwStudentApplyDO> result = new ArrayList<>();
            for (int i = headerRowIndex + 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || isEmptyRow(row)) {
                    continue;
                }
                YwStudentApplyDO item = new YwStudentApplyDO();
                item.setStudentName(getCellValue(row, headerMap, "studentName"));
                item.setIdCard(getCellValue(row, headerMap, "idCard"));
                item.setSchoolName(getCellValue(row, headerMap, "schoolName"));
                item.setClassName(getCellValue(row, headerMap, "className"));
                item.setCourseName(getCellValue(row, headerMap, "courseName"));
                item.setCourseHours(getCellValue(row, headerMap, "courseHours"));
                item.setCourseProvider(getCellValue(row, headerMap, "courseProvider"));
                item.setStampUnit(getCellValue(row, headerMap, "stampUnit"));
                item.setCertDate(parseDate(getCell(row, headerMap, "certDate")));
                if (!StringUtils.hasText(item.getStudentName())) {
                    continue;
                }
                result.add(item);
            }
            return result;
        }
    }

    private int findHeaderRowIndex(Sheet sheet) {
        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            Map<Integer, String> headerMap = buildHeaderMap(row);
            if (headerMap.containsValue("studentName") && headerMap.containsValue("courseName")) {
                return i;
            }
        }
        return -1;
    }

    private Map<Integer, String> buildHeaderMap(Row row) {
        Map<Integer, String> result = new HashMap<>();
        if (row == null) {
            return result;
        }
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            String header = normalize(cellToString(row.getCell(i)));
            String field = resolveField(header);
            if (field != null) {
                result.put(i, field);
            }
        }
        return result;
    }

    private String resolveField(String header) {
        if (!StringUtils.hasText(header)) {
            return null;
        }
        if (containsAny(header, "学生姓名", "姓名")) return "studentName";
        if (containsAny(header, "身份证号", "身份证号码", "证件号码")) return "idCard";
        if (containsAny(header, "学校名称", "学校")) return "schoolName";
        if (containsAny(header, "班级", "班级名称")) return "className";
        if (containsAny(header, "课程名称", "研学课程", "课程主题", "课程")) return "courseName";
        if (containsAny(header, "学习课时", "课时")) return "courseHours";
        if (containsAny(header, "课程实施方", "实施方")) return "courseProvider";
        if (containsAny(header, "落证时间", "证书日期", "发证日期", "日期")) return "certDate";
        if (containsAny(header, "落章单位", "盖章单位", "发证单位")) return "stampUnit";
        return null;
    }

    private boolean containsAny(String text, String... keys) {
        for (String key : keys) {
            if (text.contains(key)) {
                return true;
            }
        }
        return false;
    }

    private String getCellValue(Row row, Map<Integer, String> headerMap, String field) {
        Cell cell = getCell(row, headerMap, field);
        return normalize(cellToString(cell));
    }

    private Cell getCell(Row row, Map<Integer, String> headerMap, String field) {
        for (Map.Entry<Integer, String> entry : headerMap.entrySet()) {
            if (field.equals(entry.getValue())) {
                return row.getCell(entry.getKey());
            }
        }
        return null;
    }

    private LocalDate parseDate(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        String text = normalize(cellToString(cell)).replace("年", "-").replace("月", "-").replace("日", "").replace("/", "-").replace(".", "-");
        String[] arr = text.split("-");
        if (arr.length != 3) {
            return null;
        }
        try {
            return LocalDate.of(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
        } catch (Exception ignored) {
            return null;
        }
    }

    private boolean isEmptyRow(Row row) {
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            if (StringUtils.hasText(normalize(cellToString(row.getCell(i))))) {
                return false;
            }
        }
        return true;
    }

    private InputStream openStream(String filePath) throws Exception {
        if (filePath.startsWith("http://") || filePath.startsWith("https://") || filePath.startsWith("file://")) {
            return new URL(filePath).openStream();
        }
        return new FileInputStream(filePath);
    }

    private String cellToString(Cell cell) {
        if (cell == null) {
            return "";
        }
        CellType type = cell.getCellType();
        if (type == CellType.FORMULA) {
            type = cell.getCachedFormulaResultType();
        }
        if (type == CellType.STRING) return cell.getStringCellValue();
        if (type == CellType.BOOLEAN) return String.valueOf(cell.getBooleanCellValue());
        if (type == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
            }
            double value = cell.getNumericCellValue();
            return value == Math.rint(value) ? String.valueOf((long) value) : String.valueOf(value);
        }
        return "";
    }

    private String normalize(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\u00A0", " ").replace("\r", " ").replace("\n", " ").replace("\t", " ").replaceAll("\\s+", " ").trim();
    }
}
