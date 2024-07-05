package com.moonie.authorization.login.domain;

import com.moonie.authorization.login.entity.UserBasicEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends CrudRepository<UserBasicEntity, Long> {

    Optional<UserBasicEntity> findByUserEmailAndUserPassword(String userEmail, String userPassword);

}
