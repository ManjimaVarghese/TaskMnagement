// controller/TaskController.java
package com.example.taskmanager.controller;

import com.example.taskmanager.dto.*;
import com.example.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public TaskResponse createTask(@RequestBody TaskRequest request) {
        return taskService.createTask(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public TaskResponse updateTask(@PathVariable Long id, @RequestBody TaskRequest request) {
        return taskService.updateTask(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping("/assigned")
    public List<TaskResponse> getMyTasks() {
        return taskService.getTasksForCurrentUser();
    }
}
