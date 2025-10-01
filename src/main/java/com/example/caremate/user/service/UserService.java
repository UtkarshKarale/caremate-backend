package com.example.caremate.user.service;

import com.example.caremate.framework.model.UserRoles;
import com.example.caremate.user.command.DoctorRegisterCommand;
import com.example.caremate.user.command.ResetPasswordCommand;
import com.example.caremate.user.command.UserRegisterCommand;
import com.example.caremate.user.command.UserUpdateCommand;
import com.example.caremate.user.dto.UserResponseDTO;
import com.example.caremate.user.entity.User;
import com.example.caremate.user.entity.UserStatus;
import com.example.caremate.user.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  JavaMailSender mailSender;

    @Autowired
    private ObjectMapper objectMapper;

    public UserRegisterCommand registerUser(UserRegisterCommand request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use: " + request.getEmail());
        }

        if (request.getPassword() == null || request.getPassword().length() <= 5) {
            throw new RuntimeException("Password must be at least 5 characters long");
        }

        if (userRepository.existsByMobile(request.getMobile())) {
            throw new RuntimeException("Mobile already registered: " + request.getMobile());
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setFullName(request.getFullName());
        newUser.setMobile(request.getMobile());
        newUser.setRoles(request.getRoles());
        newUser.setStatus(UserStatus.ACTIVE);
        newUser.setCreatedOn(new Date());

        userRepository.save(newUser);

        UserRegisterCommand response = new UserRegisterCommand();
        response.setEmail(newUser.getEmail());
        response.setFullName(newUser.getFullName());
        response.setMobile(newUser.getMobile());
        response.setRoles(newUser.getRoles());
        response.setMessage("User registered successfully!");
        return response;
    }

    public UserResponseDTO updateUserRequiredFields(Long id, UserUpdateCommand command) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Map<String, Object> updates = objectMapper.convertValue(command, new TypeReference<>() {});
        updates.entrySet().removeIf(entry -> entry.getValue() == null);

        if (updates.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid fields provided for update");
        }

        updates.forEach((key, value) -> {
            switch (key) {
                case "fullName" -> user.setFullName((String) value);
                case "mobile" -> user.setMobile((String) value);
                case "roles" -> {
                    try {
                        user.setRoles(UserRoles.valueOf(value.toString().toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid role value");
                    }
                }
                case "status" -> {
                    try {
                        user.setStatus(UserStatus.valueOf(value.toString().toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status value");
                    }
                }
                default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid field: " + key);
            }
        });

        User savedUser = userRepository.save(user);
        return new UserResponseDTO(savedUser);
    }

    // ✅ Find user by ID
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    // ✅ Fetch all users
    public List<User> lookUpAllUsers() {
        return userRepository.findAll();
    }

    // ✅ Fetch all active users
    public List<User> getAllActiveUsers() {
        return userRepository.findAllByStatus(UserStatus.ACTIVE);
    }

    // ✅ Generate reset code & send email
    public String generateResetCodeAndSendToEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with this email"));

        String resetCode = UUID.randomUUID().toString().substring(0, 8);
        user.setPassword(passwordEncoder.encode(resetCode));
        userRepository.save(user);

        sendPasswordResetEmail(email, resetCode);

        return "A reset code has been sent to your email.";
    }

    private void sendPasswordResetEmail(String email, String resetCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset - Reset Code");
        message.setText("Your reset code is: " + resetCode + "\nPlease log in and change it immediately.");
        mailSender.send(message);
    }

    // ✅ Reset password with verification
    public String resetPasswordAfterVerification(ResetPasswordCommand request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with this email"));

        if (!passwordEncoder.matches(request.getResetCode(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Reset code is invalid.");
        }

        if (request.getNewPassword() == null || request.getNewPassword().length() < 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password must be at least 6 characters long.");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password and confirm password do not match.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return "Password has been successfully reset.";
    }

    public DoctorRegisterCommand registerDoctor(DoctorRegisterCommand request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use: " + request.getEmail());
        }

        if (request.getPassword() == null || request.getPassword().length() <= 5) {
            throw new RuntimeException("Password must be at least 5 characters long");
        }

        if (userRepository.existsByMobile(request.getMobile())) {
            throw new RuntimeException("Mobile already registered: " + request.getMobile());
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setFullName(request.getFullName());
        newUser.setMobile(request.getMobile());
        newUser.setRoles(request.getRoles());
        newUser.setStatus(UserStatus.ACTIVE);
        newUser.setCreatedOn(new Date());
        newUser.setSpecialist(request.getSpecialist());
        newUser.setLocation(request.getLocation());

        userRepository.save(newUser);

        DoctorRegisterCommand response = new DoctorRegisterCommand();
        response.setEmail(newUser.getEmail());
        response.setFullName(newUser.getFullName());
        response.setMobile(newUser.getMobile());
        response.setRoles(newUser.getRoles());
        response.setSpecialist(newUser.getSpecialist());
        response.setLocation(newUser.getLocation());
        response.setMessage("User registered successfully!");
        return response;
    }
}
