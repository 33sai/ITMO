package client;

import commandsabstraction.CommandRequest;
import models.MusicBand;
import network.CommandPacket;
import network.ResponsePacket;
import utilities.MusicBandValidator;

import java.util.Scanner;

public class ClientConsole {
    private final Scanner scanner;
    private final MusicBandValidator validator;
    private final ClientCommandRegistry commandRegistry;
    private final NonBlockingTcpClient tcpClient;
    private final ScriptExecutor scriptExecutor;

    public ClientConsole(Scanner scanner,
                         MusicBandValidator validator,
                         ClientCommandRegistry commandRegistry,
                         NonBlockingTcpClient tcpClient) {
        this.scanner = scanner;
        this.validator = validator;
        this.commandRegistry = commandRegistry;
        this.tcpClient = tcpClient;
        this.scriptExecutor = new ScriptExecutor(commandRegistry, tcpClient);
    }

    public void run() {
        boolean running = true;

        while (running) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                break;
            }

            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }

            String[] parts = input.split(" ", 2);
            String commandName = parts[0];
            String argument = parts.length > 1 ? parts[1] : "";

            ClientCommandRegistry.CommandMeta meta = commandRegistry.get(commandName);
            if (meta == null) {
                System.out.println("Unknown command. Type 'help'.");
                continue;
            }

            if ("help".equals(commandName)) {
                System.out.println(commandRegistry.buildHelpText());
                continue;
            }

            if ("exit".equals(commandName)) {
                running = false;
                continue;
            }

            if (meta.requiresArgument() && argument.isBlank()) {
                System.out.println("Usage: " + meta.usage());
                continue;
            }

            if ("execute_script".equals(commandName)) {
                running = scriptExecutor.executeScript(argument);
                continue;
            }

            MusicBand band = null;
            if (meta.requiresBand()) {
                try {
                    band = validator.askMusicBand();
                } catch (Exception e) {
                    System.out.println("Invalid band data: " + e.getMessage());
                    continue;
                }
            }

            CommandRequest request = new CommandRequest(argument, band);
            CommandPacket packet = new CommandPacket(commandName, request);

            int attempts = 0;
            boolean sent = false;
            while (attempts < 3 && !sent) {
                attempts++;
                try {
                    ResponsePacket response = tcpClient.send(packet, 4000);
                    System.out.println(response.getResult().getMessage());
                    if (response.getResult().getData() != null) {
                        System.out.println(response.getResult().getData());
                    }
                    sent = true;
                } catch (Exception e) {
                    if (attempts < 3) {
                        System.out.println("Server temporarily unavailable, retrying...");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException interruptedException) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    } else {
                        System.out.println("Failed to reach server: " + e.getMessage());
                    }
                }
            }
        }
    }
}

