package com.example.task_scheduler_server.dto.task;

import com.example.task_scheduler_server.common.TaskStage;
import com.example.task_scheduler_server.common.TaskStatus;
import com.example.task_scheduler_server.domain.User;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

public class TaskResponse {

    private long id;
    private String name;
    private String description;
    private Long executorId;
    private ZonedDateTime deadline;
    private Long priority;
    private TaskStatus status;
    private String comment;
    private List<TaskStage> stages;
    private TaskStage currentStage;

    public TaskResponse() {
    }

    public TaskResponse(long id, String name, String description, Long executorId, ZonedDateTime deadline, Long priority, TaskStatus status, String comment, List<TaskStage> stages, TaskStage currentStage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.executorId = executorId;
        this.deadline = deadline;
        this.priority = priority;
        this.status = status;
        this.comment = comment;
        this.stages = stages;
        this.currentStage = currentStage;
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

    public Long getExecutorId() {
        return executorId;
    }

    public void setExecutorId(Long executorId) {
        this.executorId = executorId;
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
}
