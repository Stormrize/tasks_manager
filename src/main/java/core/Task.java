package core;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * @author Monke Vladyslav
 * @version 1.1
 */
public class Task {

    private final UUID id;

    private final String name;

    private final byte priority;

    private final Instant executeAt;

    private final Runnable action;

    private final Duration repeatInterval;

    public Task(String name, byte priority, Instant executeAT, Runnable action, Duration repeatInterval) {
        if ((name != null) && (priority >=1 && priority <= 5) && executeAT != null) {
            this.id = UUID.randomUUID();
            this.name = name;
            this.priority = priority;
            this.executeAt = executeAT;
            this.action = action;
            this.repeatInterval = repeatInterval;
        } else throw new IllegalArgumentException("Invalid arguments for task");
    }

    public Task(UUID id, String name, byte priority, Instant executeAT, Runnable action, Duration repeatInterval) {
        if ((name != null) && (priority >=1 && priority <= 5) && (executeAT != null) && id != null) {
            this.id = id;
            this.name = name;
            this.priority = priority;
            this.executeAt = executeAT;
            this.action = action;
            this.repeatInterval = repeatInterval;
        } else throw new IllegalArgumentException("Invalid arguments for task");
    }

    public Task withName(String name) {
        if (name != null)
            return new Task(id, name, priority, executeAt, action, repeatInterval);
        else throw new IllegalArgumentException("Invalid name");
    }

    public Task withPriority(byte priority) {
        if (priority >= 1 && priority <=5)
            return new Task(id, name, priority, executeAt, action, repeatInterval);
        else throw new IllegalArgumentException("Invalid priority");
    }

    public Task withExecuteAt(Instant executeAt) {
        if (executeAt != null)
            return new Task(id, name, priority, executeAt, action, repeatInterval);
        else throw new IllegalArgumentException("Invalid execution time");
    }

    public String getName() {
        return name;
    }

    public byte getPriority() {
        return priority;
    }

    public Instant getExecuteAT() {
        return executeAt;
    }

    public Runnable getAction() {
        return action;
    }

    public UUID getId() {
        return id;
    }

    public Duration getRepeatInterval() {
        return repeatInterval;
    }
}
