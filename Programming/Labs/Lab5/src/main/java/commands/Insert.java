package commands;

import models.MusicBand;
import utilities.CollectionManager;
import utilities.CommandRequest;

public class Insert extends CollectionCommand {

    public Insert(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean requiresBand() {
        return true;
    }

    @Override
    public boolean requiresArgument() {
        return true;
    }

    @Override
    public CommandResult execute(CommandRequest request) {
        String key = request.getArgument();
        if (key == null || key.isEmpty()) {
            return new CommandResult("Usage: insert <key>", false);
        }

        if (manager.containsKey(key)) {
            return new CommandResult("Key '" + key + "' already exists. Use update to modify.", false);
        }

        MusicBand band = request.getBand();
        if (band == null) {
            return new CommandResult("No band data provided.", false);
        }

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
