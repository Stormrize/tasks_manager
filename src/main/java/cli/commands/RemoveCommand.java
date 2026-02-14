package cli.commands;

import cli.Command;
import core.Scheduler;
import storage.Data;

import java.util.UUID;

/**
 * Die Klasse {@code RemoveCommand} implementiert das Interface {@link Command} und
 * ermöglicht das Entfernen von Aufgaben aus dem {@link Scheduler} anhand von Name,
 * Priorität oder UUID.
 * <p>
 * Nach dem Entfernen wird der aktuelle Stand des Schedulers in der Datenbank/Datei gespeichert.
 * </p>
 *
 * @author Monke Vladyslav
 * @version 1.1
 */
public class RemoveCommand implements Command {

    /**
     * Führt den Remove-Befehl aus.
     * <p>
     * Die Argumente werden folgendermaßen interpretiert:
     * <ul>
     *     <li>{@code --byName} : entfernt Aufgaben mit dem angegebenen Namen</li>
     *     <li>{@code --byPriority} : entfernt Aufgaben mit der angegebenen Priorität</li>
     *     <li>{@code --byUUID} : entfernt die Aufgabe mit der angegebenen UUID</li>
     * </ul>
     * Nach erfolgreichem Entfernen werden die Änderungen mittels {@link Data#saveFile(Scheduler)} gespeichert.
     * </p>
     *
     * @param args Array von Argumenten des Befehls
     * @param scheduler die Scheduler-Instanz, aus der Aufgaben entfernt werden sollen
     */
    public void execute(String[] args, Scheduler scheduler) {
        StringBuilder name = new StringBuilder();
        if (args.length < 3) {
            System.out.println("help"); //to do
            return;
        }
        for(int i = 2; i < args.length; i++) {
            if (i == args.length - 1) {
                name.append(args[i]);
            } else {
                name.append(args[i]).append(" ");
            }
        }
        switch(args[1]) {
            case "--byName" -> scheduler.remove(name.toString());
            case "--byPriority" -> scheduler.remove(Integer.parseInt(name.toString()));
            case "--byUUID" -> scheduler.remove(UUID.fromString(name.toString()));
        }
        Data.saveFile(scheduler);
    }
}