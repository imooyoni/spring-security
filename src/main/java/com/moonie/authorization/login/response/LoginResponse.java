package com.moonie.authorization.login.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPassword;
}
