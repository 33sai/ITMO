package client.commands.handlers;

import client.commands.ClientCommandHandler;
import client.commands.CommandExecutionContext;
import client.commands.CommandExecutionResult;
import common.network.CommandMeta;

public class HelpCommandHandler implements ClientCommandHandler {
    @Override
    public boolean supports(String commandName) {
        return "help".equals(commandName);
    }

    @Override
    public CommandExecutionResult handle(CommandExecutionContext context,
                                         String commandName,
                                         String argument,
                                         CommandMeta meta,
                                         String sourceLabel) {
        context.printHelp();
        return CommandExecutionResult.CONTINUE;
    }
}

