package com.moonie.authorization.util;

import org.springframework.security.crypto.password.PasswordEncoder;

public class Sha512PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            return EncryptUtil.sha256(rawPassword.toString());
        } catch (Exception e) {
            throw new RuntimeException("SHA-256 encryption failed", e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
            String encodedRawPassword = EncryptUtil.sha256(rawPassword.toString());
            return encodedPassword.equals(encodedRawPassword);
        } catch (Exception e) {
            throw new RuntimeException("SHA-256 encryption failed", e);
        }
    }
}