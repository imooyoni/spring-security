package com.moonie.authorization.excel.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ExcelTemplateConfig {
    private String fileName;
    private String sheetName;
    private List<String> fieldList;
    private Map<String, String> labelMapper;
}
