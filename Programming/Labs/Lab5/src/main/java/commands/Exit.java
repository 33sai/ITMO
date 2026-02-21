package commands;

import utilities.CollectionManager;

public class Exit implements Command {

    @Override
    public CommandResult execute(String argument, CollectionManager manager) {
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
