package com.employee.taskmanager.repository;

import com.employee.taskmanager.entity.Task;
import com.employee.taskmanager.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByTitleContainingIgnoreCase(String keyword);
    Page<Task> findByUser(User user, Pageable pageable);
    Page<Task> findByUserAndTitleContainingIgnoreCase(
            User user,
            String keyword,
            Pageable pageable
    );

    Page<Task> findByUserAndCompleted(
        User user,
        boolean completed,
        Pageable pageable
    );

    List<Task> findByCompleted(boolean completed);
    Page<Task> findByCompleted(boolean completed, Pageable pageable);

   @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Task> searchByTitle(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM task WHERE completed = :completed",
        nativeQuery = true)
    List<Task> findTasksByCompletedNative(@Param("completed") boolean completed);

    Page<Task> findByTitleContainingIgnoreCaseAndCompleted(
        String keyword,
        boolean completed,
        Pageable pageable
    );
}
