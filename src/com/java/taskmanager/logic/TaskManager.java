package com.java.taskmanager.logic;

import com.java.taskmanager.enums.*;
import com.java.taskmanager.entities.Process;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TaskManager {

    public static final int CAPACITY = 10;

    private TaskManagerSettings settings;
    private List<Process> tasksList;
    private int idCounter;

    public TaskManager() {
        this(new TaskManagerSettings());
    }

    public TaskManager(TaskManagerSettings settings) {
        this.settings = settings;
        tasksList = new ArrayList<Process>();
        idCounter = 0;
    }

    public int KillGroup(Priority priority) {
        List<Process> result = tasksList.stream().filter(x -> x.getPriority() == priority).collect(Collectors.toList());

        for (var task : result) {
            remove(task);
        }
        return result.size();
    }

    public boolean kill(int id) {
        Process process = tasksList.stream().filter(p -> p.getId() == id).findAny().orElse(null);
        if (process == null)
            return false;
        remove(process);
        return true;
    }

    public void killAll() {
        for (var task : tasksList) {
            task.kill();
        }
        tasksList.clear();
    }

    public List<Process> list() {
        return list(ProcessSortByField.NONE, SortDirection.ASC);
    }

    public int size() {
        return tasksList.size();
    }

    public List<Process> list(ProcessSortByField sortBy, SortDirection sortDirection) {
        List<Process> copy = new ArrayList<Process>();
        copy.addAll(tasksList);
        Comparator<Process> compare;
        switch (sortBy) {
        case NONE:
            compare = null;
            break;
        case ID:
            compare = Comparator.comparing(Process::getId);
            break;
        case PRIORITY:
            compare = Comparator.comparing(Process::getPriorityOrder);
            break;
        case CREATED_DATE_TIME:
            compare = Comparator.comparing(Process::getCreatedDateTime);
            break;
        default:
            compare = null;
            break;
        }
        if (compare != null) {
            if (sortDirection == SortDirection.DESC) {
                Collections.sort(copy, Collections.reverseOrder(compare));
            } else {
                Collections.sort(copy, compare);
            }

        }

        return copy;
    }

    public int add(Priority priority) {
        boolean canAdd = true;
        switch (settings.getAddTaskOption()) {
        case BLOCK_CAPACITY_REACHED:
            if (tasksList.size() >= CAPACITY)
                canAdd = false;
            break;
        case FIFO:
            if (tasksList.size() >= CAPACITY) {
                Comparator<Process> compareByTime = Comparator.comparing(Process::getCreatedDateTime);
                Optional<Process> task = tasksList.stream().sorted(compareByTime).findFirst();
                if (task.isPresent()) {
                    remove(task.get());
                }
            }
            break;
        case PRIORITY:
            if (tasksList.size() >= CAPACITY) {
                Comparator<Process> compareByPriority = Comparator.comparing(Process::getPriorityOrder)
                        // .reversed()
                        .thenComparing(Process::getCreatedDateTime);

                Optional<Process> task = tasksList.stream()
                        .filter(s -> s.getPriority().getValue() < priority.getValue()).sorted(compareByPriority)
                        .findFirst();
                if (task.isPresent()) {
                    remove(task.get());
                } else {
                    canAdd = false;
                }
            }
            break;
        }
        if (canAdd) {
            int id = ++idCounter;
            Process process = new Process(id, priority, LocalDateTime.now());
            tasksList.add(process);
            return id;
        }

        return -1;
    }

    private void remove(Process process) {
        if (tasksList.contains(process)) {
            process.kill();
            tasksList.remove(process);
        }
    }
}
