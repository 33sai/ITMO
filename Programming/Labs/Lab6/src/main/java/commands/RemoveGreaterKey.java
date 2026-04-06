package commands;

import argumentcommands.CommandWithKey;
import commandsabstraction.CollectionCommand;
import commandsabstraction.CommandResult;
import utilities.CollectionManager;
import commandsabstraction.CommandRequest;

public class RemoveGreaterKey extends CommandWithKey {

    public RemoveGreaterKey(CollectionManager manager) {
        super(manager);
    }


    @Override
    protected CommandResult executeInternal(CommandRequest request) {
        String argument = request.getArgument();
        int removedCount = manager.removeKeysGreaterThan(argument);
        return new CommandResult("Removed " + removedCount + " elements with keys greater than '" + argument + "'.", true);
    }


    @Override
    public String getDescription() {
        return "Remove all elements whose key is greater than the given one";
    }

    @Override
    public String getUsage() {
        return "remove_greater_key <key>";
    }
}
