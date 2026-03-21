package argumentcommands;

import commandsabstraction.CollectionCommand;
import commandsabstraction.CommandRequest;
import commandsabstraction.CommandResult;
import models.MusicBand;
import utilities.CollectionManager;

public abstract class CommandWithElement extends CollectionCommand {

    public CommandWithElement(CollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResult execute(CommandRequest request) {
        MusicBand referenceBand = request.getBand();
        if (referenceBand == null) {
            return new CommandResult("No reference band data provided.", false);
        }
        return executeInternal(request);
    }

    protected abstract CommandResult executeInternal(CommandRequest request);

    @Override
    public boolean requiresBand() {
        return true;
    }
}
