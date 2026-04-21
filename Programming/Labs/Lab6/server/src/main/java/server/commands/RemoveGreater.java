package server.commands;

import server.commandbase.CommandWithElement;
import common.commandsabstraction.CommandResult;
import common.models.MusicBand;
import server.utilities.CollectionManager;
import common.commandsabstraction.CommandRequest;

public class RemoveGreater extends CommandWithElement {

    public RemoveGreater(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean requiresBand() {
        return true;
    }

    @Override
    protected CommandResult executeInternal(CommandRequest request) {
        MusicBand referenceBand = request.getBand();
        int removedCount = manager.removeBandsGreaterThan(referenceBand);
        return new CommandResult("Removed " + removedCount + " bands greater than the given band.", true);
    }

    @Override
    public String getDescription() {
        return "Remove all bands greater than the specified band";
    }

    @Override
    public String getUsage() {
        return "remove_greater";
    }
}


