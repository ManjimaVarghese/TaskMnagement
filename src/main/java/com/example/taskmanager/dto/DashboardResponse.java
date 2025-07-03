// dto/DashboardResponse.java
package com.example.taskmanager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {
    private long totalTasks;
    private long completedTasks;
    private long inProgressTasks;
    private long todoTasks;
    private long totalUsers;
    private long assignedTasks; // for current user
}
