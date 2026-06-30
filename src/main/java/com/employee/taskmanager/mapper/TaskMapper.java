package com.employee.taskmanager.mapper;

import com.employee.taskmanager.dto.TaskRequestDTO;
import com.employee.taskmanager.dto.TaskResponseDTO;
import com.employee.taskmanager.entity.Task;

public class TaskMapper {

    public static Task toEntity(TaskRequestDTO dto) {
        
        Task task = new Task();

        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.isCompleted());

        return task;
    }

    public static TaskResponseDTO toDTO(Task task) {

        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        dto.setPriority(task.getPriority());
        dto.setDueDate(task.getDueDate());
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.isCompleted());

        return dto;
    }
}