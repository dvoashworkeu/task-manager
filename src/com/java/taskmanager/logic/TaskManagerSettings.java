package com.java.taskmanager.logic;

import com.java.taskmanager.enums.*;

public class TaskManagerSettings {
    private AddTaskOption addTaskOption;

    public TaskManagerSettings() {
        setDefault();
    }

    public void setDefault() {
        addTaskOption = AddTaskOption.BLOCK_CAPACITY_REACHED;
    }

    // Getter
    public AddTaskOption getAddTaskOption() {
        return this.addTaskOption;
    }

    // Setter
    public void setAddTaskOption(AddTaskOption addTaskOption) {
        this.addTaskOption = addTaskOption;
    }
}
