package cli;

import core.commands.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Monke Vladyslav
 * @version 1.1
 */
public class CommandRegistry {

    private static final Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("list", new ListCommand());
        commands.put("sort", new SortCommand());
        commands.put("remove", new RemoveCommand());
        commands.put("add", new AddCommand());
        commands.put("change", new ChangeCommand());
        commands.put("help", new HelpCommand());
    }

    public static Command get(String name) {
        return commands.get(name);
    }
}
