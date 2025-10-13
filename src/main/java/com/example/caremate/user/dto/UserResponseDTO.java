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
    private String specialist;
    private String location;


    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.mobile = user.getMobile();
        this.roles = user.getRoles();
        this.status = user.getStatus();
        this.specialist =user.getSpecialist();
        this.location= user.getLocation();
    }

    // ✅ Public getters
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public String getMobile() { return mobile; }
    public UserRoles getRoles() { return roles; }
    public UserStatus getStatus() { return status; }
    public String getSpecialist(){return specialist;}
    public String getLocation(){ return location;}

    // ✅ Optional setters (if you need them)
    public void setId(Long id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public void setRoles(UserRoles roles) { this.roles = roles; }
    public void setStatus(UserStatus status) { this.status = status; }
    public void setSpecialist(String specialist){ this.specialist = specialist;}
    public void setLocation(String location){this.location = location;}
}
