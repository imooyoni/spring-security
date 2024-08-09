package com.moonie.authorization.util;

import com.moonie.authorization.common.exception.CustomException;
import com.moonie.authorization.common.exception.handler.ErrorCode;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ExcelProcessor {
    public List<Map<String, Object>> processExcelFile(MultipartFile file, Map<String, String> labelMapper, Map<String, Function<String, String>> validators) throws IOException {
        List<Map<String, Object>> resultList = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum <= 1) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        // 첫 번째 행을 헤더로 사용하여 각 열이 어떤 필드에 해당하는지 매핑합니다.
        Row headerRow = sheet.getRow(0);
        Map<Integer, String> columnMapping = new HashMap<>();
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell cell = headerRow.getCell(i);
            String headerValue = cell.getStringCellValue();
            for (Map.Entry<String, String> entry : labelMapper.entrySet()) {
                if (entry.getValue().equals(headerValue)) {
                    columnMapping.put(i, entry.getKey());
                }
            }
        }

        // 각 행을 처리합니다.
        for (int i = 1; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            Map<String, Object> rowData = new HashMap<>();
            StringBuilder errMsg = new StringBuilder();

//            for (int j = 0; j < row.getLastCellNum(); j++) {
            for (Map.Entry<Integer, String> entry : columnMapping.entrySet()) {
                int columnIndex = entry.getKey();
                String fieldName = entry.getValue();

                Cell cell = row.getCell(columnIndex);
                String cellValue = null;

                int cellsInRow = row.getPhysicalNumberOfCells();
                System.out.println("cellsInRow 전체 컬럼의 개수 : " + cellsInRow);

                if (cell != null) {
                    switch (cell.getCellType()) {
                        case FORMULA -> cellValue = cell.getCellFormula();
                        case NUMERIC -> cellValue = cell.getNumericCellValue() + "";
                        case STRING -> cellValue = cell.getStringCellValue();
                        case BLANK -> cellValue = "";
                        case ERROR -> cellValue = cell.getErrorCellValue() + "";
                    }
                } else {
                    cellValue = ""; // 셀이 없을 경우 빈 문자열로 처리
                }

                // 유효성 검사 및 에러 메시지 생성
                if (validators != null && validators.containsKey(fieldName)) {
                    String error = validators.get(fieldName).apply(cellValue);
                    if (error != null) {
                        errMsg.append(error).append(" ");
                    }
                }
                rowData.put(fieldName, cellValue);
//                String fieldName = columnMapping.get(j);
//                if (fieldName != null) {
//                    if (validators != null && validators.containsKey(fieldName)) {
//                        String error = validators.get(fieldName).apply(cellValue);
//                        if (error != null) {
//                            errMsg.append(error).append(" ");
//                        }
//                    }
//                    rowData.put(fieldName, cellValue);
//                }
            }

            rowData.put("rowNum", i + 1);
            if (errMsg.length() > 0) {
                rowData.put("errMsg", errMsg.toString().trim());
            }
            resultList.add(rowData);
        }

        return resultList;
    }
}
