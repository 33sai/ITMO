package client.commands.handlers;

import client.commands.ClientCommandHandler;
import client.commands.CommandExecutionContext;
import client.commands.CommandExecutionResult;
import common.models.MusicBand;
import common.network.CommandMeta;

public class ServerForwardingCommandHandler implements ClientCommandHandler {
    @Override
    public boolean supports(String commandName) {
        return true;
    }

    @Override
    public CommandExecutionResult handle(CommandExecutionContext context,
                                         String commandName,
                                         String argument,
                                         CommandMeta meta,
                                         String sourceLabel) {
        MusicBand band = null;
        if (meta.requiresBand()) {
            try {
                band = context.readBand();
            } catch (Exception e) {
                context.printInvalidBandData(sourceLabel, e.getMessage());
                return CommandExecutionResult.CONTINUE;
            }
        }

        context.forwardToServer(commandName, argument, band);
        return CommandExecutionResult.CONTINUE;
    }
}

