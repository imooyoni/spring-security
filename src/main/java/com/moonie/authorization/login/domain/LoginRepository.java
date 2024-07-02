package com.moonie.authorization.login.domain;

import com.moonie.authorization.login.entity.UserEntity;
import com.moonie.authorization.login.response.LoginResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserEmailAndUserPassword(String userEmail, String userPassword);

}
