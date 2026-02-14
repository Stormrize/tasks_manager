package cli;

import core.Scheduler;
import storage.Data;

import java.util.Scanner;

/**
 * Main
 *
 * @author Monke Vladyslav
 * @version 1.1
 */
public class Main {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        Data.loadFile(scheduler);

        scheduler.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine();

            if (line.equals("exit")) {
                Data.saveFile(scheduler);
                scheduler.shutdown();
                break;  
            }

            String[] parts = line.split(" ");
            Command cmd = CommandRegistry.get(parts[0]);
            if (cmd != null) {
                cmd.execute(parts, scheduler);
            }
        }
    }
}