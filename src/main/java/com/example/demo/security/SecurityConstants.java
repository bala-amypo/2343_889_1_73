package com.example.demo.security;

public class SecurityConstants {

    // MUST be at least 256 bits (32+ chars) – otherwise app will CRASH
    public static final String SECRET_KEY ="this-is-a-very-long-256-bit-secret-key-for-demo-project-2025";

    public static final long EXPIRATION_TIME = 86400000; // 1 day
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
