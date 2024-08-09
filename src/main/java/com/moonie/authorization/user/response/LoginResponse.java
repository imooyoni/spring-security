package com.moonie.authorization.user.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private Long userId;
    private String userName;
    private String userEmail;
}
