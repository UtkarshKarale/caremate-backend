package com.example.hospital_management.framework.service;

import com.example.hospital_management.framework.dto.AuthRequest;
import com.example.hospital_management.framework.dto.AuthResponse;
import com.example.hospital_management.framework.security.JwtUtil;
import com.example.hospital_management.user.command.UserRegisterCommand;
import com.example.hospital_management.user.entity.User;
import com.example.hospital_management.user.repository.UserRepository;
import com.example.hospital_management.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(JwtUtil jwtUtil,
                       UserService userService,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRegisterCommand registerUser(UserRegisterCommand command) {
        return userService.registerUser(command);
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Authentication failed: Bad credentials for user");
        }

        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token, user.getRoles());
    }
}
