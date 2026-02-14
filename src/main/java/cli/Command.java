package cli;

import core.Scheduler;

/**
 * Das Interface {@code Command} definiert einen allgemeinen Befehl,
 * der mit einer Scheduler-Instanz ausgeführt werden kann.
 * <p>
 * Jede Implementierung muss die Methode {@link #execute(String[], Scheduler)} bereitstellen,
 * die die Argumente des Befehls verarbeitet und entsprechende Aktionen auf dem Scheduler ausführt.
 * </p>
 *
 * @author Monke Vladyslav
 * @version 1.1
 */
public interface Command {

    /**
     * Führt den Befehl aus.
     *
     * @param args Array von Argumenten des Befehls
     * @param scheduler Scheduler-Instanz, auf der der Befehl ausgeführt wird
     */
    void execute(String[] args, Scheduler scheduler);
}
