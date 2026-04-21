package server.commands;

import server.commandbase.CommandWithKey;
import common.commandsabstraction.CommandResult;
import server.utilities.CollectionManager;
import common.commandsabstraction.CommandRequest;

public class RemoveKey extends CommandWithKey {

    public RemoveKey(CollectionManager manager) {
        super(manager);
    }

    @Override
    protected CommandResult executeInternal(CommandRequest request) {
        String key = request.getArgument();
        if (!manager.containsKey(key)) {
            return new CommandResult("No element found with key '" + key + "'.", false);
        }
        manager.remove(key);
        return new CommandResult("Band with key '" + key + "' removed.", true);
    }


    @Override
    public String getDescription() {
        return "Remove a band by its key";
    }

    @Override
    public String getUsage() {
        return "remove_key <key>";
    }
}


