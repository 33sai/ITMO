package commands;

import utilities.CollectionManager;
import utilities.CommandRequest;

import java.util.List;

public class PrintFieldDescendingSinglesCount extends CollectionCommand {

    public PrintFieldDescendingSinglesCount(CollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResult execute(CommandRequest request) {
        List<Integer> singles = manager.getAllSinglesCountDescending();
        if (singles.isEmpty()) {
            return new CommandResult("No singles count values found.", true);
        }
        StringBuilder result = new StringBuilder("Singles counts (descending):\n");
        for (Integer s : singles) {
            result.append(s).append("\n");
        }
        return new CommandResult(result.toString(), true, singles);
    }

    @Override
    public String getDescription() {
        return "Print all singlesCount values in descending order";
    }

    @Override
    public String getUsage() {
        return "print_field_descending_singles_count";
    }
}
