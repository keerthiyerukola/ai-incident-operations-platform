package com.keerthi.incident_platform.auth;

import com.keerthi.incident_platform.user.Role;
import com.keerthi.incident_platform.user.dto.UserResponse;

public class LoginResponse {

    private String token;
    private UserResponse user;

    public LoginResponse(String token, UserResponse user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public UserResponse getUser() {
        return user;
    }
}