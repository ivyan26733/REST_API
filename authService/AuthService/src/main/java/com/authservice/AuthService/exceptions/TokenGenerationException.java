package com.authservice.AuthService.exceptions;

public class TokenGenerationException extends Exception{
    public TokenGenerationException(String message) {
        super(message);
    }
}
