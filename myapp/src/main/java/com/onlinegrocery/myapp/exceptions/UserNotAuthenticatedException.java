package com.onlinegrocery.myapp.exceptions;

public class UserNotAuthenticatedException extends Exception{
    public UserNotAuthenticatedException(String message) {
        super(message);
    }
}
