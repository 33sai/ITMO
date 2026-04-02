package commands;

import argumentcommands.CommandWithoutArgs;
import commandsabstraction.CommandResult;
import utilities.CollectionManager;
import java.util.Map;
import models.MusicBand;


public class Show extends CommandWithoutArgs {

    public Show(CollectionManager manager) {
        super(manager);
    }


    @Override
    protected CommandResult executeInternal() {
        if (manager.size() == 0) {
            return new CommandResult("The collection is empty", true);
        }

        StringBuilder output = new StringBuilder();
        for (Map.Entry<String, MusicBand> entry : manager.getCollection().entrySet()) {
            output.append("Key: ").append(entry.getKey()).append("\n");
            output.append(entry.getValue()).append("\n");
            output.append("=".repeat(67)).append("\n");
        }

        return new CommandResult(output.toString(), true);
    }

    @Override
    public String getDescription() {
        return "Show all elements in collection with their keys";
    }

    @Override
    public String getUsage() {
        return "show";
    }
}
