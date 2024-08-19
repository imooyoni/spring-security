package com.moonie.authorization.user.controller;

import com.moonie.authorization.common.controller.CommonController;
import com.moonie.authorization.common.exception.CustomException;
import com.moonie.authorization.common.exception.handler.ErrorCode;
import com.moonie.authorization.common.reponse.CommonResponse;
import com.moonie.authorization.common.service.CommonService;
import com.moonie.authorization.user.dto.LoginRequest;
import com.moonie.authorization.user.dto.ModifyUserInfoRequest;
import com.moonie.authorization.user.dto.SignUpRequest;
import com.moonie.authorization.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@Tag(name="USER", description = "USER INFO 관련 API 입니다.")
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends CommonController {
    private final UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "signup service")
    public CommonResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            if(signUpRequest.getUsername().isBlank()){
                throw new CustomException(ErrorCode.USER_MISSING_USERNAME);
            }
            if(signUpRequest.getPassword().isBlank()){
                throw new CustomException(ErrorCode.USER_MISSING_PASSWORD);
            }
            if(signUpRequest.getUseremail().isBlank()){
                throw new CustomException(ErrorCode.USER_MISSING_EMAIL);
            }
            if(signUpRequest.getUserphone().isBlank()){
                throw new CustomException(ErrorCode.USER_MISSING_PHONE);
            }
            if(signUpRequest.getUserCountryCode().isBlank()){
                throw new CustomException(ErrorCode.USER_MISSING_COUNTRYCODE);
            }

            return result(userService.setSignUpInfo(signUpRequest));

        } catch (CustomException e) {
            log.error("Custom exception occurred: ", e);
            throw e;
        } catch (NoSuchAlgorithmException e) {
            log.error("No such algorithm exception occurred: ", e);
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    @Operation(summary = "login service")
    public CommonResponse logIn(@RequestBody LoginRequest loginRequest) {
        try {
            if(loginRequest.getUsername().isBlank()){
                throw new CustomException(ErrorCode.USER_MISSING_USERNAME);
            }
            if(loginRequest.getPassword().isBlank()){
                throw new CustomException(ErrorCode.USER_MISSING_PASSWORD);
            }

//            return result(userService.setLoginInfo(loginRequest));
            return result(userService.authorize(loginRequest));
        } catch (CustomException e) {
            log.error("No such algorithm exception occurred: ", e);
            throw e;
        }
//        catch (NoSuchAlgorithmException e) {
//            log.error("No such algorithm exception occurred: ", e);
//            throw new RuntimeException(e);
//        }
    }

    @PutMapping("/modification")
    @Operation(summary = "modify user info service")
    public CommonResponse modifyUserinfo(@RequestBody ModifyUserInfoRequest modifyUserInfoRequest) {
        try{
            if(modifyUserInfoRequest.getUserid() == null){
                throw new CustomException(ErrorCode.USER_MISSING_USERID);
            }

            return result(userService.setModifyUserInfo(modifyUserInfoRequest));
        } catch (CustomException e) {
            log.error("Custom exception occurred: ", e);
            throw e;
        }
    }
}
