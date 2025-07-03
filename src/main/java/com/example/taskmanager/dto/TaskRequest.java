package com.example.taskmanager.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import com.example.taskmanager.model.Priority;
import com.example.taskmanager.model.Status;
import com.example.taskmanager.model.User;


@Data
@Builder
public class TaskRequest {
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Priority priority; 
    private Status status;
    private Long assignedToUserId;
}
