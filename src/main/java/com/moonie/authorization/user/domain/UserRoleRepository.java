package com.moonie.authorization.user.domain;

import com.moonie.authorization.user.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

}
