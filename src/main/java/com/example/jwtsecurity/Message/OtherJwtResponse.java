package com.example.jwtsecurity.Message;

public class OtherJwtResponse {
    private String token;

    public OtherJwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
