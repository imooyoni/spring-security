package com.moonie.authorization.user.repository;

import com.moonie.authorization.login.entity.UserBasicEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBasicRepository extends JpaRepository<UserBasicEntity, Long> {
    @EntityGraph(attributePaths = "roles")
    //entitygrapt annotation -> lazy 조회(proxy 데이터/ 지연조회)가 아닌 eager 조회(원본 데이터/ 즉시조회)로 authorities 정보를 같이 조회
    Optional<UserBasicEntity> findOneWithRolesByUserName(String userName);
}
