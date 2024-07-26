package com.moonie.authorization.util;

import org.springframework.beans.factory.annotation.Value;

public final class GlobalContants {
    // jwt
    public static final String SECRET_KEY = "c2VjcmV0LWp3dC10dXRvcmlhbC1tYWtlLWxvbmdlci0yNTYtYml0ZQ==";
    public static final String AUTHORITIES_KEY = "auth";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final long ONE_DAY = 86400;
}
