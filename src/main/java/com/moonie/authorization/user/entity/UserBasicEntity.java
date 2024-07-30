package com.moonie.authorization.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@Table(name = "USER_BASIC")
@RequiredArgsConstructor
@AllArgsConstructor
public class UserBasicEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_EMAIL")
    private String userEmail;

    @Column(name = "USER_PASSWORD")
    private String userPassword;

    @Column(name="USER_COUNTRY_CODE")
    private String userCountyCode;

    @Column(name="USER_PHONE")
    private String userPhone;

    @Column(name="USER_SALT_KEY")
    private String userSaltKey;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_name", referencedColumnName = "role_name")}
    )
    private Set<RolesEntity> roles;

}
