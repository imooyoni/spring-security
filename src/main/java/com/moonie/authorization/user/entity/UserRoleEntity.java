package com.moonie.authorization.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name="USER_ROLE")
public class UserRoleEntity {
    @Id @Column(name = "ROLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;

    @Column(name="USER_ID")
    private long userId;

    @Column(name="ROLE_NAME")
    private String roleName;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="CREATED_AT")
    private LocalDateTime createdAt;
}