package commands;

import models.MusicBand;
import utilities.CollectionManager;
import utilities.CommandRequest;

public class Update extends CollectionCommand {

    public Update(CollectionManager manager) {
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
        String argument = request.getArgument();
        if (argument == null || argument.isEmpty()) {
            return new CommandResult("Usage: update <id>", false);
        }

        long id;
        try {
            id = Long.parseLong(argument);
        } catch (NumberFormatException e) {
            return new CommandResult("ID must be a number.", false);
        }

        MusicBand existing = manager.getBandById(id);
        if (existing == null) {
            return new CommandResult("No band found with ID " + id, false);
        }

        String key = null;
        for (String k : manager.getKeys()) {
            if (manager.get(k).getId() == id) {
                key = k;
                break;
            }
        }
        if (key == null) {
            return new CommandResult("Error: band found but key missing.", false);
        }

        MusicBand bandToUpdate = request.getBand();
        if (bandToUpdate == null) {
            return new CommandResult("No band data provided.", false);
        }

        bandToUpdate.setId(id);
        manager.add(key, bandToUpdate);

        return new CommandResult("Band with ID " + id + " updated.", true);
    }

    @Override
    public String getDescription() {
        return "Update a band by its ID";
    }

    @Override
    public String getUsage() {
        return "update <id>";
    }
}
