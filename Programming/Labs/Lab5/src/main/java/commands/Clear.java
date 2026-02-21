package commands;

import utilities.CollectionManager;

import java.util.Collection;

public class Clear implements Command {

    @Override
    public CommandResult execute(String arg, CollectionManager manager) {
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
