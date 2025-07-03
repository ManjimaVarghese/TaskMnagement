// dto/TaskResponse.java
package com.example.taskmanager.dto;

import com.example.taskmanager.model.Priority;
import com.example.taskmanager.model.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Priority priority;
    private Status status;
    private String assignedToUsername;
    private String createdByUsername;
    private LocalDateTime createdAt;
}
