package com.chee.sendmail.service.impl;

import com.chee.sendmail.entity.TableRow;
import com.chee.sendmail.service.ReadService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ReadServiceImpl implements ReadService {
    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";


    public List<TableRow> read(String filePath, LocalDate localDate) {
        File file = new File(filePath);
        // 解析得到workbook对象
        Workbook workbook = null;
        try (FileInputStream inputStream = new FileInputStream(file)) {
            workbook = getWorkbook(inputStream, file.getName().substring(file.getName().indexOf('.') + 1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        if (workbook == null) {
            System.out.println("解析workbook对象失败");
            return null;
        }
        String getSheetName = localDate.getMonthValue() + "." + localDate.getDayOfMonth();
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        Sheet sendSheet = null;
        while (sheetIterator.hasNext()) {
            Sheet next = sheetIterator.next();
            if (next.getSheetName().trim().equals(getSheetName)) {
                sendSheet = next;
                break;
            }
        }
        if (sendSheet == null) {
            System.out.println("未获取到名为" + getSheetName + "的sheet页");
            return null;
        }

        List<TableRow> result = new ArrayList<>();

        int rowNum = 1;
        String lastName = "";
        while (rowNum <= sendSheet.getLastRowNum() && sendSheet.getRow(rowNum) != null) {
            Row row = sendSheet.getRow(rowNum);
            String name = getCellString(row.getCell(0));
            if (name == null) {
                name = lastName;
            } else {
                lastName = name;
            }
            String content = getCellString(row.getCell(1));
            if (content == null) {
                rowNum++;
                continue;
            }
            String hours = String.valueOf((int) row.getCell(2).getNumericCellValue());
            String remark = getCellString(row.getCell(3));

            TableRow tableRow = new TableRow();
            tableRow.setName(name);
            tableRow.setContent(content);
            tableRow.setHours(hours);
            tableRow.setRemark(remark);
            result.add(tableRow);

            rowNum++;
        }
        return result;
    }

    private static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
        Workbook workbook = null;
        if (fileType.equalsIgnoreCase(XLS)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileType.equalsIgnoreCase(XLSX)) {
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }

    private static String getCellString(Cell cell) {
        if (cell == null) {
            return null;
        }
        String stringCellValue = cell.getStringCellValue();
        if (StringUtils.isEmpty(stringCellValue)) {
            return null;
        }
        stringCellValue = stringCellValue.trim();
        if (StringUtils.isEmpty(stringCellValue)) {
            return null;
        }
        return stringCellValue;
    }
}
