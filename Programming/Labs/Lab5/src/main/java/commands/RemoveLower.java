package commands;

import models.MusicBand;
import utilities.CollectionManager;
import utilities.MusicBandValidator;
import java.util.ArrayList;
import java.util.List;

public class RemoveLower implements Command {
    private MusicBandValidator validator;

    public RemoveLower(MusicBandValidator validator) {
        this.validator = validator;
    }

    @Override
    public CommandResult execute(String argument, CollectionManager manager) {
        System.out.println("Enter the band to compare with (bands lower than this will be removed):");
        MusicBand reference = validator.askMusicBand();

        List<String> toRemove = new ArrayList<>();
        for (String key : manager.getKeys()) {
            MusicBand band = manager.get(key);
            if (band.compareTo(reference) < 0) {
                toRemove.add(key);
            }
        }

        for (String key : toRemove) {
            manager.remove(key);
        }

        return new CommandResult("Removed " + toRemove.size() + " bands lower than the given band.", true);
    }

    @Override
    public String getDescription() {
        return "Remove all bands lower than the specified band";
    }

    @Override
    public String getUsage() {
        return "remove_lower (then enter band data)";
    }
}