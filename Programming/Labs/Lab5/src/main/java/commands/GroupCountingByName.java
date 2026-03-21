package commands;

import utilities.CollectionManager;
import utilities.CommandRequest;

import java.util.Map;

public class GroupCountingByName extends CollectionCommand {

    public GroupCountingByName(CollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResult execute(CommandRequest request) {
        Map<String, Long> groups = manager.countByName();
        if (groups.isEmpty()) {
            return new CommandResult("Collection is empty.", false);
        }
        StringBuilder result = new StringBuilder("Groups by name:\n");
        groups.forEach((name, count) -> result.append(name).append(": ").append(count).append("\n"));
        return new CommandResult(result.toString(), true, groups);
    }

    @Override
    public String getDescription() {
        return "Group elements by name and show count per group";
    }

    @Override
    public String getUsage() {
        return "group_counting_by_name";
    }
}
