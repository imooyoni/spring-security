package com.moonie.authorization.login.controller;

import com.moonie.authorization.login.dto.LoginDTO;
import com.moonie.authorization.login.response.LoginResponse;
import com.moonie.authorization.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String setLoginInfo(@ModelAttribute LoginDTO loginDTO, Model model){
        String userEmail = loginDTO.getEmail();
        String userPassword = loginDTO.getPassword();

        log.info("::::::login controller:::::: id:{}, pw:{}", userEmail, userPassword);
        LoginResponse loginResponse = loginService.setLoginInfo(userEmail, userPassword);
        model.addAttribute("loginResponse", loginResponse);
        return "index";
    }
}
