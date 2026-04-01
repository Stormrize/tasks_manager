package cli;

import core.Scheduler;

/**
 * @author Monke Vladyslav
 * @version 1.1
 */
public interface Command {

    void execute(String[] args, Scheduler scheduler);
}
