package com.employee.taskmanager.service;

import com.employee.taskmanager.entity.Task;
import com.employee.taskmanager.entity.User;
import com.employee.taskmanager.exception.TaskNotFoundException;
import com.employee.taskmanager.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class TaskService {
    private static final Logger logger =
        LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository,
                       UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    // Returns task only if it belongs to the logged-in user
    public Task getTaskById(Long id) {
        logger.info("Fetching task {}", id);
        User currentUser = userService.getCurrentUser();

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found with id " + id));

        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not allowed to access this task.");
        }

        return task;
    }

    public Task createTask(Task task) {

        logger.info("Creating task: {}", task.getTitle());

        Task savedTask = taskRepository.save(task);

        logger.info("Task created successfully with ID: {}", savedTask.getId());

        return savedTask;
    }

    public Task updateTask(Long id, Task updatedTask) {
        logger.info("Updating task with ID: {}", id);
        Task task = getTaskById(id);

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setCompleted(updatedTask.isCompleted());
        Task savedTask = taskRepository.save(task);

        logger.info("Task updated successfully: {}", savedTask.getId());

        return savedTask;
    }

    public void deleteTask(Long id) {

        logger.info("Deleting task {}", id);

        Task task = getTaskById(id);

        taskRepository.delete(task);

        logger.info("Task deleted successfully.");
    }

    public Page<Task> getTasks(Pageable pageable) {
        logger.info("Fetching tasks for user: {}",
        userService.getCurrentUser().getUsername());
        return taskRepository.findByUser(
                userService.getCurrentUser(),
                pageable
        );
    }

    public List<Task> searchTasks(String keyword) {
        logger.info("Searching tasks with keyword: {}", keyword);
        return taskRepository
                .findByTitleContainingIgnoreCase(keyword);
    }

    public Page<Task> searchTasks(String keyword, Pageable pageable) {

        return taskRepository.findByUserAndTitleContainingIgnoreCase(
                userService.getCurrentUser(),
                keyword,
                pageable
        );
    }

    public Page<Task> getTasksByStatus(boolean completed, Pageable pageable) {

        return taskRepository.findByUserAndCompleted(
                userService.getCurrentUser(),
                completed,
                pageable
        );
    }

    public List<Task> getTasksByCompleted(boolean completed) {
        logger.info("Fetching completed tasks: {}", completed);
        return taskRepository.findByCompleted(completed);
    }

    public List<Task> searchByTitleJPQL(String keyword) {
        logger.info("Searching tasks using JPQL: {}", keyword);
        return taskRepository.searchByTitle(keyword);
    }

    public List<Task> getCompletedTasksNative(boolean completed) {
        logger.info("Fetching tasks using native query. Completed={}", completed);
        return taskRepository.findTasksByCompletedNative(completed);
    }

}