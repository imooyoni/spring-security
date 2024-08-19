package com.moonie.authorization.excel.controller;

import com.moonie.authorization.common.controller.CommonController;
import com.moonie.authorization.common.reponse.CommonResponse;
import com.moonie.authorization.excel.dto.ExcelTemplateConfig;
import com.moonie.authorization.excel.response.ExcelUserFileResponse;
import com.moonie.authorization.excel.service.ExcelManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Tag(name="EXCEL", description = "EXCEL SERVICE 관련 API 입니다.")
@Slf4j
@RestController
@RequestMapping("/excel")
@RequiredArgsConstructor
public class ExcelController extends CommonController {
    private final ExcelManageService excelManageService;

    @PostMapping("/upload")
    @Operation(summary = "upload excel file")
    public CommonResponse excelUserUpload(@RequestParam("file") MultipartFile file) throws IOException {
        List<ExcelUserFileResponse> response = excelManageService.excelFileuserUpload(file);
        return result(response);
    }

    @GetMapping("/template/{contents}")
    @Operation(summary = "download excel template file")
    public CommonResponse downloadExcelTemplate(HttpServletResponse response, @PathVariable(name="contents") String contents) throws Exception {
        try {
            ExcelTemplateConfig config = getExcelTemplateConfig(contents);

            if (config == null) {
                throw new IllegalArgumentException("Invalid contents type");
            }
            String saveDirectory = "C:\\Users\\user\\Desktop";  // 파일 저장 경로
            String fileName = config.getFileName() + ".xlsx";
            String filePath = Paths.get(saveDirectory, fileName).toString();

            excelManageService.downloadExcelTemplateViaWeb(response, config.getSheetName(), fileName, config.getFieldList(), config.getLabelMapper());
            excelManageService.downloadExcelTemplateToFile(filePath, config.getSheetName(), fileName, config.getFieldList(), config.getLabelMapper());

        } catch (Exception ex) {
            log.error("Exception occured : ", ex);
            throw ex;
        }
        return success();
    }

    private ExcelTemplateConfig getExcelTemplateConfig(String contents) {
        ExcelTemplateConfig excelTemplateConfig = new ExcelTemplateConfig();
        if ("user".equals(contents)) {
            excelTemplateConfig.setSheetName("UserTemplate");
            excelTemplateConfig.setFileName("sheet1");
            excelTemplateConfig.setFieldList( Arrays.asList("userName", "email", "phoneNumber"));
            excelTemplateConfig.setLabelMapper(Map.of("userName", "고객명", "email", "이메일", "phoneNumber", "휴대전화 번호"));
        }
        // Add more content types as needed
        return excelTemplateConfig;
    }

}
