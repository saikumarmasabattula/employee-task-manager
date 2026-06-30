package com.employee.taskmanager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TaskmanagerApplicationTests {

    @Test
    void applicationMainShouldStartWithoutThrowing() {
        assertDoesNotThrow(() -> TaskmanagerApplication.main(new String[]{}));
    }
}
