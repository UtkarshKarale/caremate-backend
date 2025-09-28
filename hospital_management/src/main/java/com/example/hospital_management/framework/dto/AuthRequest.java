package com.example.hospital_management.framework.dto;

public class AuthRequest {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }
    public void setEmail(){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(){
        this.password = password;
    }
}
