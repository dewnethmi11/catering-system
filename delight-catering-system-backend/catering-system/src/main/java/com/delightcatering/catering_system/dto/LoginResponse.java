package com.delightcatering.catering_system.dto;

import com.delightcatering.catering_system.entity.User;

public class LoginResponse {
    private String message;
    private User user;
    private String token; // For future JWT implementation

    public LoginResponse() {}

    public LoginResponse(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public LoginResponse(String message, User user, String token) {
        this.message = message;
        this.user = user;
        this.token = token;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}