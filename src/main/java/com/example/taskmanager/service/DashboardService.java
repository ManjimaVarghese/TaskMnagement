// service/DashboardService.java
package com.example.taskmanager.service;

import com.example.taskmanager.dto.DashboardResponse;
import com.example.taskmanager.model.Status;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TaskRepository taskRepo;
    private final UserRepository userRepo;

    public DashboardResponse getAdminDashboard() {
        long totalTasks = taskRepo.count();
        long completed = taskRepo.countByStatus(Status.COMPLETE);
        long inProgress = taskRepo.countByStatus(Status.INPROGRESS);
        long todo = taskRepo.countByStatus(Status.TODO);
        long totalUsers = userRepo.count();

        return DashboardResponse.builder()
                .totalTasks(totalTasks)
                .completedTasks(completed)
                .inProgressTasks(inProgress)
                .todoTasks(todo)
                .totalUsers(totalUsers)
                .build();
    }

    public DashboardResponse getUserDashboard() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByEmail(email).orElseThrow();

        long assigned = taskRepo.countByAssignedTo(user);
        long completed = taskRepo.countByAssignedToAndStatus(user, Status.COMPLETE);
        long inProgress = taskRepo.countByAssignedToAndStatus(user, Status.INPROGRESS);
        long todo = taskRepo.countByAssignedToAndStatus(user, Status.TODO);

        return DashboardResponse.builder()
                .assignedTasks(assigned)
                .completedTasks(completed)
                .inProgressTasks(inProgress)
                .todoTasks(todo)
                .build();
    }
}
