package commands;

import utilities.CommandRequest;

public class Exit implements Command {

    @Override
    public CommandResult execute(CommandRequest request) {
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
