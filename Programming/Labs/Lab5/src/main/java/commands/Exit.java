package commands;

import argumentcommands.CommandWithoutArgs;
import commandsabstraction.CommandResult;

public class Exit extends CommandWithoutArgs {

    @Override
    protected CommandResult executeInternal() {
        return new CommandResult("See you next time", true);
    }

    @Override
    public String getDescription() {
        return "Exits the program without saving.";
    }

    @Override
    public String getUsage() {
        return "exit";
    }
}
