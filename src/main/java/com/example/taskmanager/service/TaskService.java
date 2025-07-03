// service/TaskService.java
package com.example.taskmanager.service;

import com.example.taskmanager.dto.*;
import com.example.taskmanager.model.*;
import com.example.taskmanager.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskResponse createTask(TaskRequest request) {
        User currentUser = getCurrentUser();
        User assignedTo = userRepository.findById(request.getAssignedToUserId())
                .orElseThrow(() -> new RuntimeException("Assigned user not found"));

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .priority(request.getPriority())
                .status(request.getStatus())
                .assignedTo(assignedTo)
                .createdBy(currentUser)
                .createdAt(LocalDateTime.now())
                .build();

        taskRepository.save(task);

        return toResponse(task);
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        User assignedTo = userRepository.findById(request.getAssignedToUserId())
                .orElseThrow(() -> new RuntimeException("Assigned user not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());
        task.setAssignedTo(assignedTo);

        return toResponse(taskRepository.save(task));
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<TaskResponse> getTasksForCurrentUser() {
        User currentUser = getCurrentUser();
        return taskRepository.findByAssignedTo(currentUser).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private TaskResponse toResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .priority(task.getPriority())
                .status(task.getStatus())
                .assignedToUsername(task.getAssignedTo().getUsername())
                .createdByUsername(task.getCreatedBy().getUsername())
                .createdAt(task.getCreatedAt())
                .build();
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
