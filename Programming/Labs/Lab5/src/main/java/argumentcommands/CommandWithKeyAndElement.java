package argumentcommands;

import commandsabstraction.CollectionCommand;
import commandsabstraction.CommandRequest;
import commandsabstraction.CommandResult;
import models.MusicBand;
import utilities.CollectionManager;

public abstract class CommandWithKeyAndElement extends CollectionCommand {

    private CommandRequest request;

    public CommandWithKeyAndElement(CollectionManager manager) {
        super(manager);
    }

    public CommandResult execute(CommandRequest request) {
        MusicBand band = request.getBand();
        String key = request.getArgument();
        if (key == null || key.isEmpty()) {
            return new CommandResult("Usage: " + getUsage(), false);
        }


        if (!manager.containsKey(key)) {
            return new CommandResult("Band with key " + key + " is already used. Use 'update' to modify", false);
        }

        return executeInternal(request);
    }


    protected abstract CommandResult executeInternal(CommandRequest request);


    @Override
    public boolean requiresArgument() {
        return true;
    }

    @Override
    public boolean requiresBand() {
        return true;
    }

}
