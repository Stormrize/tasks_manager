package cli;

import cli.commands.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Die Klasse {@code CommandRegistry} verwaltet alle verfügbaren CLI-Befehle.
 * <p>
 * Befehle werden in einer statischen Map registriert und können über ihren Namen abgerufen werden.
 * </p>
 *
 * @author Monke Vladyslav
 * @version 1.1
 */
public class CommandRegistry {

    /** Map der verfügbaren Befehle mit ihrem Namen */
    private static final Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("list", new ListCommand());
        commands.put("sort", new SortCommand());
        commands.put("remove", new RemoveCommand());
        commands.put("add", new addCommand());
        commands.put("change", new changeCommand());
    }

    /**
     * Gibt den Befehl für den angegebenen Namen zurück.
     *
     * @param name Name des Befehls
     * @return {@link Command}-Instanz, oder {@code null}, wenn der Befehl nicht existiert
     */
    public static Command get(String name) {
        return commands.get(name);
    }
}
