package server;

import commandsabstraction.Command;
import commandsabstraction.CommandResult;
import network.CommandPacket;

public class CommandProcessor {
    private final ServerCommandRegistry commandRegistry;

    public CommandProcessor(ServerCommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }

    public CommandResult processClientCommand(CommandPacket commandPacket) {
        String commandName = commandPacket.getCommandName();

        if ("save".equals(commandName)) {
            return new CommandResult("Command 'save' is available only on the server side.", false);
        }

        if ("exit".equals(commandName)) {
            return new CommandResult("Exit is handled on the client side.", true);
        }

        Command command = commandRegistry.getPublicCommand(commandName);
        if (command == null) {
            return new CommandResult("Unknown command. Type 'help' for available commands.", false);
        }

        return command.execute(commandPacket.getRequest());
    }

    public CommandResult processServerCommand(String commandName) {
        Command command = commandRegistry.getServerOnlyCommand(commandName);
        if (command == null) {
            return new CommandResult("Unknown server command: " + commandName, false);
        }

        return command.execute(new commandsabstraction.CommandRequest(""));
    }
}

