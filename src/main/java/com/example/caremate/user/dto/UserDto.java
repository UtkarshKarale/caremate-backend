package com.example.caremate.user.dto;

import com.example.caremate.framework.model.UserRoles;
import com.example.caremate.user.entity.UserStatus;
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
