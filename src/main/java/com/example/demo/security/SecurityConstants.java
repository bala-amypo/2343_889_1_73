package com.example.demo.security;

public class SecurityConstants {
    public static final String JWT_SECRET = "mySecretKeyForJWTTokenGenerationAndValidationPurpose12345";
    public static final long JWT_EXPIRATION = 86400000L; // 24 hours
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}