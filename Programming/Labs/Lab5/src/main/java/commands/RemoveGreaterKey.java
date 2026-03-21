package commands;

import argumentcommands.CommandWithKey;
import commandsabstraction.CollectionCommand;
import commandsabstraction.CommandResult;
import utilities.CollectionManager;
import commandsabstraction.CommandRequest;

import java.util.ArrayList;
import java.util.List;

public class RemoveGreaterKey extends CommandWithKey {

    public RemoveGreaterKey(CollectionManager manager) {
        super(manager);
    }


    @Override
    protected CommandResult executeInternal(CommandRequest request) {
        String argument = request.getArgument();
        List<String> toRemove = new ArrayList<>();
        for (String key : manager.getKeys()) {
            if (key.compareTo(argument) > 0) {
                toRemove.add(key);
            }
        }

        for (String key : toRemove) {
            manager.remove(key);
        }

        return new CommandResult("Removed " + toRemove.size() + " elements with keys greater than '" + argument + "'.", true);
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
