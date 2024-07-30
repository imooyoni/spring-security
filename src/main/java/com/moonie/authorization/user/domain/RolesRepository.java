package com.moonie.authorization.user.domain;

import com.moonie.authorization.user.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RolesRepository extends JpaRepository<RolesEntity, Long> {
}
