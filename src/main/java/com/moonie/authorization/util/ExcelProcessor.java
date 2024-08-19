package com.moonie.authorization.util;

import com.moonie.authorization.common.exception.CustomException;
import com.moonie.authorization.common.exception.handler.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class ExcelProcessor {
    /***
     * detail excelupload
     * @param file
     * @param labelMapper
     * @param validators
     * @return
     * @throws IOException
     */
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
            }

            rowData.put("rowNum", i + 1);
            if (errMsg.length() > 0) {
                rowData.put("errMsg", errMsg.toString().trim());
            }
            resultList.add(rowData);
        }

        return resultList;
    }

    // excel file down module
    public void downloadExcelTemplateViaWeb(HttpServletResponse response, String sheetName, String fileName, List<String> fieldList, Map<String, String> labelMapper) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".xlsx\"");

        OutputStream output = response.getOutputStream();

        try (
                Workbook workbook = new XSSFWorkbook();
                OutputStream outputStream = response.getOutputStream()
//                FileOutputStream fileOut = new FileOutputStream(filePath)
        ) {
            // 시트 생성
            Sheet sheet = workbook.createSheet(sheetName);

            // 스타일 설정
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);

            // 헤더 행 생성
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < fieldList.size(); i++) {
                String headerValue = labelMapper.get(fieldList.get(i));
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headerValue);
                cell.setCellStyle(headerStyle);
            }

            //header value
            for(int i=0; i<fieldList.size(); i++){
                String headerValue = labelMapper.get(fieldList.get(i));

                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headerValue);
                cell.setCellStyle(headerStyle);
            }

            for (int i = 0; i<fieldList.size(); i++) {
                // 열넓이 설정 (열 위치, 넓이)
                sheet.autoSizeColumn(i);
                // cell is 255 chararcters 에러 원인(윗줄만으로는 컬럼의 width가 부족하여 더 늘려야함.)
                sheet.setColumnWidth(i, (sheet.getColumnWidth(i)) + 1024);
            }
            // WorkSheet 쓰기
            workbook.write(output);
//            workbook.write(fileOut);
        } catch (Exception e) {
            log.error("엑셀 파일 다운로드 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void downloadExcelTemplateToFile(String sheetName, String filePath, List<String> fieldList, Map<String, String> labelMapper) throws Exception {
        try (
                Workbook workbook = new XSSFWorkbook();
                FileOutputStream fileOut = new FileOutputStream(filePath)
        ) {
            // 시트 생성
            Sheet sheet = workbook.createSheet(sheetName);

            // 스타일 설정
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);

            // 헤더 행 생성
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < fieldList.size(); i++) {
                String headerValue = labelMapper.get(fieldList.get(i));
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headerValue);
                cell.setCellStyle(headerStyle);
            }

            //header value
            for(int i=0; i<fieldList.size(); i++){
                String headerValue = labelMapper.get(fieldList.get(i));

                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headerValue);
                cell.setCellStyle(headerStyle);
            }

            for (int i = 0; i<fieldList.size(); i++) {
                // 열넓이 설정 (열 위치, 넓이)
                sheet.autoSizeColumn(i);
                // cell is 255 chararcters 에러 원인(윗줄만으로는 컬럼의 width가 부족하여 더 늘려야함.)
                sheet.setColumnWidth(i, (sheet.getColumnWidth(i)) + 1024);
            }
            // WorkSheet 쓰기
            workbook.write(fileOut);
        } catch (Exception e) {
            log.error("엑셀 파일 다운로드 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        }
    }
}

