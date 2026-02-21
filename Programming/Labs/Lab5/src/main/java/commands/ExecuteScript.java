package commands;

import utilities.CollectionManager;
import utilities.CommandManager;
import java.io.*;
import java.util.*;

public class ExecuteScript implements Command {
    private CommandManager commandManager;
    private Set<String> scriptsRunning = new HashSet<>();  // for recursion detection

    public ExecuteScript(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public CommandResult execute(String argument, CollectionManager manager) {
        if (argument.isEmpty()) {
            return new CommandResult("Usage: execute_script <file_name>", false);
        }
        String filename = argument;

        // Recursion check
        if (scriptsRunning.contains(filename)) {
            return new CommandResult("Recursion detected: script '" + filename + "' is already being executed.", false);
        }

        File file = new File(filename);
        if (!file.exists()) {
            return new CommandResult("File not found: " + filename, false);
        }
        if (!file.canRead()) {
            return new CommandResult("Cannot read file: " + filename, false);
        }

        scriptsRunning.add(filename);
        int lineNumber = 0;
        int successCount = 0;
        int failCount = 0;

        try (Scanner scriptScanner = new Scanner(file)) {
            while (scriptScanner.hasNextLine()) {
                lineNumber++;
                String line = scriptScanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue; // skip comments/empty

                System.out.println("[" + filename + ":" + lineNumber + "] Executing: " + line);
                String[] parts = line.split(" ", 2);
                String cmdName = parts[0];
                String cmdArg = parts.length > 1 ? parts[1] : "";

                Command command = commandManager.getCommand(cmdName);
                if (command == null) {
                    System.out.println("Unknown command: " + cmdName);
                    failCount++;
                    continue;
                }

                // Prevent nested script recursion from within the script itself
                if (command instanceof ExecuteScript) {
                    // We already added filename to scriptsRunning, so nested call will be caught.
                }

                CommandResult result = command.execute(cmdArg, manager);
                if (result.isSuccess()) {
                    System.out.println("Success: " + result.getMessage());
                    successCount++;
                } else {
                    System.out.println("Fail: " + result.getMessage());
                    failCount++;
                }
            }
        } catch (FileNotFoundException e) {
            // already checked, but just in case
            return new CommandResult("Error opening script file.", false);
        } finally {
            scriptsRunning.remove(filename);
        }

        return new CommandResult("Script finished. Lines: " + lineNumber + ", Success: " + successCount + ", Fail: " + failCount, true);
    }

    @Override
    public String getDescription() {
        return "Execute commands from a script file";
    }

    @Override
    public String getUsage() {
        return "execute_script <file_name>";
    }
}