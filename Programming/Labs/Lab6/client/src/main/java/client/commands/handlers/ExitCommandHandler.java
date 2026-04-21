package client.commands.handlers;

import client.commands.ClientCommandHandler;
import client.commands.CommandExecutionContext;
import client.commands.CommandExecutionResult;
import common.network.CommandMeta;

public class ExitCommandHandler implements ClientCommandHandler {
    @Override
    public boolean supports(String commandName) {
        return "exit".equals(commandName);
    }

    @Override
    public CommandExecutionResult handle(CommandExecutionContext context,
                                         String commandName,
                                         String argument,
                                         CommandMeta meta,
                                         String sourceLabel) {
        return context.handleExit(sourceLabel);
    }
}

