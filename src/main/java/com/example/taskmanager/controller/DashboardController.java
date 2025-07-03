// controller/DashboardController.java
package com.example.taskmanager.controller;

import com.example.taskmanager.dto.DashboardResponse;
import com.example.taskmanager.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public DashboardResponse getAdminDashboard() {
        return dashboardService.getAdminDashboard();
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    public DashboardResponse getUserDashboard() {
        return dashboardService.getUserDashboard();
    }
}
