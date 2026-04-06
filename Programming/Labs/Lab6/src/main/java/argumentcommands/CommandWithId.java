package argumentcommands;

import commandsabstraction.CollectionCommand;
import commandsabstraction.CommandRequest;
import commandsabstraction.CommandResult;
import utilities.CollectionManager;

public abstract class CommandWithId extends CollectionCommand {

    public CommandWithId(CollectionManager manager) {
        super(manager);
    }



    @Override
    public CommandResult execute(CommandRequest request) {
        String argument = request.getArgument();
        if (argument == null || argument.isEmpty()) {
            return new CommandResult("Usage: " + getUsage(), false);
        }
        return executeInternal(request);
    }


    protected abstract CommandResult executeInternal(CommandRequest request);

    @Override
    public boolean requiresBand() {
        return false;
    }

    @Override
    public boolean requiresArgument() {
        return true;
    }
}
