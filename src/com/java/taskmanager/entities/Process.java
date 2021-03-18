package com.java.taskmanager.entities;

import com.java.taskmanager.enums.*;
import java.time.LocalDateTime;

public final class Process {

    private final int id;

    private final Priority priority;

    private final LocalDateTime createdDateTime;

    public Process(int id, Priority priority, LocalDateTime createdDateTime) {
        this.priority = priority;
        this.id = id;
        this.createdDateTime = createdDateTime;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public Priority getPriority() {
        return priority;
    }

    public int getPriorityOrder() {
        return priority.getValue();
    }

    public void kill() {

    }
}
