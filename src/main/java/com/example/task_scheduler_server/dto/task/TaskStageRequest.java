package com.example.task_scheduler_server.dto.task;

public class TaskStageRequest {

    private String name;
    private long sequenceNumber;

    public TaskStageRequest() {
    }

    public TaskStageRequest(String name, long sequenceNumber) {
        this.name = name;
        this.sequenceNumber = sequenceNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}
