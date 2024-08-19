package com.moonie.authorization.excel.service;

import com.moonie.authorization.common.exception.CustomException;
import com.moonie.authorization.common.exception.handler.ErrorCode;
import com.moonie.authorization.excel.response.ExcelUserFileResponse;
import com.moonie.authorization.util.ExcelProcessor;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

@Service
@Slf4j
public class ExcelManageService {
    public List<ExcelUserFileResponse> excelFileuserUpload(MultipartFile file) throws IOException {
        ExcelProcessor excelProcessor = new ExcelProcessor();
        // 엑셀 헤더와 필드 매핑
        Map<String, String> labelMapper = new HashMap<>();
        labelMapper.put("userName", "고객명");
        labelMapper.put("email", "이메일");
        labelMapper.put("phoneNumber", "휴대전화 번호");

        // 유효성 검사기 정의
        Map<String, Function<String, String>> validators = new HashMap<>();
        validators.put("phoneNumber", value -> (value == null || value.isBlank()) ? "PhoneNumber is missing." : null);
        validators.put("email", value -> value != null && !value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$") ? "Invalid email format." : null);

        // 엑셀 파일 처리
        List<Map<String, Object>> processedData = excelProcessor.processExcelFile(file, labelMapper, validators);

        // 결과를 ExcelUserFileResponse로 변환
        List<ExcelUserFileResponse> responseList = new ArrayList<>();
        for (Map<String, Object> data : processedData) {
            ExcelUserFileResponse response = new ExcelUserFileResponse();
            response.setUserName((String) data.get("userName"));
            response.setEmail((String) data.get("email"));
            response.setPhoneNumber((String) data.get("phoneNumber"));
            response.setRowNum((Integer) data.get("rowNum"));
            response.setErrMsg((String) data.get("errMsg"));
            responseList.add(response);
        }

        return responseList;

    }
    public List<ExcelUserFileResponse> excelFileIterate(MultipartFile file) throws IOException {
        List<ExcelUserFileResponse> excelUserFileResponseList = new ArrayList<>();
        List<String> field = Arrays.asList(
                "userName", "email", "phoneNumber"
        );
        Map<String, String> labelMapper = new HashMap<>();
            labelMapper.put("userName","고객명");
            labelMapper.put("email","이메일");
            labelMapper.put("phoneNumber","휴대전화 번호");

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        List<Map<String, Object>> excelDataList = new ArrayList<>();
        int lastRowNum = sheet.getLastRowNum();
        int firstRowNum = sheet.getFirstRowNum();
        System.out.println("lastRowNum 전체 행의 개수 : " + lastRowNum);
        System.out.println("firstRowNum 전체 행의 개수 : " + firstRowNum);

        if(lastRowNum <= 1) {
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

        // get rows value
        for(int i=1; i<(lastRowNum + 1); i++) {
            Row row = sheet.getRow(i);
            Map<String, Object> cellData = new HashMap<>();
            ExcelUserFileResponse excelUserFileResponse = new ExcelUserFileResponse();

            int cellsInRow = row.getPhysicalNumberOfCells();
            System.out.println("cellsInRow 전체 컬럼의 개수 : " + cellsInRow);

            // get cells value
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                String cellValue = null;

                if (cell != null) {
                    switch (cell.getCellType()) {
                        case FORMULA -> cellValue = cell.getCellFormula();
                        case NUMERIC -> cellValue = cell.getNumericCellValue() + "";
                        case STRING -> cellValue = cell.getStringCellValue();
                        case BLANK -> cellValue = "";
                        case ERROR -> cellValue = cell.getErrorCellValue() + "";
                    }
                }

                String fieldName = columnMapping.get(j);
                if (fieldName != null) {
                    cellData.put(fieldName, cellValue);
                }
                log.info("value: " + cellValue + ", j: " + j);
            }
            String email = (String) cellData.get("email");
            String phoneNumber = (String) cellData.get("phoneNumber");

            StringBuilder errMsg = new StringBuilder();
            if(phoneNumber == null || phoneNumber.isEmpty()){
                errMsg.append("PhoneNumber is missing.");
            }
            if(email != null && !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
                errMsg.append("Invalid email format.");
            }

            excelUserFileResponse.setRowNum(i + 1);
            excelUserFileResponse.setUserName((String) cellData.get("userName"));
            excelUserFileResponse.setEmail(email);
            excelUserFileResponse.setPhoneNumber(phoneNumber);
            if (errMsg.length() > 0) {
                excelUserFileResponse.setErrMsg(errMsg.toString());
            }
            excelUserFileResponseList.add(excelUserFileResponse);
        }

//        Iterator<Map<String, Object>> result = excelDataList.iterator();
        return excelUserFileResponseList;
    }

    public void downloadExcelTemplateViaWeb(HttpServletResponse response, String sheetName, String fileName, List<String> fieldList, Map<String, String> labelMapper) throws Exception{
        ExcelProcessor excelProcessor = new ExcelProcessor();
        excelProcessor.downloadExcelTemplateViaWeb(response, sheetName, fileName, fieldList, labelMapper);
    }

    public void downloadExcelTemplateToFile(String filePath, String sheetName, String fileName, List<String> fieldList, Map<String, String> labelMapper) throws Exception{
        ExcelProcessor excelProcessor = new ExcelProcessor();
        excelProcessor.downloadExcelTemplateToFile(sheetName, filePath, fieldList, labelMapper);
    }
}
