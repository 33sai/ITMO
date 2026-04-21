package client.commands;

import client.ClientCommandRegistry;
import client.NonBlockingTcpClient;
import common.commandsabstraction.CommandRequest;
import common.models.MusicBand;
import common.network.CommandMeta;
import common.network.CommandPacket;
import common.network.ResponsePacket;

public class CommandExecutionContext {
    private static final int SEND_TIMEOUT_MILLIS = 15000;
    private static final int RETRY_DELAY_MILLIS = 500;

    @FunctionalInterface
    public interface BandReader {
        MusicBand read() throws Exception;
    }

    @FunctionalInterface
    public interface ScriptRunner {
        boolean execute(String fileName);
    }

    private final ClientCommandRegistry commandRegistry;
    private final NonBlockingTcpClient tcpClient;
    private final BandReader bandReader;
    private final ScriptRunner scriptRunner;
    private final boolean validateRequiredArgument;
    private final boolean scriptMode;
    private final int maxSendAttempts;

    public CommandExecutionContext(ClientCommandRegistry commandRegistry,
                                   NonBlockingTcpClient tcpClient,
                                   BandReader bandReader,
                                   ScriptRunner scriptRunner,
                                   boolean validateRequiredArgument,
                                   boolean scriptMode,
                                   int maxSendAttempts) {
        this.commandRegistry = commandRegistry;
        this.tcpClient = tcpClient;
        this.bandReader = bandReader;
        this.scriptRunner = scriptRunner;
        this.validateRequiredArgument = validateRequiredArgument;
        this.scriptMode = scriptMode;
        this.maxSendAttempts = Math.max(1, maxSendAttempts);
    }

    public CommandMeta getCommandMeta(String commandName) {
        return commandRegistry.get(commandName);
    }

    public boolean shouldValidateRequiredArgument() {
        return validateRequiredArgument;
    }

    public void printUnknownCommand(String commandName, String sourceLabel) {
        if (scriptMode) {
            System.out.println("[" + sourceLabel + "] Unknown command: " + commandName);
            return;
        }
        System.out.println("Unknown command. Type 'help'.");
    }

    public void printHelp() {
        System.out.println(commandRegistry.buildHelpText());
    }

    public void printUsage(CommandMeta meta) {
        System.out.println("Usage: " + meta.usage());
    }

    public CommandExecutionResult handleExit(String sourceLabel) {
        if (scriptMode) {
            System.out.println("[" + sourceLabel + "] exit requested.");
        }
        return CommandExecutionResult.STOP;
    }

    public boolean executeScript(String argument) {
        return scriptRunner.execute(argument);
    }

    public MusicBand readBand() throws Exception {
        if (bandReader == null) {
            throw new IllegalStateException("Band reader is not configured.");
        }
        return bandReader.read();
    }

    public void printInvalidBandData(String sourceLabel, String details) {
        if (scriptMode) {
            System.out.println("[" + sourceLabel + "] Invalid band data in script: " + details);
            return;
        }
        System.out.println("Invalid band data: " + details);
    }

    public void forwardToServer(String commandName, String argument, MusicBand band) {
        CommandRequest request = new CommandRequest(argument, band);
        CommandPacket packet = new CommandPacket(commandName, request);

        for (int attempt = 1; attempt <= maxSendAttempts; attempt++) {
            try {
                ResponsePacket responsePacket = tcpClient.send(packet, SEND_TIMEOUT_MILLIS);
                System.out.println(responsePacket.getResult().getMessage());
                if (responsePacket.getResult().getData() != null) {
                    System.out.println(responsePacket.getResult().getData());
                }
                return;
            } catch (Exception e) {
                boolean hasRetriesLeft = attempt < maxSendAttempts;
                if (hasRetriesLeft && !scriptMode) {
                    System.out.println("Server temporarily unavailable, retrying...");
                    if (!sleepBeforeRetry()) {
                        return;
                    }
                    continue;
                }

                if (scriptMode) {
                    System.out.println("Server unavailable while executing script command '" + commandName + "': " + e.getMessage());
                } else {
                    System.out.println("Failed to reach server: " + e.getMessage());
                }
                return;
            }
        }
    }

    private boolean sleepBeforeRetry() {
        try {
            Thread.sleep(RETRY_DELAY_MILLIS);
            return true;
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}

