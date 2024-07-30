package com.moonie.authorization.user.service;

import com.moonie.authorization.common.exception.CustomException;
import com.moonie.authorization.common.exception.handler.ErrorCode;
import com.moonie.authorization.user.domain.LoginRepository;
import com.moonie.authorization.user.dto.LoginRequest;
import com.moonie.authorization.user.entity.UserBasicEntity;
import com.moonie.authorization.user.response.LoginResponse;
import com.moonie.authorization.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    public LoginResponse setLoginInfo(LoginRequest loginRequest) throws NoSuchAlgorithmException {
        String userPassword = EncryptUtil.sha512(loginRequest.getPassword());

        Optional<UserBasicEntity> optUserEntity = loginRepository.findByUserNameAndUserPassword(loginRequest.getUsername(), userPassword);
        LoginResponse loginResponse = new LoginResponse();

        if(optUserEntity.isPresent()){
            UserBasicEntity userBasicEntity = optUserEntity.get();
                loginResponse.setUserId(userBasicEntity.getUserId());
                loginResponse.setUserName(userBasicEntity.getUserName());
                loginResponse.setUserEmail(userBasicEntity.getUserEmail());
        } else {
            throw new CustomException(ErrorCode.USER_NO_EXIST_USER);
        }
        return loginResponse;
    }

}
