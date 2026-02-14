package storage;

import core.Scheduler;
import core.Task;

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
        try(BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                UUID id = UUID.fromString(parts[0]);
                String name = parts[1];
                int priority = Integer.parseInt(parts[2]);
                Instant time = Instant.parse(parts[3]);
                scheduler.addTask(id, name, priority, time);
            }
        } catch(IOException e) {
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
        try(FileWriter writer = new FileWriter("tasks.txt")) {
            for (Task task : tasks) {
                writer.write(task.getId() + "\t" + task.getName() + "\t" + task.getPriority() + "\t" + task.getExecuteAT());
                writer.write("\n");
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
