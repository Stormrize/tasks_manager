package cli.commands;

import cli.Command;
import core.Scheduler;
import java.util.UUID;

/**
 * Die Klasse {@code changeCommand} implementiert das Interface {@link Command} und
 * ermöglicht das Ändern von Aufgaben im {@link Scheduler} anhand ihrer UUID.
 * <p>
 * Der Befehl kann Name oder Priorität einer Aufgabe ändern. Die Ausführungszeit ist
 * aktuell noch nicht implementiert.
 * </p>
 *
 * @author Monke Vladyslav
 * @version 1.1
 */
public class changeCommand implements Command {

    /**
     * Führt den Change-Befehl aus.
     * <p>
     * Die Argumente werden wie folgt interpretiert:
     * <ul>
     *     <li>{@code --Name} : ändert den Namen der Aufgabe mit der angegebenen UUID</li>
     *     <li>{@code --Priority} : ändert die Priorität der Aufgabe mit der angegebenen UUID</li>
     *     <li>{@code --ExecuteAt} : (noch nicht implementiert) würde den Ausführungszeitpunkt ändern</li>
     * </ul>
     * <p>
     * Die UUID muss im Standardformat sein und die Priorität muss eine gültige Zahl zwischen 1 und 5 sein.
     * </p>
     *
     * @param args Array von Argumenten des Befehls
     * @param scheduler die Scheduler-Instanz, in der die Aufgabe geändert werden soll
     * @throws IllegalStateException wenn ein unerwartetes Argument übergeben wird
     */
    public void execute(String[] args, Scheduler scheduler) {
        if (args.length < 4) {
            System.out.println("help");
            return;
        }
        switch (args[1]) {
            case "--Name" -> scheduler.changeName(UUID.fromString(args[2]), args[3]);
            case "--Priority" -> scheduler.changePriority(UUID.fromString(args[2]), Integer.parseInt(args[3]));
            //case "--ExecuteAt" -> scheduler.changeExecuteAt(UUID.fromString(args[2]), args[3]); to do
            default -> throw new IllegalStateException("Unexpected value: " + args[1]);
        }
    }
}
