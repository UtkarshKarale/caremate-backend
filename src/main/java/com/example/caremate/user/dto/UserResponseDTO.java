package com.example.caremate.user.dto;

import com.example.caremate.framework.model.UserRoles;
import com.example.caremate.user.entity.User;
import com.example.caremate.user.entity.UserStatus;

public class UserResponseDTO {
    private Long id;
    private String email;
    private String fullName;
    private String mobile;
    private UserRoles roles;
    private UserStatus status;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.mobile = user.getMobile();
        this.roles = user.getRoles();
        this.status = user.getStatus();
    }

    // Getters & Setters
}
