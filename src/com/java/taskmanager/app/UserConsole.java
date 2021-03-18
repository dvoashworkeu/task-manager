package com.java.taskmanager.app;

import com.java.taskmanager.enums.*;
import com.java.taskmanager.logic.*;
import com.java.taskmanager.entities.Process;

import java.io.Console;
import java.util.List;

public class UserConsole {

    public <T extends Enum<T>> T getEnumValueLoop(Class<T> aEnum) {
        List<T> enumValues = java.util.Arrays.asList(aEnum.getEnumConstants());
        // Create the console object
        Console cnsl = System.console();

        if (cnsl == null) {
            System.out.println("No console available");
            return null;
        }
        boolean canParse = false;
        T enumInput = aEnum.getEnumConstants()[0];
        do {
            String enumStr = cnsl.readLine("type " + aEnum.getSimpleName() + " values:" + enumValues
                    + ", or type enter for default:" + enumInput.name() + ":");
            try {
                if (!enumStr.equals("")) {
                    enumInput = Enum.valueOf(aEnum, enumStr.toUpperCase());
                }

                canParse = true;
            } catch (IllegalArgumentException ex) {
                canParse = false;
                System.out.println("Value doesn't exists");
            }
        } while (!canParse);

        return enumInput;
    }

    public void Run(TaskManager manager) {
        // Create the console object
        Console cnsl = System.console();

        if (cnsl == null) {
            System.out.println("No console available");
            return;
        }

        System.out.println("The capacity of the task manager is : " + TaskManager.CAPACITY);

        System.out.println("available commands:");
        System.out.println("'exit': break the console");
        System.out.println("'add': to add a new process");
        System.out.println("'kill': to kill a process");
        System.out.println("'list': to display the list of processes");
        System.out.println("'killall': to kill all the processes");
        System.out.println("'killgroup': to kill all processes with specific priority");

        while (true) {
            // Read line
            String str = cnsl.readLine("Please type the command(exit,add,kill,list,killall,killgroup):");
            if (str.equals("exit"))
                break;
            switch (str) {
            case "add": {
                Priority priorityEnum = getEnumValueLoop(Priority.class);
                var id = manager.add(priorityEnum);
                if (id == -1)
                    System.out.println("Cannot add a new process!");
                else
                    System.out.printf("the process is added, id:%d, list size %d", id, manager.size());
                System.out.println();
                break;
            }
            case "list":
                ProcessSortByField processSortByFieldEnum = getEnumValueLoop(ProcessSortByField.class);
                SortDirection sortDirectionEnum = SortDirection.ASC;
                if (processSortByFieldEnum != ProcessSortByField.NONE) {
                    sortDirectionEnum = getEnumValueLoop(SortDirection.class);
                }
                var list = manager.list(processSortByFieldEnum, sortDirectionEnum);
                String leftAlignFormat = "| %-30s | %-8s | %-4d |%n";

                System.out.format("+--------------------------------+----------+------+%n");
                System.out.format("| Date Time                      | Priority | ID   |%n");
                System.out.format("+--------------------------------+----------+------+%n");
                for (Process p : list) {
                    System.out.format(leftAlignFormat, p.getCreatedDateTime(), p.getPriority(), p.getId());
                }
                System.out.format("+--------------------------------+----------+------+%n");
                break;
            case "killall":
                manager.killAll();
                System.out.println("All the processes were killed");
                break;
            case "killgroup": {
                Priority priorityEnum = getEnumValueLoop(Priority.class);
                int resCount = manager.KillGroup(priorityEnum);
                System.out.printf("%d processes were killed", resCount);
                System.out.println();
                break;
            }
            case "kill":
                str = cnsl.readLine("Please type id to kill:");
                int number = 0;
                try {
                    number = Integer.parseInt(str);

                } catch (NumberFormatException ex) {
                    System.out.println("id is not valid");
                    break;
                }
                if (manager.kill(number))
                    System.out.println("The id is killed");
                else
                    System.out.println("the id doesn't exist");
                break;
            default:
                System.out.println("The command is not recognized");
                break;
            }
        }

    }
}
