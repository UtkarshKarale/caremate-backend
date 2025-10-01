package com.example.caremate.user.command;

import com.example.caremate.framework.model.UserRoles;

public class DoctorRegisterCommand {
    private String email;
    private String password;
    private String fullName;
    private String mobile;
    private String message;
    private UserRoles roles;
    private String specialist;
    private String location;

    // Getters & Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public UserRoles getRoles(){
        return roles;
    }
    public void setRoles(UserRoles roles){
        this.roles = roles;
    }

    public String getSpecialist(){
        return specialist;
    }
    public void setSpecialist(String specialist){
        this.specialist= specialist;
    }

    public String getLocation(){
        return location;
    }
    public void setLocation(String location){
        this.location = location;
    }

}
