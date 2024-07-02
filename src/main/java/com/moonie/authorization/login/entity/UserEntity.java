package com.moonie.authorization.login.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "USER_BASIC")
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_EMAIL")
    private String userEmail;

    @Column(name = "USER_PASSWORD")
    private String userPassword;
}
