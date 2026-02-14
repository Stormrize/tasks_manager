package core;

import java.time.Instant;
import java.util.UUID;

/**
 * Die Klasse {@code Task} repräsentiert eine Aufgabe mit einem eindeutigen {@link UUID},
 * einem Namen, einer Priorität und einem Ausführungszeitpunkt.
 * <p>
 * Aufgaben sind unveränderlich (immutable). Änderungen wie Name, Priorität oder Ausführungszeit
 * erzeugen eine neue {@code Task}-Instanz.
 * </p>
 *
 * @author Monke Vladyslav
 * @version 1.1
 */
public class Task {

    /** Eindeutige ID der Aufgabe */
    private final UUID id;

    /** Name der Aufgabe */
    private final String name;

    /** Priorität der Aufgabe (1 bis 5) */
    private final int priority;

    /** Zeitpunkt der Ausführung */
    private final Instant executeAt;

    /**
     * Erstellt eine neue Aufgabe mit automatisch generierter ID.
     *
     * @param name Name der Aufgabe (nicht null)
     * @param priority Priorität der Aufgabe (1 bis 5)
     * @param executeAT Zeitpunkt der Ausführung (nicht null)
     * @throws IllegalArgumentException falls Name null ist, Priorität außerhalb von 1-5 liegt oder executeAT null ist
     */
    public Task(String name, int priority, Instant executeAT) {
        if ((name != null) && (priority >=1 && priority <= 5) && executeAT != null) {
            this.id = UUID.randomUUID();
            this.name = name;
            this.priority = priority;
            this.executeAt = executeAT;
        } else throw new IllegalArgumentException("Ungültige Argumente für Task");
    }

    /**
     * Erstellt eine neue Aufgabe mit gegebener ID.
     *
     * @param id UUID der Aufgabe (nicht null)
     * @param name Name der Aufgabe (nicht null)
     * @param priority Priorität der Aufgabe (1 bis 5)
     * @param executeAT Zeitpunkt der Ausführung (nicht null)
     * @throws IllegalArgumentException falls eines der Argumente ungültig ist
     */
    public Task(UUID id, String name, int priority, Instant executeAT) {
        if ((name != null) && (priority >=1 && priority <= 5) && (executeAT != null) && id != null) {
            this.id = id;
            this.name = name;
            this.priority = priority;
            this.executeAt = executeAT;
        } else throw new IllegalArgumentException("Ungültige Argumente für Task");
    }

    /**
     * Gibt eine neue {@code Task}-Instanz mit geändertem Namen zurück.
     *
     * @param name neuer Name der Aufgabe (nicht null)
     * @return neue {@code Task}-Instanz mit geändertem Namen
     * @throws IllegalArgumentException falls der Name null ist
     */
    public Task withName(String name) {
        if (name != null)
            return new Task(id, name, priority, executeAt);
        else throw new IllegalArgumentException("Ungültiger Name");
    }

    /**
     * Gibt eine neue {@code Task}-Instanz mit geänderter Priorität zurück.
     *
     * @param priority neue Priorität (1 bis 5)
     * @return neue {@code Task}-Instanz mit geänderter Priorität
     * @throws IllegalArgumentException falls die Priorität nicht zwischen 1 und 5 liegt
     */
    public Task withPriority(int priority) {
        if (priority >= 1 && priority <=5)
            return new Task(id, name, priority, executeAt);
        else throw new IllegalArgumentException("Ungültige Priorität");
    }

    /**
     * Gibt eine neue {@code Task}-Instanz mit geändertem Ausführungszeitpunkt zurück.
     *
     * @param executeAt neuer Zeitpunkt der Ausführung (nicht null)
     * @return neue {@code Task}-Instanz mit geändertem Ausführungszeitpunkt
     * @throws IllegalArgumentException falls executeAt null ist
     */
    public Task withExecuteAt(Instant executeAt) {
        if (executeAt != null)
            return new Task(id, name, priority, executeAt);
        else throw new IllegalArgumentException("Ungültiger Ausführungszeitpunkt");
    }

    /**
     * Gibt den Namen der Aufgabe zurück.
     *
     * @return Name der Aufgabe
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt die Priorität der Aufgabe zurück.
     *
     * @return Priorität der Aufgabe
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Gibt den Zeitpunkt der Ausführung zurück.
     *
     * @return Ausführungszeitpunkt
     */
    public Instant getExecuteAT() {
        return executeAt;
    }

    /**
     * Gibt die eindeutige ID der Aufgabe zurück.
     *
     * @return UUID der Aufgabe
     */
    public UUID getId() {
        return id;
    }
}
