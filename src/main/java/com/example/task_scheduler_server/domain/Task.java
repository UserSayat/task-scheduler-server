package com.example.task_scheduler_server.domain;

import com.example.task_scheduler_server.common.TaskStage;
import com.example.task_scheduler_server.common.TaskStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;

    @ManyToOne()
    @JoinColumn(name = "executor_id")
    private User executor;

    private ZonedDateTime deadline;
    private Long priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;
    private String comment;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "stages", columnDefinition = "jsonb")
    private List<TaskStage> stages;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "current_stage", columnDefinition = "jsonb")
    private TaskStage currentStage;

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public Task() {
    }

    public Task(String name, String description, User executor,
                ZonedDateTime deadline, Long priority, TaskStatus status,
                String comment, List<TaskStage> stages, TaskStage currentStage,
                ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.name = name;
        this.description = description;
        this.executor = executor;
        this.deadline = deadline;
        this.priority = priority;
        this.status = status;
        this.comment = comment;
        this.stages = stages;
        this.currentStage = currentStage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(ZonedDateTime deadline) {
        this.deadline = deadline;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<TaskStage> getStages() {
        return stages;
    }

    public void setStages(List<TaskStage> stages) {
        this.stages = stages;
    }

    public TaskStage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(TaskStage currentStage) {
        this.currentStage = currentStage;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description)
                && Objects.equals(executor, task.executor) && Objects.equals(deadline, task.deadline)
                && Objects.equals(priority, task.priority) && status == task.status
                && Objects.equals(comment, task.comment) && Objects.equals(stages, task.stages)
                && Objects.equals(currentStage, task.currentStage) && Objects.equals(createdAt, task.createdAt)
                && Objects.equals(updatedAt, task.updatedAt);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
