package cli.commands;

import cli.Command;
import core.Scheduler;
import storage.Data;

/**
 * Die Klasse {@code SortCommand} implementiert das Interface {@link Command} und
 * ermöglicht das Sortieren von Aufgaben im {@link Scheduler} anhand von Name,
 * Priorität oder Zeit.
 * <p>
 * Der Befehl kann Aufgaben nach der Priorität, Namen, oder Dringlichkeit sortieren
 *
 * Nach dem Sortieren wird der aktuelle Stand des Schedulers in der Datenbank/Datei gespeichert.
 * </p>
 *
 * @author Monke Vladyslav
 * @version 1.1
 */

public class SortCommand implements Command {

    /**
     * Führt den Sort-Befehl aus.
     * <p>
     * Die Argumente werden folgendermaßen interpretiert:
     * <ul>
     *     <li>{@code --byName} : Sortiert Aufgaben mit nach dem Namen</li>
     *     <li>{@code --byPriority} : Sortiert Aufgaben nach der Priorität</li>
     *     <li>{@code --byTime} : Sortiert die Aufgaben nach der Zeit</li>
     * </ul>
     * Nach erfolgreichem Sortieren werden die Änderungen mittels {@link Data#saveFile(Scheduler)} gespeichert.
     * </p>
     *
     * @param args Array von Argumenten des Befehls
     * @param scheduler die Scheduler-Instanz, aus der Aufgaben entfernt werden sollen
     */

    public void execute(String[] args, Scheduler scheduler) {
        if (args.length < 2) {
            System.out.println("help");
        }
        switch(args[1]) {
            case "--byName" -> scheduler.sortByName();
            case "--byPriority" -> scheduler.sortByPriority();
            case "--byTime" -> scheduler.sortByTime();
        }
        Data.saveFile(scheduler);
    }
}
