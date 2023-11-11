package com.plocky.global.jwt.dto;

public class JwtException extends RuntimeException{
    private final String message;

    public JwtException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
