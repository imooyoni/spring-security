package com.moonie.authorization.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "User email is mandatory")
    private String useremail;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "CountryCode is mandatory")
    private String userCountryCode;
    @NotBlank(message = "PhoneNumber is mandatory")
    private String userphone;
}
