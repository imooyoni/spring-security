package com.moonie.authorization.common.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse {
    private boolean success; //true || false
    private int code; //응답 코드 번호 : >= 0 정상, < 0 비정상
    private String msg; //message
}
