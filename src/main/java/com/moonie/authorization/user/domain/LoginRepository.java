package com.moonie.authorization.user.domain;

import com.moonie.authorization.user.entity.UserBasicEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends CrudRepository<UserBasicEntity, Long> {

    Optional<UserBasicEntity> findByUserNameAndUserPassword(String userEmail, String userPassword);

}
