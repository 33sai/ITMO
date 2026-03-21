package commands;

import utilities.CollectionManager;
import utilities.CommandRequest;

public class Clear extends CollectionCommand {

    public Clear(CollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResult execute(CommandRequest request) {
        manager.clear();
        return new CommandResult("Successfully cleared collection", true);
    }

    @Override
    public String getDescription() {
        return "Clears all elements of collection";
    }

    @Override
    public String getUsage() {
        return "clear";
    }
}
