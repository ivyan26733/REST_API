package com.authservice.AuthService.utils;

import com.authservice.AuthService.model.User;

public class LoginDTO {
    User user;
    String token;

    public LoginDTO() {

    }

    public LoginDTO(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
