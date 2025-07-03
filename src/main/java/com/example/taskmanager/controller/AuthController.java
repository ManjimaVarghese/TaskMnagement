// controller/AuthController.java
package com.example.taskmanager.controller;

import com.example.taskmanager.dto.*;
import com.example.taskmanager.model.*;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

   @PostMapping("/register")
public AuthResponse register(@RequestBody RegisterRequest request) {
    System.out.println("Register request: " + request);  // Debug

    Role role;
    try {
        role = Role.valueOf(request.getRole().toUpperCase()); // Safe
    } catch (IllegalArgumentException e) {
        throw new RuntimeException("Invalid role: " + request.getRole());
    }

    User user = User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(role)
            .createdAt(LocalDateTime.now())
            .build();

    try {
        userRepo.save(user);
    } catch (Exception e) {
        e.printStackTrace();  // 🔴 This will now show what is breaking in the logs
        throw new RuntimeException("Could not register user: " + e.getMessage());
    }

    String jwt = jwtService.generateToken(user.getEmail());
    return new AuthResponse(jwt);
}


    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        String jwt = jwtService.generateToken(request.getEmail());
        return new AuthResponse(jwt);
    }
    


    
}
