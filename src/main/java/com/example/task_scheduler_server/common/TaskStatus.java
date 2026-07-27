package com.example.task_scheduler_server.common;

public enum TaskStatus {

    TO_DO, // A new task is awaiting the start.
    IN_PROGRESS, // The person responsible is working on it right now.
    ON_HOLD, // Work has been suspended.
    BLOCKED, // Cannot continue due to external reasons.
    IN_REVIEW, // Under review by the supervisor or client.
    DONE, // The goal has been successfully achieved.
    CANCELED // The task is no longer relevant.
}
