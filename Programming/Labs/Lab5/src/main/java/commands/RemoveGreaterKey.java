package commands;

import utilities.CollectionManager;
import utilities.CommandRequest;

import java.util.ArrayList;
import java.util.List;

public class RemoveGreaterKey extends CollectionCommand {

    public RemoveGreaterKey(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean requiresArgument() {
        return true;
    }

    @Override
    public CommandResult execute(CommandRequest request) {
        String argument = request.getArgument();
        if (argument == null || argument.isEmpty()) {
            return new CommandResult("Usage: remove_greater_key <key>", false);
        }

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
