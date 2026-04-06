package argumentcommands;

import commandsabstraction.CollectionCommand;
import commandsabstraction.CommandRequest;
import commandsabstraction.CommandResult;
import models.MusicBand;
import utilities.CollectionManager;

/**
 * This class is extended by classes that validate Music Band objects.
 */
public abstract class CommandWithElement extends CollectionCommand {

    public CommandWithElement(CollectionManager manager) {
        super(manager);
    }

    /**
     * Validates the Music Band object so redundant validations don't happen.
     * @param request the request containing argument and optional data.
     * @return The method that  subclasses have to implement.
     */
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
