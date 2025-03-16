package com.example.auth.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public record SecurityConstants() {
    public static final int EXPIRATION_DATE = 1000 * 60 * 60;
    public static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    public static final String ACCESS_TOKEN_NAME = "access-token";
}
