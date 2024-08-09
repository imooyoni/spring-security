package com.moonie.authorization.excel.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExcelUserFileResponse {
    private String userName;
    private String email;
    private String phoneNumber;
    private Integer rowNum;  // 추가: 에러가 발생한 행 번호
    private String errMsg;   // 추가: 에러 메시지
}
