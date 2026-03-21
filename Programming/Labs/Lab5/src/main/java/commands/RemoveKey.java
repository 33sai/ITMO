package commands;

import utilities.CollectionManager;
import utilities.CommandRequest;

public class RemoveKey extends CollectionCommand {

    public RemoveKey(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean requiresArgument() {
        return true;
    }

    @Override
    public CommandResult execute(CommandRequest request) {
        String key = request.getArgument();
        if (key == null || key.isEmpty()) {
            return new CommandResult("Usage: remove_key <key>", false);
        }

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
