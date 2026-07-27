package com.example.task_scheduler_server.common;

import java.util.Objects;

public class TaskStage {

    private String name;
    private long sequenceNumber;

    public TaskStage() {
    }

    public TaskStage(String name, long sequenceNumber) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskStage taskStage)) return false;
        return sequenceNumber == taskStage.sequenceNumber && Objects.equals(name, taskStage.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
