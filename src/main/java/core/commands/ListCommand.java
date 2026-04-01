package core.commands;

import cli.Command;
import core.Scheduler;

/**
 * @author Monke Vladyslav
 * @version 1.1
 */

public class ListCommand implements Command {

    public void execute(String[] args, Scheduler scheduler) {
        scheduler.list();
    }
}
