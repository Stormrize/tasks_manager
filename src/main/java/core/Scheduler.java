package core;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Die Klasse {@code Scheduler} ermöglicht das Verwalten und Ausführen von Aufgaben ({@link Task})
 * zu geplanten Zeitpunkten. Aufgaben können hinzugefügt, entfernt, sortiert und geändert werden.
 * <p>
 * Jede Aufgabe wird intern mit einem {@link ScheduledExecutorService} geplant.
 * </p>
 *
 * @author Monke Vladyslav
 * @version 1.1
 */
public class Scheduler {

    /** Liste aller Aufgaben */
    private final List<Task> tasks = new ArrayList<>();

    /** Map für geplante Aufgaben, die mit {@link ScheduledFuture} verwaltet werden */
    private final Map<UUID, ScheduledFuture<?>> scheduled = new HashMap<>();

    /** Executor für die zeitgesteuerte Ausführung von Aufgaben */
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    /**
     * Gibt eine Kopie aller Aufgaben zurück.
     *
     * @return eine neue Liste aller Aufgaben
     */
    public synchronized List<Task> snapshot() {
        return new ArrayList<>(tasks);
    }

    /**
     * Listet alle Aufgaben auf der Konsole mit ID, Name, Priorität und Ausführungszeit auf.
     */
    public void list() {
        for (Task task : snapshot()) {
            ZonedDateTime local = task.getExecuteAT().atZone(ZoneId.systemDefault());
            System.out.println(
                    "ID : " + task.getId() +
                            "\nName : " + task.getName() +
                            "\nPriority : " + task.getPriority() +
                            "\nExecution: " + local
            );
        }
    }

    /**
     * Fügt eine Aufgabe hinzu und plant deren Ausführung.
     *
     * @param task die hinzuzufügende Aufgabe
     */
    public void addTask(Task task) {
        synchronized (this) {
            tasks.add(task);
            scheduleTask(task);
        }
    }

    /**
     * Erstellt und fügt eine Aufgabe anhand von Name, Priorität und Ausführungszeit hinzu.
     *
     * @param name Name der Aufgabe
     * @param priority Priorität der Aufgabe
     * @param executeAT Zeitpunkt der Ausführung
     */
    public void addTask(String name, int priority, Instant executeAT) {
        Task task = new Task(name, priority, executeAT);
        addTask(task);
    }

    /**
     * Erstellt und fügt eine Aufgabe mit bestimmter ID hinzu.
     *
     * @param id UUID der Aufgabe
     * @param name Name der Aufgabe
     * @param priority Priorität der Aufgabe
     * @param executeAT Zeitpunkt der Ausführung
     */
    public void addTask(UUID id, String name, int priority, Instant executeAT) {
        Task task = new Task(id, name, priority, executeAT);
        addTask(task);
    }

    /**
     * Entfernt eine Aufgabe anhand ihrer ID.
     *
     * @param id UUID der zu entfernenden Aufgabe
     */
    public void remove(UUID id) {
        ScheduledFuture<?> future;
        synchronized (this) {
            tasks.removeIf(task -> task.getId().equals(id));
            future = scheduled.remove(id);
        }
        if (future != null) future.cancel(false);
    }

    /**
     * Entfernt alle Aufgaben mit dem angegebenen Namen.
     *
     * @param name Name der zu entfernenden Aufgaben
     */
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

    /**
     * Entfernt alle Aufgaben mit der angegebenen Priorität.
     *
     * @param priority Priorität der zu entfernenden Aufgaben
     */
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

    /**
     * Sortiert die Aufgaben nach Priorität (aufsteigend).
     */
    public synchronized void sortByPriority() {
        tasks.sort(Comparator.comparingInt(Task::getPriority));
    }

    /**
     * Sortiert die Aufgaben alphabetisch nach Name.
     */
    public synchronized void sortByName() {
        tasks.sort(Comparator.comparing(Task::getName));
    }

    /**
     * Sortiert die Aufgaben nach Ausführungszeit (früheste zuerst).
     */
    public synchronized void sortByTime() {
        tasks.sort(Comparator.comparing(Task::getExecuteAT));
    }

    /**
     * Plant die Ausführung einer Aufgabe über den Executor.
     *
     * @param task die zu planende Aufgabe
     */
    private void scheduleTask(Task task) {
        if (scheduled.containsKey(task.getId())) return;
        long delay = task.getExecuteAT().toEpochMilli() - Instant.now().toEpochMilli();
        if (delay < 0) delay = 0;

        ScheduledFuture<?> f = executor.schedule(() -> {
            try {
                System.out.println("\nEXEC " + task.getName());
            } finally {
                synchronized (this) {
                    scheduled.remove(task.getId());
                    remove(task.getId());
                }
            }
        }, delay, TimeUnit.MILLISECONDS);
        scheduled.put(task.getId(), f);
    }

    /**
     * Startet alle bisher hinzugefügten Aufgaben.
     */
    public void start() {
        synchronized (this) {
            for(Task task : tasks) {
                scheduleTask(task);
            }
        }
    }

    /**
     * Beendet den Scheduler und den Executor.
     */
    public void shutdown() {
        executor.shutdown();
    }

    /**
     * Ändert den Namen einer Aufgabe.
     *
     * @param id UUID der Aufgabe
     * @param newName neuer Name
     */
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

    /**
     * Ändert die Priorität einer Aufgabe.
     *
     * @param id UUID der Aufgabe
     * @param newPriority neue Priorität
     */
    public void changePriority(UUID id, int newPriority) {
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