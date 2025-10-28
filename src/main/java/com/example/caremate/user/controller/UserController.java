package com.example.caremate.user.controller;

import com.example.caremate.framework.dto.AuthRequest;
import com.example.caremate.framework.dto.AuthResponse;
import com.example.caremate.framework.model.UserRoles;
import com.example.caremate.framework.service.AuthService;
import com.example.caremate.user.command.*;
import com.example.caremate.user.dto.UserResponseDTO;
import com.example.caremate.user.entity.User;
import com.example.caremate.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "User")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;


    @PostMapping("/api/user/register")
    public ResponseEntity<UserRegisterCommand> register(@Valid @RequestBody UserRegisterCommand request) {
        UserRegisterCommand response = authService.registerUser(request);
        response.setPassword(null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/doctor/register")
    public ResponseEntity<DoctorRegisterCommand> registerDoctor(@Valid @RequestBody DoctorRegisterCommand request){
        DoctorRegisterCommand response = authService.registerDoctor(request);
        response.setPassword(null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PutMapping("/api/user/{id}/update/allfields")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable long id, @RequestBody UserUpdateCommand updateCommand) {
        return ResponseEntity.ok(userService.updateUserRequiredFields(id, updateCommand));
    }

    @GetMapping("/api/user/{id}/lookup")
    public ResponseEntity<User> findUserById(@PathVariable long id) {
        return ResponseEntity.of(Optional.ofNullable(userService.findUserById(id)));
    }

    @GetMapping("/api/user/lookup/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.lookUpAllUsers());
    }

    @GetMapping("/api/user/lookup/all/active")
    public ResponseEntity<List<User>> getAllActiveUsers() {
        return ResponseEntity.ok(userService.getAllActiveUsers());
    }

    @PutMapping("/api/user/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        String message = userService.generateResetCodeAndSendToEmail(email);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/api/user/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordCommand request) {
        String message = userService.resetPasswordAfterVerification(request);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/api/user/count/by/{role}")
    public ResponseEntity<Long> countUsersByRole(@PathVariable String role) {
        UserRoles userRole;
        try {
            userRole = UserRoles.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        long count = userService.countUsersByRole(userRole);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/api/admin/register")
    public ResponseEntity<AdminRegisterCommand> registerAdmin(@RequestBody AdminRegisterCommand request) {
        try {
            AdminRegisterCommand response = userService.registerAdmin(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            AdminRegisterCommand errorResponse = new AdminRegisterCommand();
            errorResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            AdminRegisterCommand errorResponse = new AdminRegisterCommand();
            errorResponse.setMessage("An error occurred during registration");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/api/user/latest/users")
    public ResponseEntity<List<User>> getLatestUsers() {
        return ResponseEntity.ok(userService.getLatestUsers());
    }

    @GetMapping("/api/user/latest/doctors")
    public ResponseEntity<List<User>> getLatestDoctors() {
        return ResponseEntity.ok(userService.getLatestDoctors());
    }

    @GetMapping("/api/user/latest/receptionists")
    public ResponseEntity<List<User>> getLatestReceptionists() {
        return ResponseEntity.ok(userService.getLatestReceptionists());
    }

    // Optional: Generic endpoint with query parameter
    @GetMapping("/api/user/latest/by-role")
    public ResponseEntity<List<User>> getLatestUsersByRole(
            @RequestParam String role,
            @RequestParam(defaultValue = "5") int limit) {
        UserRoles userRole;
        try {
            userRole = UserRoles.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.getLatestUsersByRole(userRole, limit));
    }

}
