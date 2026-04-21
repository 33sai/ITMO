package client.commands;

import client.commands.handlers.ExecuteScriptCommandHandler;
import client.commands.handlers.ExitCommandHandler;
import client.commands.handlers.HelpCommandHandler;
import client.commands.handlers.ServerForwardingCommandHandler;
import common.network.CommandMeta;

import java.util.List;

public class CommandDispatcher {
    private final CommandExecutionContext context;
    private final List<ClientCommandHandler> localHandlers;
    private final ClientCommandHandler remoteHandler;

    public CommandDispatcher(CommandExecutionContext context) {
        this.context = context;
        this.localHandlers = List.of(
                new HelpCommandHandler(),
                new ExitCommandHandler(),
                new ExecuteScriptCommandHandler()
        );
        this.remoteHandler = new ServerForwardingCommandHandler();
    }

    public boolean dispatch(String commandName, String argument, String sourceLabel) {
        CommandMeta meta = context.getCommandMeta(commandName);
        if (meta == null) {
            context.printUnknownCommand(commandName, sourceLabel);
            return true;
        }

        if (context.shouldValidateRequiredArgument() && meta.requiresArgument() && argument.isBlank()) {
            context.printUsage(meta);
            return true;
        }

        for (ClientCommandHandler handler : localHandlers) {
            if (handler.supports(commandName)) {
                return handler.handle(context, commandName, argument, meta, sourceLabel) == CommandExecutionResult.CONTINUE;
            }
        }

        return remoteHandler.handle(context, commandName, argument, meta, sourceLabel) == CommandExecutionResult.CONTINUE;
    }
}

