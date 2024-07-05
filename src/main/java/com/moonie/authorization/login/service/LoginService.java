package com.moonie.authorization.login.service;

import com.moonie.authorization.common.exception.CustomException;
import com.moonie.authorization.common.exception.handler.ErrorCode;
import com.moonie.authorization.login.domain.LoginRepository;
import com.moonie.authorization.login.entity.UserBasicEntity;
import com.moonie.authorization.login.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    public LoginResponse setLoginInfo(String userEmail, String userPassword){
        Optional<UserBasicEntity> optUserEntity = loginRepository.findByUserEmailAndUserPassword(userEmail, userPassword);
        LoginResponse loginResponse = new LoginResponse();

        if(optUserEntity.isPresent()){
            UserBasicEntity userBasicEntity = optUserEntity.get();
                loginResponse.setUserId(userBasicEntity.getUserId());
                loginResponse.setUserName(userBasicEntity.getUserName());
                loginResponse.setUserEmail(userBasicEntity.getUserEmail());
        } else {
            throw new CustomException(ErrorCode.LOGIN_NO_EXIST_USER);
        }
        return loginResponse;
    }

}
