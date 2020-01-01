package com.example.jwtsecurity.Message;

public class TokenMessage {
    private String token;

    public TokenMessage(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
