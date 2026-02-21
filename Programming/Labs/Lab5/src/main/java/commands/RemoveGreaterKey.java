package commands;

import utilities.CollectionManager;
import java.util.ArrayList;
import java.util.List;

public class RemoveGreaterKey implements Command {

    @Override
    public CommandResult execute(String argument, CollectionManager manager) {
        if (argument.isEmpty()) {
            return new CommandResult("Usage: remove_greater_key <key>", false);
        }
        String threshold = argument;

        List<String> toRemove = new ArrayList<>();
        for (String key : manager.getKeys()) {
            if (key.compareTo(threshold) > 0) {
                toRemove.add(key);
            }
        }

        for (String key : toRemove) {
            manager.remove(key);
        }

        return new CommandResult("Removed " + toRemove.size() + " bands with key greater than '" + threshold + "'.", true);
    }

    @Override
    public String getDescription() {
        return "Remove all bands whose key is greater than the given key";
    }

    @Override
    public String getUsage() {
        return "remove_greater_key <key>";
    }
}