package commands;

import models.MusicBand;
import utilities.CollectionManager;
import utilities.MusicBandValidator;

public class Insert implements Command {
    private MusicBandValidator validator;

    public Insert(MusicBandValidator validator) {
        this.validator = validator;
    }

    @Override
    public CommandResult execute(String argument, CollectionManager manager) {
        if (argument.isEmpty()) {
            return new CommandResult("Usage: insert <key>", false);
        }
        String key = argument;

        if (manager.containsKey(key)) {
            return new CommandResult("Key '" + key + "' already exists. Use update to modify.", false);
        }

        // Ask for all fields using validator
        MusicBand band = validator.askMusicBand();
        manager.add(key, band);

        return new CommandResult("Band added successfully with key '" + key + "'.", true);
    }

    @Override
    public String getDescription() {
        return "Add a new band with the specified key";
    }

    @Override
    public String getUsage() {
        return "insert <key>";
    }
}