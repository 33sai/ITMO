package commands;

import argumentcommands.CommandWithKey;
import commandsabstraction.CollectionCommand;
import commandsabstraction.CommandResult;
import utilities.CollectionManager;
import commandsabstraction.CommandRequest;

public class RemoveKey extends CommandWithKey {

    public RemoveKey(CollectionManager manager) {
        super(manager);
    }

    @Override
    protected CommandResult executeInternal(CommandRequest request) {
        String key = request.getArgument();
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
