package core.commands;

import cli.Command;
import core.runnable.RunnableRegistry;
import core.Scheduler;
import core.runnable.WallpapersChanger.WallpaperHelper;
import storage.Data;

import java.time.Duration;
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

public class AddCommand implements Command {

    /**
     * Führt den Hinzufügen-Befehl aus.
     * <p>
     * * Nach erfolgreichem Entfernen werden die Änderungen mittels {@link Data#saveTasks(Scheduler)} gespeichert.
     * </p>
     *
     * @param args Array von Argumenten des Befehls
     * @param scheduler die Scheduler-Instanz, aus der Aufgaben entfernt werden sollen
     */

    public void execute (String[] args, Scheduler scheduler) {
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

        if (parts.size() < 4) {
            System.out.println("Look up manual \"help\"");
            return;
        }
        String name = parts.get(0).toString();
        byte priority = Byte.parseByte(parts.get(1).toString());
        Instant executeAt = Instant.now().plus(parseDuration(parts.get(2).toString()));
        Runnable action = RunnableRegistry.get(parts.get(3).toString());
        Duration repeatInterval = null;
        if (parts.size() >= 5) repeatInterval = parseDuration(parts.get(4).toString());
        if (name.equals("wallpaper")) {
            scheduler.addTask(name, priority, executeAt.minusSeconds(240), new WallpaperHelper(), Duration.ofDays(15));
        }
        scheduler.addTask(name, priority, executeAt, action, repeatInterval);
        Data.saveTasks(scheduler);
    }

    private Duration parseDuration(String input) {
        int value = Integer.parseInt(input.replaceAll("\\D", ""));
        String unit = input.replaceAll("\\d", "");

        return switch (unit) {
            case "s", "sec" -> Duration.ofSeconds(value);
            case "min" -> Duration.ofMinutes(value);
            case "h" -> Duration.ofHours(value);
            case "d" -> Duration.ofDays(value);
            default -> Duration.ZERO;
        };
    }
}
