package commands;

import argumentcommands.CommandWithoutArgs;
import commandsabstraction.CollectionCommand;
import commandsabstraction.CommandResult;
import utilities.CollectionManager;
import commandsabstraction.CommandRequest;

public class Info extends CommandWithoutArgs {

    public Info(CollectionManager manager) {
        super(manager);
    }


    @Override
    protected CommandResult executeInternal() {
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
