package argumentcommands;

import commandsabstraction.CollectionCommand;
import commandsabstraction.CommandRequest;
import commandsabstraction.CommandResult;
import utilities.CollectionManager;

public abstract class CommandWithOtherArgs extends CollectionCommand {

    public CommandWithOtherArgs(CollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResult execute(CommandRequest request) {
        if (request.getArgument() == null || request.getArgument().isEmpty()) {
            return new CommandResult("Usage: " +getUsage(), false);
        }
        return executeInternal(request);
    }

    protected abstract CommandResult executeInternal(CommandRequest request);

    @Override
    public boolean requiresArgument() {
        return true;
    }

}
