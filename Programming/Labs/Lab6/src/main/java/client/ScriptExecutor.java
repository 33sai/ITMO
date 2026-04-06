package client;

import commandsabstraction.CommandRequest;
import models.Coordinates;
import models.MusicBand;
import models.MusicGenre;
import models.Studio;
import network.CommandPacket;
import network.ResponsePacket;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ScriptExecutor {
    private final ClientCommandRegistry commandRegistry;
    private final NonBlockingTcpClient tcpClient;
    private final Set<String> runningScripts = new HashSet<>();

    public ScriptExecutor(ClientCommandRegistry commandRegistry, NonBlockingTcpClient tcpClient) {
        this.commandRegistry = commandRegistry;
        this.tcpClient = tcpClient;
    }

    public boolean executeScript(String fileName) {
        if (runningScripts.contains(fileName)) {
            System.out.println("Recursion detected for script: " + fileName);
            return true;
        }

        File scriptFile = new File(fileName);
        if (!scriptFile.exists() || !scriptFile.canRead()) {
            System.out.println("Script file is unavailable: " + fileName);
            return true;
        }

        runningScripts.add(fileName);
        try (Scanner scanner = new Scanner(scriptFile)) {
            int lineNo = 0;
            while (scanner.hasNextLine()) {
                lineNo++;
                String rawLine = scanner.nextLine().trim();
                if (rawLine.isEmpty() || rawLine.startsWith("#")) {
                    continue;
                }

                String[] parts = rawLine.split(" ", 2);
                String commandName = parts[0];
                String argument = parts.length > 1 ? parts[1] : "";

                ClientCommandRegistry.CommandMeta commandMeta = commandRegistry.get(commandName);
                if (commandMeta == null) {
                    System.out.println("[" + fileName + ":" + lineNo + "] Unknown command: " + commandName);
                    continue;
                }

                if ("help".equals(commandName)) {
                    System.out.println(commandRegistry.buildHelpText());
                    continue;
                }

                if ("exit".equals(commandName)) {
                    System.out.println("[" + fileName + ":" + lineNo + "] exit requested.");
                    return false;
                }

                if ("execute_script".equals(commandName)) {
                    boolean shouldContinue = executeScript(argument);
                    if (!shouldContinue) {
                        return false;
                    }
                    continue;
                }

                MusicBand band = null;
                if (commandMeta.requiresBand()) {
                    try {
                        band = readBandFromScript(scanner);
                    } catch (Exception e) {
                        System.out.println("[" + fileName + ":" + lineNo + "] Invalid band data in script: " + e.getMessage());
                        continue;
                    }
                }

                CommandRequest request = new CommandRequest(argument, band);
                CommandPacket packet = new CommandPacket(commandName, request);
                try {
                    ResponsePacket responsePacket = tcpClient.send(packet, 4000);
                    System.out.println(responsePacket.getResult().getMessage());
                    if (responsePacket.getResult().getData() != null) {
                        System.out.println(responsePacket.getResult().getData());
                    }
                } catch (Exception e) {
                    System.out.println("Server unavailable while executing script command '" + commandName + "': " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Script file not found: " + fileName);
        } finally {
            runningScripts.remove(fileName);
        }

        return true;
    }

    private MusicBand readBandFromScript(Scanner scanner) {
        String name = scanner.nextLine().trim();
        double x = Double.parseDouble(scanner.nextLine().trim());
        int y = Integer.parseInt(scanner.nextLine().trim());
        long participants = Long.parseLong(scanner.nextLine().trim());

        String singlesLine = scanner.nextLine().trim();
        Integer singles = singlesLine.isEmpty() ? null : Integer.parseInt(singlesLine);

        String dateLine = scanner.nextLine().trim();
        LocalDate establishmentDate = dateLine.isEmpty() ? null : LocalDate.parse(dateLine);

        String genreLine = scanner.nextLine().trim();
        MusicGenre genre = genreLine.isEmpty() ? null : MusicGenre.valueOf(genreLine);

        String studioLine = scanner.nextLine().trim();
        Studio studio = studioLine.isEmpty() ? null : new Studio(studioLine);

        return new MusicBand(name, new Coordinates(x, y), participants, singles, establishmentDate, genre, studio);
    }
}

