package cli.commands;

import cli.Command;
import core.Scheduler;

/**
 * Die Klasse {@code ListCommand} implementiert das Interface {@link Command} und
 * ermöglicht das Ausgeben von Aufgaben im {@link Scheduler}.
 *
 * @author Monke Vladyslav
 * @version 1.1
 */

public class ListCommand implements Command {
    /**
     * Führt den List-Befehl aus.
     *
     * @param args Array von Argumenten des Befehls
     * @param scheduler die Scheduler-Instanz, in der die Aufgabe geändert werden soll
     */

    public void execute(String[] args, Scheduler scheduler) {
        scheduler.list();
    }
}
