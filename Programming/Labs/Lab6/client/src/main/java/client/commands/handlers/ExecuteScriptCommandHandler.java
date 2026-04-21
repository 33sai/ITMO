package client.commands.handlers;

import client.commands.ClientCommandHandler;
import client.commands.CommandExecutionContext;
import client.commands.CommandExecutionResult;
import common.network.CommandMeta;

public class ExecuteScriptCommandHandler implements ClientCommandHandler {
    @Override
    public boolean supports(String commandName) {
        return "execute_script".equals(commandName);
    }

    @Override
    public CommandExecutionResult handle(CommandExecutionContext context,
                                         String commandName,
                                         String argument,
                                         CommandMeta meta,
                                         String sourceLabel) {
        return context.executeScript(argument) ? CommandExecutionResult.CONTINUE : CommandExecutionResult.STOP;
    }
}

