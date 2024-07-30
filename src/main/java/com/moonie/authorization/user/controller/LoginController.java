package com.moonie.authorization.user.controller;

import com.moonie.authorization.user.dto.LoginRequest;
import com.moonie.authorization.user.dto.SignUpRequest;
import com.moonie.authorization.user.response.LoginResponse;
import com.moonie.authorization.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@Controller
@Slf4j
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping
    public String getLoginPage(){
        return "login";
    }

    @PostMapping("/submit")
    public String setLoginInfo(@ModelAttribute LoginRequest loginRequest, Model model) {
        LoginResponse loginResponse = null;
        try {
            loginResponse = loginService.setLoginInfo(loginRequest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        model.addAttribute("loginResponse", loginResponse);
        return "index";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute SignUpRequest signUpRequest, Model model){

        return "index";
    }

//    @GetMapping("/user/list")
//    public String getUserList(
//                    , @RequestHeader Map<String
//                    , String> headers, Model model){
//        try{
//            Long userSeq = Long.parseLong(headers.get("userSeq"));
//            // user list
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        log.info("::::::login controller:::::: id:{}, pw:{}", userEmail, userPassword);
//        LoginResponse loginResponse = loginService.setLoginInfo(userEmail, userPassword);
//        model.addAttribute("loginResponse", loginResponse);
//        return "index";
//    }
}
