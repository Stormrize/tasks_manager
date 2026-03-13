package core.commands;

import cli.Command;
import core.Scheduler;

public class HelpCommand implements Command {

    public void execute(String[] args, Scheduler scheduler) {

        System.out.println("""
    ==================================================
                    TASK SCHEDULER HELP
    ==================================================
    
    Available commands:
    
    ADD TASK
    --------
    Adds a new task to the scheduler.
    
    Usage:
    add --name <task name> --priority <number> --in <time>
    
    Example:
    add --name Do homework --priority 2 --in 30min
    add --name Clean room --priority 1 --in 2h
    
    
    EDIT TASK
    ---------
    Changes task parameters.
    
    Usage:
    edit --name <UUID> <new name>
    edit --nriority <UUID> <new priority>
    
    Example:
    edit --name 123e4567-e89b-12d3-a456-426614174000 Buy milk
    edit --priority 123e4567-e89b-12d3-a456-426614174000 5
    
    
    REMOVE TASK
    -----------
    Removes a task from the scheduler.
    
    Usage:
    remove --byName <task name>
    remove --byPriority <priority>
    remove --byUUID <uuid>
    
    Example:
    remove --byName Buy milk
    remove --byPriority 3
    remove --byUUID 123e4567-e89b-12d3-a456-426614174000
    
    
    LIST TASKS
    ----------
    Shows all tasks currently stored in the scheduler.
    
    Usage:
    list
    
    
    SORT TASKS
    ----------
    Sorts tasks by a chosen parameter.
    
    Usage:
    sort --byName
    sort --byPriority
    sort --byTime
    
    
    TIME FORMAT
    -----------
    You can specify time using:
    
    10s   -> seconds
    5min  -> minutes
    2h    -> hours
    
    Example:
    --in 30min
    --in 2h
    
    ==================================================
    """);
    }
}