package com.java.taskmanager.app;

import com.java.taskmanager.enums.*;
import com.java.taskmanager.logic.*;
import com.java.taskmanager.entities.*;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {

        Configuration config = new Configuration();
        config.load();
        String taskListType = config.get("add-task-option");
        System.out.println(taskListType);
        TaskManagerSettings settings = new TaskManagerSettings();
        settings.setAddTaskOption(AddTaskOption.valueOf(taskListType));
        TaskManager taskManager = new TaskManager(settings);

        UserConsole console = new UserConsole();
        console.Run(taskManager);

    }

}
