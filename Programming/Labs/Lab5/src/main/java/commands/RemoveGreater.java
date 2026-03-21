package commands;

import argumentcommands.CommandWithElement;
import commandsabstraction.CommandResult;
import models.MusicBand;
import utilities.CollectionManager;
import commandsabstraction.CommandRequest;

import java.util.ArrayList;
import java.util.List;

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

        List<String> toRemove = new ArrayList<>();
        for (String key : manager.getKeys()) {
            MusicBand band = manager.get(key);
            if (band.compareTo(referenceBand) > 0) {
                toRemove.add(key);
            }
        }

        for (String key : toRemove) {
            manager.remove(key);
        }

        return new CommandResult("Removed " + toRemove.size() + " bands greater than the given band.", true);
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
