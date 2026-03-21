package commands;

import utilities.CollectionManager;
import utilities.CommandRequest;

public class Info extends CollectionCommand {

    public Info(CollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResult execute(CommandRequest request) {
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
