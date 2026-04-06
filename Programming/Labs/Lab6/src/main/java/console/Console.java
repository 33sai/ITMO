// LEGACY CLASS

package console;

import commandsabstraction.Command;
import commandsabstraction.CommandResult;
import utilities.CommandManager;
import commandsabstraction.CommandRequest;
import commandsabstraction.CommandRequestFactory;
import utilities.MusicBandValidator;

import java.util.Scanner;

public class Console {
    private CommandManager commandManager;
    private CommandRequestFactory requestFactory;
    private Scanner scanner;
    private boolean running = true;

    public Console(CommandManager commandManager,
                   MusicBandValidator validator,
                   Scanner scanner) {
        this.commandManager = commandManager;
        this.requestFactory = new CommandRequestFactory(validator);
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("""
                \s
                        __________
                       1          1
                       1          1
                       1          1
                  ____ 1     ____ 1
                 /    \\1    /    \\1
                1      1   1      1
                 \\____/     \\____/
                
                Welcome to Music Band Manager⠀⠀⠀⠀⠀⠀
                """);

        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            String[] parts = input.split(" ", 2);
            String commandName = parts[0];
            String argument = parts.length > 1 ? parts[1] : "";

            Command command = commandManager.getCommand(commandName);

            if (command == null) {
                System.out.println("Unknown command. Type 'help' for available commands.");
                continue;
            }

            try {
                // Factory requirement DONE HERE
                CommandRequest request = requestFactory.buildRequest(command, argument);
                CommandResult result = command.execute(request);

                if (result.isSuccess()) {
                    System.out.println(result.getMessage());
                    if (result.getData() != null) {
                        System.out.println(result.getData());
                    }
                } else {
                    System.out.println("Error: " + result.getMessage());
                }if (commandName.equals("exit")) {
                    running = false;
                }

            } catch (Exception e) {
                System.out.println("Error executing command: " + e.getMessage());
            }

        }
    }
}
