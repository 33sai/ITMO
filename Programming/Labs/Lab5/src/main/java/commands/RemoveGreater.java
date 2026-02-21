package commands;

import models.MusicBand;
import utilities.CollectionManager;
import utilities.MusicBandValidator;
import java.util.ArrayList;
import java.util.List;

public class RemoveGreater implements Command {
    private MusicBandValidator validator;

    public RemoveGreater(MusicBandValidator validator) {
        this.validator = validator;
    }

    @Override
    public CommandResult execute(String argument, CollectionManager manager) {
        // argument ignored; we ask for a band interactively
        System.out.println("Enter the band to compare with (bands greater than this will be removed):");
        MusicBand reference = validator.askMusicBand();

        List<String> toRemove = new ArrayList<>();
        for (String key : manager.getKeys()) {
            MusicBand band = manager.get(key);
            if (band.compareTo(reference) > 0) {
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
        return "remove_greater (then enter band data)";
    }
}