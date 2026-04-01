package core;

import storage.Data;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Monke Vladyslav
 * @version 1.1
 */
public class Scheduler {

    private final List<Task> tasks = new ArrayList<>();

    private final Map<UUID, ScheduledFuture<?>> scheduled = new HashMap<>();

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public synchronized List<Task> snapshot() {
        return new ArrayList<>(tasks);
    }

    public void list() {
        for (Task task : snapshot()) {
            ZonedDateTime local = task.getExecuteAT().atZone(ZoneId.systemDefault());
            System.out.println(
                    "ID : " + task.getId() +
                            "\nName : " + task.getName() +
                            "\nPriority : " + task.getPriority() +
                            "\nExecution: " + local +
                            "\nAction " + task.getAction().toString() +
                            "\nRepeatInterval " + task.getRepeatInterval()
            );
        }
    }

    public void addTask(Task task) {
        synchronized (this) {
            tasks.add(task);
            scheduleTask(task);
        }
    }

    public void addTask(String name, byte priority, Instant executeAT, Runnable action, Duration repeatInterval) {
        Task task = new Task(name, priority, executeAT, action, repeatInterval);
        addTask(task);
    }

    public void addTask(UUID id, String name, byte priority, Instant executeAT, Runnable action, Duration repeatInterval) {
        Task task = new Task(id, name, priority, executeAT, action, repeatInterval);
        addTask(task);
    }

    public void remove(UUID id) {
        ScheduledFuture<?> future;
        synchronized (this) {
            tasks.removeIf(task -> task.getId().equals(id));
            future = scheduled.remove(id);
        }
        if (future != null) future.cancel(false);
    }

    public void remove(String name) {
        List<Task> toRemove;
        synchronized (this) {
            toRemove = new ArrayList<>();
            for (Task task : tasks) {
                if (task.getName().equals(name)) toRemove.add(task);
            }
            tasks.removeAll(toRemove);
            for (Task task : toRemove) {
                ScheduledFuture<?> future = scheduled.remove(task.getId());
                if (future != null) future.cancel(false);
            }
        }
    }

    public void remove(int priority) {
        List<Task> toRemove;
        synchronized (this) {
            toRemove = new ArrayList<>();
            for (Task task : tasks) {
                if (task.getPriority() == priority) toRemove.add(task);
            }
            tasks.removeAll(toRemove);
            for (Task task : toRemove) {
                ScheduledFuture<?> future = scheduled.remove(task.getId());
                if (future != null) future.cancel(false);
            }
        }
    }

    public synchronized void sortByPriority() {
        tasks.sort(Comparator.comparingInt(Task::getPriority));
    }

    public synchronized void sortByName() {
        tasks.sort(Comparator.comparing(Task::getName));
    }

    public synchronized void sortByTime() {
        tasks.sort(Comparator.comparing(Task::getExecuteAT));
    }

    private void scheduleTask(Task task) {
        if (scheduled.containsKey(task.getId())) return;
        long delay = task.getExecuteAT().toEpochMilli() - Instant.now().toEpochMilli();
        if (delay < 0) delay = 0;

        ScheduledFuture<?> f = executor.schedule(() -> {
            try {
                task.getAction().run();
                if (task.getRepeatInterval() != null) {
                    String name = task.getName();
                    byte priority = task.getPriority();
                    Runnable action = task.getAction();
                    Duration repeatInterval = task.getRepeatInterval();
                    Instant executeAt = task.getExecuteAT().plus(repeatInterval);
                    Task next = new Task(name, priority, executeAt, action, repeatInterval);
                    addTask(next);
                    Data.saveTasks(this);
                }
            } finally {
                synchronized (this) {
                    scheduled.remove(task.getId());
                    remove(task.getId());
                }
            }
        }, delay, TimeUnit.MILLISECONDS);
        scheduled.put(task.getId(), f);
    }

    public void start() {
        synchronized (this) {
            for(Task task : tasks) {
                scheduleTask(task);
            }
        }
    }

    public void shutdown() {
        executor.shutdown();
    }

    public void changeName(UUID id, String newName) {
        ScheduledFuture<?> future;
        Task newTask;
        for (Task task : snapshot()) {
            if (task.getId().equals(id)) {
                newTask = task.withName(newName);
                tasks.remove(task);
                future = scheduled.remove(id);
                if (future != null) future.cancel(false);
                addTask(newTask);
                break;
            }
        }
    }

    public void changePriority(UUID id, byte newPriority) {
        ScheduledFuture<?> future;
        Task newTask;
        for (Task task : snapshot()) {
            if (task.getId().equals(id)) {
                newTask = task.withPriority(newPriority);
                tasks.remove(task);
                future = scheduled.remove(id);
                if (future != null) future.cancel(false);
                addTask(newTask);
                break;
            }
        }
    }
//     public void changeExecuteAt(UUID id, String addTime) {
//        boolean minus = addTime.startsWith("-");
//        String value = addTime.substring(1);
//        int number = Integer.parseInt(value.replaceAll("\\D", ""));
//        String unit = value.replaceAll("\\d", "");
//        int seconds = switch (unit) {
//            case "min" -> number * 60;
//            case "h" -> number * 3600;
//            default -> number;
//        };
//        for (Task task : tasks) {
//            if (task.getId().equals(id)) {
//                task.setExecuteAT(
//                        minus ?
//                                task.getExecuteAT().minusSeconds(seconds) :
//                                task.getExecuteAT().plusSeconds(seconds));
//                break;
//            }
//        }
//     }
}