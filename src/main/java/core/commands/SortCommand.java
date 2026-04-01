package core.commands;

import cli.Command;
import core.Scheduler;
import storage.Data;

/**
 * @author Monke Vladyslav
 * @version 1.1
 */

public class SortCommand implements Command {

    public void execute(String[] args, Scheduler scheduler) {
        if (args.length < 2) {
            System.out.println("Look up manual \"help\"");
        }
        switch(args[1]) {
            case "--byName" -> scheduler.sortByName();
            case "--byPriority" -> scheduler.sortByPriority();
            case "--byTime" -> scheduler.sortByTime();
        }
        Data.saveTasks(scheduler);
    }
}
