package storage;

import core.Scheduler;
import core.Task;
import core.runnable.SpaceFact;

import java.io.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Die Klasse {@code Data} ist für das Laden und Speichern von Aufgaben aus/in einer Datei zuständig.
 * <p>
 * Aufgaben werden in der Datei {@code tasks.txt} gespeichert. Jede Zeile repräsentiert eine Aufgabe
 * mit der Struktur: {@code UUID <Tab> Name <Tab> Priorität <Tab> Ausführungszeitpunkt}.
 * </p>
 *
 * @author Monke Vladyslav
 * @version 1.1
 */
public class Data {

    /**
     * Lädt Aufgaben aus der Datei {@code tasks.txt} in den Scheduler.
     * <p>
     * Jede Zeile wird geparst und als {@link Task} zum Scheduler hinzugefügt.
     * </p>
     *
     * @param scheduler Scheduler, in den die Aufgaben geladen werden
     */
    public static void loadFile(Scheduler scheduler) {
        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.json"))) {
            String line;
            UUID id = null;
            String name = null;
            int priority = 0;
            Instant executeAt = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // ID-Zeile: "123e4567-e89b-12d3-a456-426614174000": {
                if (line.startsWith("\"") && line.contains("\": {")) {
                    String key = line.substring(1, line.indexOf("\":"));
                    id = UUID.fromString(key);
                }
                // Name-Zeile
                else if (line.startsWith("\"name\"")) {
                    name = line.split(":")[1].trim()
                            .replace("\"", "")
                            .replace(",", "");
                }
                // Priority-Zeile
                else if (line.startsWith("\"priority\"")) {
                    priority = Integer.parseInt(
                            line.split(":")[1].trim().replace(",", "")
                    );
                }
                // executeAt-Zeile
                else if (line.startsWith("\"executeAt\"")) {
                    executeAt = Instant.parse(
                            line.split(":", 2)[1].trim().replace("\"", "")
                    );
                } else if (line.startsWith("\"action\"")) {
                    Runnable action = new SpaceFact();
                    scheduler.addTask(id, name, priority, executeAt, action);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Speichert alle Aufgaben aus dem Scheduler in der Datei {@code tasks.txt}.
     * <p>
     * Jede Aufgabe wird in einer eigenen Zeile gespeichert mit Tabulator-getrennten Feldern:
     * UUID, Name, Priorität, Ausführungszeitpunkt.
     * </p>
     *
     * @param scheduler Scheduler, dessen Aufgaben gespeichert werden
     */
     public static void saveFile(Scheduler scheduler) {
        List<Task> tasks = scheduler.snapshot();

        try (FileWriter writer = new FileWriter("tasks.json")) {
            writer.write("{\n");
            int index = 0;
            for (Task task : tasks) {
                writer.write("  \"" + task.getId() + "\": {\n");
                writer.write("    \"name\": \"" + task.getName() + "\",\n");
                writer.write("    \"priority\": " + task.getPriority() + ",\n");
                writer.write("    \"executeAt\": \"" + task.getExecuteAT() + "\"\n");
                writer.write("  }");
                if (index < tasks.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
                index++;
            }
            writer.write("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
