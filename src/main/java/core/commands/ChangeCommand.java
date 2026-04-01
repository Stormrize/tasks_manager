package core.commands;

import cli.Command;
import core.Scheduler;
import java.util.UUID;

/**
 * @author Monke Vladyslav
 * @version 1.1
 */
public class ChangeCommand implements Command {

    public void execute(String[] args, Scheduler scheduler) {
        if (args.length < 4) {
            System.out.println("Look up manual \"help\"");
            return;
        }
        switch (args[1]) {
            case "--name" -> scheduler.changeName(UUID.fromString(args[2]), args[3]);
            case "--priority" -> scheduler.changePriority(UUID.fromString(args[2]), Byte.parseByte(args[3]));
            //case "--executeAt" -> scheduler.changeExecuteAt(UUID.fromString(args[2]), args[3]); to do
        }
    }
}
