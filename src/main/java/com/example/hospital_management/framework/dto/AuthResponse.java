package com.example.hospital_management.framework.dto;

import com.example.hospital_management.framework.model.UserRoles;

public class AuthResponse {
    private String token;
    private String role;

    // Default constructor
    public AuthResponse() {
    }

    // Parameterized constructor
    public AuthResponse(String token, UserRoles roles) {
        this.token = token;
        this.role = roles != null ? roles.name() : null;
    }

    // Getter and Setter for token
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    // Getter and Setter for role
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
