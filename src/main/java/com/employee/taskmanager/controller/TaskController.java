package com.employee.taskmanager.controller;
import com.employee.taskmanager.service.UserService;
import com.employee.taskmanager.dto.TaskRequestDTO;
import com.employee.taskmanager.dto.TaskResponseDTO;
import com.employee.taskmanager.entity.Task;
import com.employee.taskmanager.mapper.TaskMapper;
import com.employee.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Task Management", description = "CRUD Operations for Tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    public TaskController(TaskService taskService,
                        UserService userService) {

        this.taskService = taskService;
        this.userService = userService;
        }
        // Create Task
        @Operation(summary = "Create Task")
        @ApiResponse(responseCode = "200", description = "Task created")
        @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(
            @Valid @RequestBody TaskRequestDTO dto) {

        Task task = TaskMapper.toEntity(dto);
        task.setUser(userService.getCurrentUser());
        Task createdTask = taskService.createTask(task);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TaskMapper.toDTO(createdTask));
    }

    @Operation(summary = "Get All Tasks")
        @ApiResponse(responseCode = "200", description = "Tasks fetched")
    @GetMapping
    public ResponseEntity<Page<TaskResponseDTO>> getAllTasks(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction

    ) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TaskResponseDTO> result =
                taskService.getTasks(pageable)
                        .map(TaskMapper::toDTO);

        return ResponseEntity.ok(result);
    }
    @Operation(summary = "Search Tasks")
        @GetMapping("/search")
    public ResponseEntity<Page<TaskResponseDTO>> searchTasks(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction
    ) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TaskResponseDTO> tasks = taskService
                .searchTasks(keyword, pageable)
                .map(TaskMapper::toDTO);

        return ResponseEntity.ok(tasks);
    }
    @Operation(summary = "Filter Tasks")        
    @GetMapping("/filter")
    public ResponseEntity<List<TaskResponseDTO>> filterTasks(

            @RequestParam boolean completed

    ) {

        List<TaskResponseDTO> tasks = taskService
                .getTasksByCompleted(completed)
                .stream()
                .map(TaskMapper::toDTO)
                .toList();

        return ResponseEntity.ok(tasks);
    }
        @Operation(summary = "Get Task By ID")
        @ApiResponse(responseCode = "200", description = "Task found")
        @ApiResponse(responseCode = "404", description = "Task not found")
        @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(
            @PathVariable Long id) {

        Task task = taskService.getTaskById(id);

        return ResponseEntity.ok(TaskMapper.toDTO(task));
    }

    @Operation(summary = "Update Task")
        @ApiResponse(responseCode = "200", description = "Task updated")
        @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDTO dto) {

        Task updatedTask = TaskMapper.toEntity(dto);

        Task savedTask = taskService.updateTask(id, updatedTask);

        return ResponseEntity.ok(TaskMapper.toDTO(savedTask));
    }

    @Operation(summary = "Delete Task")
@ApiResponse(responseCode = "200", description = "Task deleted")
@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/jpql-search")
    public ResponseEntity<List<TaskResponseDTO>> jpqlSearch(

            @RequestParam String keyword

    ) {

        List<TaskResponseDTO> tasks = taskService
                .searchByTitleJPQL(keyword)
                .stream()
                .map(TaskMapper::toDTO)
                .toList();

        return ResponseEntity.ok(tasks);
    }
    @Operation(summary = "Filter Tasks by Completion Status")
    @GetMapping("/native-filter")
    public ResponseEntity<List<TaskResponseDTO>> nativeFilter(

            @RequestParam boolean completed

    ) {

        List<TaskResponseDTO> tasks = taskService
                .getCompletedTasksNative(completed)
                .stream()
                .map(TaskMapper::toDTO)
                .toList();

        return ResponseEntity.ok(tasks);
    }
}