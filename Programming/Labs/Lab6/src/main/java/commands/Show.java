package commands;

import argumentcommands.CommandWithoutArgs;
import commandsabstraction.CommandResult;
import utilities.CollectionManager;
import java.util.stream.Collectors;


public class Show extends CommandWithoutArgs {

    public Show(CollectionManager manager) {
        super(manager);
    }


    @Override
    protected CommandResult executeInternal() {
        if (manager.size() == 0) {
            return new CommandResult("The collection is empty", true);
        }

        String output = manager.getEntriesSortedByBandName().stream()
                .map(entry -> "Key: " + entry.getKey() + "\n" + entry.getValue() + "\n" + "=".repeat(67))
                .collect(Collectors.joining("\n"));

        return new CommandResult(output, true);
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
