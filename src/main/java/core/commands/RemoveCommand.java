package core.commands;

import cli.Command;
import core.Scheduler;
import storage.Data;

import java.util.UUID;

/**
 * @author Monke Vladyslav
 * @version 1.1
 */
public class RemoveCommand implements Command {

    public void execute(String[] args, Scheduler scheduler) {
        StringBuilder name = new StringBuilder();
        if (args.length < 3) {
            System.out.println("Look up manual \"help\"");
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
        Data.saveTasks(scheduler);
    }
}