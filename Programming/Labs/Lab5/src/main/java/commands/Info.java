package commands;

import utilities.CollectionManager;

public class Info implements Command {

    @Override
    public CommandResult execute(String argument, CollectionManager manager) {
        String info = "Collection type: " + manager.getCollectionType() + "\n"
                + "Initialization date: " + manager.getInitializationDate() + "\n"
                + "Number of elements: " + manager.size();
        return new CommandResult(info, true);
    }

    @Override
    public String getDescription() {
        return "Shows information about the collection";
    }

    @Override
    public String getUsage() {
        return "info";
    }
}
