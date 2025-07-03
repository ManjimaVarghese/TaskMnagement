package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.model.Status; // ✅ add this

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedTo(User user);

    // Dashboard metrics
    long countByStatus(Status status);
    long countByAssignedTo(User user);
    long countByAssignedToAndStatus(User user, Status status);
}
