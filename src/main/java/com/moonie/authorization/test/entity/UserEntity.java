package com.moonie.authorization.test.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "USER_BASIC")
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_EMAIL")
    private String userEmail;

    @Column(name = "USER_PASSWORD")
    private String user_password;
}
