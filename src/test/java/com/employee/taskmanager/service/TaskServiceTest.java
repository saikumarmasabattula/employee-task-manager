package com.employee.taskmanager.service;

import com.employee.taskmanager.entity.Task;
import com.employee.taskmanager.entity.User;
import com.employee.taskmanager.repository.TaskRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.when;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnTaskById() {

        User user = new User();
        user.setId(1L);
        user.setUsername("sai");

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Learn Spring Boot");
        task.setUser(user);

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        when(userService.getCurrentUser())
                .thenReturn(user);

        Task result = taskService.getTaskById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Learn Spring Boot", result.getTitle());
    }
}