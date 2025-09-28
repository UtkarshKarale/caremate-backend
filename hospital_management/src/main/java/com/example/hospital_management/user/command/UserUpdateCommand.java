package com.example.hospital_management.user.command;

import com.example.hospital_management.user.entity.UserStatus;

public class UserUpdateCommand {
    private String fullName;
    private String mobile;
    private UserStatus status;

    // Getters & Setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) { this.status = status; }
}
