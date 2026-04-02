package argumentcommands;

import commandsabstraction.CollectionCommand;
import commandsabstraction.CommandRequest;
import commandsabstraction.CommandResult;
import utilities.CollectionManager;

public abstract class CommandWithoutArgs extends CollectionCommand {

    public CommandWithoutArgs(CollectionManager manager) {
        super(manager);
    }

    public CommandWithoutArgs() {

    }


    @Override
    public CommandResult execute(CommandRequest request) {
        if (!request.getArgument().isEmpty()) {
            return new CommandResult("This command doesn't require arguments", false);
        }

        return executeInternal();
    }

    protected abstract CommandResult executeInternal();


}
