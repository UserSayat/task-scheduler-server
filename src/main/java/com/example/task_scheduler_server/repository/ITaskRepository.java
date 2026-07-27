package com.example.task_scheduler_server.repository;

import com.example.task_scheduler_server.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {
    @Query(value = """
            SELECT t.id, t.name, t.description, t.executor_id, t.deadline,
            t.priority, t.status, t.comment, t.stages, t.current_stage, t.created_at, t.updated_at FROM tasks t
            WHERE t.executor_id = :id
            """, nativeQuery = true)
    List<Task> findTasksByExecutorId(long id);
}
