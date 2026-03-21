package commands;

import models.*;
import utilities.CommandManager;
import utilities.CommandRequest;
import utilities.CommandRequestFactory;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class ExecuteScript implements Command {
    private CommandManager commandManager;
    private Set<String> scriptsRunning = new HashSet<>();

    public ExecuteScript(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public boolean requiresArgument() {
        return true;
    }

    @Override
    public CommandResult execute(CommandRequest request) {
        String filename = request.getArgument();
        if (filename == null || filename.isEmpty()) {
            return new CommandResult("Usage: execute_script <file_name>", false);
        }

        if (scriptsRunning.contains(filename)) {
            return new CommandResult("Recursion detected: script '" + filename + "' is already being executed.", false);
        }

        File file = new File(filename);
        if (!file.exists()) {
            return new CommandResult("File not found: " + filename, false);
        }
        if (!file.canRead()) {
            return new CommandResult("Cannot read file: " + filename + " try doing chmod 777", false);
        }

        scriptsRunning.add(filename);
        int lineNumber = 0;
        int successCount = 0;
        int failCount = 0;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                lineNumber++;
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                System.out.println("[" + filename + ":" + lineNumber + "] " + line);

                String[] parts = line.split(" ", 2);
                String cmdName = parts[0];
                String cmdArg = parts.length > 1 ? parts[1] : "";

                Command command = commandManager.getCommand(cmdName);
                if (command == null) {
                    System.out.println("Unknown command: " + cmdName);
                    failCount++;
                    continue;
                }

                CommandRequest cmdRequest;
                if (command.requiresBand()) {
                    MusicBand band = readBandFromScanner(fileScanner);
                    cmdRequest = CommandRequestFactory.buildScriptRequest(cmdArg, band);
                } else {
                    cmdRequest = CommandRequestFactory.buildSimpleRequest(cmdArg);
                }

                CommandResult result = command.execute(cmdRequest);

                if (result.isSuccess()) {
                    System.out.println("Successful! " + result.getMessage());
                    successCount++;
                } else {
                    System.out.println("Unsuccessful! " + result.getMessage());
                    failCount++;
                }
            }
        } catch (FileNotFoundException e) {
            return new CommandResult("Error opening script file.", false);
        } catch (Exception e) {
            return new CommandResult("Error in script: " + e.getMessage(), false);
        } finally {
            scriptsRunning.remove(filename);
        }

        return new CommandResult("Script completed. Lines: " + lineNumber +
                ", Success: " + successCount +
                ", Fail: " + failCount,
                true
        );
    }

    private MusicBand readBandFromScanner(Scanner sc) {
        String name = sc.nextLine().trim();
        double x = Double.parseDouble(sc.nextLine().trim());
        int y = Integer.parseInt(sc.nextLine().trim());
        long participants = Long.parseLong(sc.nextLine().trim());

        String singlesStr = sc.nextLine().trim();
        Integer singles = singlesStr.isEmpty() ? null : Integer.parseInt(singlesStr);

        String estStr = sc.nextLine().trim();
        LocalDate estDate = estStr.isEmpty() ? null : LocalDate.parse(estStr);

        String genreStr = sc.nextLine().trim();
        MusicGenre genre = genreStr.isEmpty() ? null : MusicGenre.valueOf(genreStr);

        String studioStr = sc.nextLine().trim();
        Studio studio = studioStr.isEmpty() ? null : new Studio(studioStr);

        Coordinates coords = new Coordinates(x, y);
        return new MusicBand(name, coords, participants, singles, estDate, genre, studio);
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
