package com.example.caremate.user.command;

import com.example.caremate.framework.model.UserRoles;
import com.example.caremate.user.entity.UserStatus;
import java.util.Date;

public class UserUpdateCommand {
    private String fullName;
    private String mobile;
    private UserStatus status;
    private UserRoles roles;
    private String specialist;
    private String location;
    private Date updatedOn;

    // ✅ Getters & Setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) { this.status = status; }

    public UserRoles getRoles() { return roles; }
    public void setRoles(UserRoles roles) { this.roles = roles; }

    public String getSpecialist() { return specialist; }
    public void setSpecialist(String specialist) { this.specialist = specialist; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Date getUpdatedOn() { return updatedOn; }
    public void setUpdatedOn(Date updatedOn) { this.updatedOn = updatedOn; }
}
