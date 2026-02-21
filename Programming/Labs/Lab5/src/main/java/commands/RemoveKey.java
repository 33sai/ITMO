package commands;

import utilities.CollectionManager;

public class RemoveKey implements Command {

    @Override
    public CommandResult execute(String argument, CollectionManager manager) {
        if (argument.isEmpty()) {
            return new CommandResult("Usage: remove_key <key>", false);
        }
        String key = argument;

        if (!manager.containsKey(key)) {
            return new CommandResult("No band found with key '" + key + "'.", false);
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