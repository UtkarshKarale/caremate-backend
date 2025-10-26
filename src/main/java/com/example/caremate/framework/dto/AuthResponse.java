package com.example.caremate.framework.dto;

import com.example.caremate.framework.model.UserRoles;
import com.example.caremate.user.entity.User;

public class AuthResponse {
    private String token;
    private User user;
    private String role;

    // Default constructor
    public AuthResponse() {
    }

    // Parameterized constructor
    public AuthResponse(String token, UserRoles roles, User user) {
        this.token = token;
        this.user = user;
        this.role = roles != null ? roles.name() : null;
    }

    // Getter and Setter for token
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setUser(User user) {
        this.user = user;
    }

    // Getter and Setter for role
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public User getUser() {
        return user;
    }
}
