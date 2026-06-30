package com.employee.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import com.employee.taskmanager.enums.Priority;
import java.time.LocalDate;

public class TaskRequestDTO {

    private Priority priority;

    private LocalDate dueDate;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private boolean completed;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public Priority getPriority() {
    return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}