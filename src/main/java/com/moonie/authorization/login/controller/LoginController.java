package com.moonie.authorization.login.controller;

import com.moonie.authorization.login.dto.LoginDto;
import com.moonie.authorization.login.response.LoginResponse;
import com.moonie.authorization.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String setLoginInfo(@ModelAttribute LoginDto loginDTO, Model model){
        String userName = loginDTO.getUsername();
        String userPassword = loginDTO.getPassword();

        log.info("::::::login controller:::::: id:{}, pw:{}", userName, userPassword);
        LoginResponse loginResponse = loginService.setLoginInfo(userName, userPassword);
        model.addAttribute("loginResponse", loginResponse);
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
