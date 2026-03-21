package commands;

import argumentcommands.CommandWithoutArgs;
import commandsabstraction.CollectionCommand;
import commandsabstraction.CommandResult;
import utilities.CollectionManager;
import commandsabstraction.CommandRequest;

public class Clear extends CommandWithoutArgs {

    public Clear(CollectionManager manager) {
        super(manager);
    }


    @Override
    public CommandResult executeInternal() {
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
