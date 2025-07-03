package com.example.taskmanager.controller;

import com.example.taskmanager.dto.UpdateUserRequest;
import com.example.taskmanager.dto.UserInfoResponse;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor

public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/info")
public UserInfoResponse getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
    User user = userRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));
    return new UserInfoResponse(user.getUsername(), user.getEmail(), user.getRole().name());
}


@PutMapping("/update/{id}")
public UserInfoResponse updateUserById(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());

    user = userRepository.save(user);
    return new UserInfoResponse(user.getUsername(), user.getEmail(), user.getRole().name());
}

@DeleteMapping("/delete/{id}")
public String deleteUserById(@PathVariable Long id) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

    userRepository.delete(user);
    return "User with ID " + id + " deleted successfully.";
}


}