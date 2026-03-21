package commands;

import models.MusicBand;
import utilities.CollectionManager;
import utilities.CommandRequest;

import java.util.ArrayList;
import java.util.List;

public class RemoveGreater extends CollectionCommand {

    public RemoveGreater(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean requiresBand() {
        return true;
    }

    @Override
    public CommandResult execute(CommandRequest request) {
        MusicBand referenceBand = request.getBand();
        if (referenceBand == null) {
            return new CommandResult("No reference band data provided.", false);
        }

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
