package com.authservice.AuthService.exceptions;

public class UserNotAuthenticatedException extends Exception{
    public UserNotAuthenticatedException(String message) {
        super(message);
    }
}
