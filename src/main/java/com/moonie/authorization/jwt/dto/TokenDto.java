package com.moonie.authorization.jwt.dto;

import com.moonie.authorization.user.entity.UserBasicEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private String token;
    private UserBasicEntity userBasicEntity;
}
