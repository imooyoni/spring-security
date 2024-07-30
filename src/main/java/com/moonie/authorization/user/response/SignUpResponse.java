package com.moonie.authorization.user.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignUpResponse {
    private Long userId;
    private String userName;
    private String userEmail;
}
