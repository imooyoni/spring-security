package com.moonie.authorization.login.service;

import com.moonie.authorization.login.domain.LoginRepository;
import com.moonie.authorization.login.dto.LoginDTO;
import com.moonie.authorization.login.entity.UserEntity;
import com.moonie.authorization.login.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;

    public LoginResponse setLoginInfo(String userEmail, String userPassword){
        Optional<UserEntity> optUserEntity = loginRepository.findByUserEmailAndUserPassword(userEmail, userPassword);
        LoginResponse loginResponse = new LoginResponse();

        if(optUserEntity.isPresent()){
            UserEntity userEntity = optUserEntity.get();
                loginResponse.setUserId(userEntity.getUserId());
                loginResponse.setUserName(userEntity.getUserName());
                loginResponse.setUserEmail(userEntity.getUserEmail());
        }
        return loginResponse;
    }

}
