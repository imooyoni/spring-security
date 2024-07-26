package com.moonie.authorization.login.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ROLES")
public class RolesEntity {

    @Id @Column(name = "ROLE_NAME")
    private String roleName;
}
