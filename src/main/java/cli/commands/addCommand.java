package cli.commands;

import cli.Command;
import core.Scheduler;
import storage.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse {@code addCommand} implementiert das Interface {@link Command} und
 * ermöglicht das Hinzufügen von Aufgaben aus dem {@link Scheduler}
 * <p>
 * Nach dem Hinzufügen wird der aktuelle Stand des Schedulers in der Datenbank/Datei gespeichert.
 * </p>
 *
 * @author Monke Vladyslav
 * @version 1.1
 */

public class addCommand implements Command {

    /**
     * Führt den Hinzufügen-Befehl aus.
     * <p>
     * * Nach erfolgreichem Entfernen werden die Änderungen mittels {@link Data#saveFile(Scheduler)} gespeichert.
     * </p>
     *
     * @param args Array von Argumenten des Befehls
     * @param scheduler die Scheduler-Instanz, aus der Aufgaben entfernt werden sollen
     */

    public void execute (String[] args, Scheduler scheduler) {

        if (args.length < 2) {
            throw new IllegalArgumentException("not enough arguments"); // to do
        }

        List<StringBuilder> parts = new ArrayList<>();
        int counter = 0;
        for (int i = 1; i < args.length; i++) {
            if (args[i].startsWith("--")) {
                parts.add(new StringBuilder());
                counter = 0;
                continue;
            }
            String add = counter > 0 ? " " + args[i] : args[i];
            parts.getLast().append(add);
            counter++;
        }

        if (parts.size() < 3) {
            throw new IllegalArgumentException("minimum 3 arguments");
        }
        String value = parts.get(2).toString();
        int seconds = Integer.parseInt(value.replaceAll("\\D", ""));
        String time = value.replaceAll("\\d", "");
        if (time.equals("min")) {
            seconds *= 60;
        } else if (time.equals("h")) {
            seconds = seconds * 3600;
        }
        Instant executeAt = Instant.now().plusSeconds(seconds);
        scheduler.addTask(parts.get(0).toString(), Integer.parseInt(parts.get(1).toString()), executeAt);
        Data.saveFile(scheduler);
    }
}
