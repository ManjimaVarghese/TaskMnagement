// dto/LoginRequest.java
package com.example.taskmanager.dto;

import lombok.Data;
import lombok.Builder;


@Data
public class LoginRequest {
    private String email;
    private String password;
}
