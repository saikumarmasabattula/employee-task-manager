package com.employee.taskmanager.controller;

import com.employee.taskmanager.dto.TaskResponseDTO;
import com.employee.taskmanager.entity.User;
import com.employee.taskmanager.mapper.TaskMapper;
import com.employee.taskmanager.repository.TaskRepository;
import com.employee.taskmanager.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin", description = "Admin APIs")
public class AdminController {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public AdminController(UserRepository userRepository,
                           TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    // Existing endpoint
@Operation(summary = "Admin Dashboard")
    @GetMapping("/dashboard")
    public String dashboard() {
        return "Welcome Admin!";
    }

    // Get all users
    @Operation(summary = "Get All Users")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    // Get all tasks
    @Operation(summary = "Get All Tasks")   
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {

        List<TaskResponseDTO> tasks = taskRepository.findAll()
                .stream()
                .map(TaskMapper::toDTO)
                .toList();

        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Delete Task") 
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {

        taskRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}