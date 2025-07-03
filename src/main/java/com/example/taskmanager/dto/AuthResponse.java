package com.example.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor  // ✅ Required for frameworks that use reflection
public class AuthResponse {
    private String token;
}
