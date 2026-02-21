package commands;

import utilities.CollectionManager;
import java.util.List;

public class PrintFieldDescendingSinglesCount implements Command {

    @Override
    public CommandResult execute(String argument, CollectionManager manager) {
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