package com.moonie.authorization.user.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyUserInfoResponse {
    private Long userid;
    private String username;
    private String useremail;
    private String usercountrycode;
    private String userphone;
}
