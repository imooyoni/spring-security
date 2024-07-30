package com.moonie.authorization.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyUserInfoRequest {
    private Long userid;
    private String username;
    private String useremail;
    private String password;
    private String usercountrycode;
    private String userphone;
}
