package com.moonie.authorization.user.service;

import com.moonie.authorization.common.exception.CustomException;
import com.moonie.authorization.common.exception.handler.ErrorCode;
import com.moonie.authorization.user.domain.RolesRepository;
import com.moonie.authorization.user.domain.UserBasicRepository;
import com.moonie.authorization.user.domain.UserRoleRepository;
import com.moonie.authorization.user.dto.LoginRequest;
import com.moonie.authorization.user.dto.ModifyUserInfoRequest;
import com.moonie.authorization.user.dto.SignUpRequest;
import com.moonie.authorization.user.entity.UserBasicEntity;
import com.moonie.authorization.user.entity.UserRoleEntity;
import com.moonie.authorization.user.response.LoginResponse;
import com.moonie.authorization.user.response.ModifyUserInfoResponse;
import com.moonie.authorization.user.response.SignUpResponse;
import com.moonie.authorization.util.EncryptUtil;
import com.moonie.authorization.util.GlobalContants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserBasicRepository userBasicRepository;
    private final UserRoleRepository userRoleRepository;

    public LoginResponse setLoginInfo(LoginRequest loginRequest) throws NoSuchAlgorithmException {
        String userPassword = EncryptUtil.sha512(loginRequest.getPassword());

        Optional<UserBasicEntity> optUserEntity = userBasicRepository.findByUserNameAndUserPassword(loginRequest.getUsername(), userPassword);
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

    public SignUpResponse setSignUpInfo(SignUpRequest signUpRequest) throws NoSuchAlgorithmException {
        try{

            String userPassword = EncryptUtil.sha512(signUpRequest.getPassword());
            Optional<UserBasicEntity> optUserEntity = userBasicRepository.findByUserNameAndUserPassword(signUpRequest.getUsername(), userPassword);

            if(optUserEntity.isPresent()){
                throw new CustomException(ErrorCode.USER_EXIST_USER_INFO);
            }

            UserBasicEntity userBasicEntity = UserBasicEntity.builder()
                                                             .userEmail(signUpRequest.getUseremail())
                                                             .userPassword(userPassword)
                                                             .userName(signUpRequest.getUsername())
                                                             .userPhone(signUpRequest.getUserphone())
                                                             .userCountyCode(signUpRequest.getUserCountryCode())
                                                             .userSaltKey(GlobalContants.SALT_KEY)
                                                             .build();

            userBasicEntity = userBasicRepository.save(userBasicEntity); // Save the entity to the database

            UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                                                          .userId(userBasicEntity.getUserId())
                                                          .createdAt(LocalDateTime.now())
                                                          .roleName(GlobalContants.ROLE_1)
                                                          .description("USER")
                                                          .build();

            userRoleRepository.save(userRoleEntity);

            return SignUpResponse.builder()
                                 .userName(userBasicEntity.getUserName())
                                 .userEmail(userBasicEntity.getUserEmail())
                                 .userId(userBasicEntity.getUserId())
                                 .build();

        } catch (CustomException e) {
            throw e;
        } catch(DataAccessException e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.NOT_IMPLEMENTED);
        }
    }

    @Transactional
    public ModifyUserInfoResponse setModifyUserInfo(ModifyUserInfoRequest modifyUserInfoRequest) {
        try{
            Optional<UserBasicEntity> optionalUserBasicEntity = userBasicRepository.findById(modifyUserInfoRequest.getUserid());
            if(optionalUserBasicEntity.isEmpty()){
                throw new CustomException(ErrorCode.USER_NO_EXIST_USER);
            }

            // 조회된 엔티티를 가져옵니다.
            UserBasicEntity userBasicEntity = optionalUserBasicEntity.get();

            // 비밀번호가 비어있지 않으면 비밀번호를 암호화하고 업데이트합니다.
            if (!modifyUserInfoRequest.getPassword().isBlank()) {
                String userPassword = EncryptUtil.sha512(modifyUserInfoRequest.getPassword());
                userBasicEntity.setUserPassword(userPassword);
            }

            // 필드가 빈 문자열이 아니면 해당 필드를 업데이트합니다.
            if (!modifyUserInfoRequest.getUsername().isBlank()) {
                userBasicEntity.setUserName(modifyUserInfoRequest.getUsername());
            }

            if (!modifyUserInfoRequest.getUseremail().isBlank()) {
                userBasicEntity.setUserEmail(modifyUserInfoRequest.getUseremail());
            }

            if (!modifyUserInfoRequest.getUsercountrycode().isBlank()) {
                userBasicEntity.setUserCountyCode(modifyUserInfoRequest.getUsercountrycode());
            }

            if (!modifyUserInfoRequest.getUserphone().isBlank()) {
                userBasicEntity.setUserPhone(modifyUserInfoRequest.getUserphone());
            }

            // 응답 객체를 생성합니다.
            ModifyUserInfoResponse modifyUserInfoResponse = new ModifyUserInfoResponse();
                modifyUserInfoResponse.setUserid(userBasicEntity.getUserId());
                modifyUserInfoResponse.setUsername(userBasicEntity.getUserName());
                modifyUserInfoResponse.setUserphone(userBasicEntity.getUserPhone());
                modifyUserInfoResponse.setUseremail(userBasicEntity.getUserEmail());
                modifyUserInfoResponse.setUsercountrycode(userBasicEntity.getUserCountyCode());

            return modifyUserInfoResponse;

        } catch (CustomException e) {
            throw e;
        } catch (NoSuchAlgorithmException e) {
            log.error("No such algorithm exception occurred: ", e);
            throw new RuntimeException(e);
        }
    }
}
