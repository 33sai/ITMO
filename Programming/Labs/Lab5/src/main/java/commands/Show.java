package commands;

import models.MusicBand;
import utilities.CollectionManager;

import java.util.Collection;


public class Show implements Command {

    @Override
    public CommandResult execute(String argument, CollectionManager manager) {
        Collection<MusicBand> bands = manager.getAll();
        if (bands.isEmpty()) {
            return new CommandResult("The collection is empty", true);
        }

        StringBuilder output = new StringBuilder();
        for (MusicBand band: bands) {
            output.append(band).append("\n");
            output.append("=".repeat(67));
        }

        return new CommandResult(output.toString(), true);
    }

    @Override
    public String getDescription() {
        return "Show all elements in collection";
    }

    @Override
    public String getUsage() {
        return "show";
    }
}
