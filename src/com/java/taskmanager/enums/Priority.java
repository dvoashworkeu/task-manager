package com.java.taskmanager.enums;

public enum Priority {
    LOW(1), MEDIUM(2), HIGH(3);

    private final int value;

    private Priority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
