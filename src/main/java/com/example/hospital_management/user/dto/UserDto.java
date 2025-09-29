package com.example.hospital_management.user.dto;

import com.example.hospital_management.framework.model.UserRoles;
import com.example.hospital_management.user.entity.UserStatus;
import java.util.Date;

public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String fullName;
    private String mobile;
    private UserRoles roles = UserRoles.USER;
    private UserStatus status = UserStatus.ACTIVE;
    private Date createdOn = new Date();

    // Getters & Setters
}
