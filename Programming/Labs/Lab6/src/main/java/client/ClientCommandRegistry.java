package client;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClientCommandRegistry {
    public record CommandMeta(String usage, String description, boolean requiresArgument, boolean requiresBand) {}

    private final Map<String, CommandMeta> commands = new LinkedHashMap<>();

    public ClientCommandRegistry() {
        commands.put("help", new CommandMeta("help", "Show all commands", false, false));
        commands.put("info", new CommandMeta("info", "Show collection info", false, false));
        commands.put("show", new CommandMeta("show", "Show all elements in the collection", false, false));
        commands.put("clear", new CommandMeta("clear", "Clear the collection", false, false));

        commands.put("insert", new CommandMeta("insert <key>", "Add a new band with key", true, true));
        commands.put("update", new CommandMeta("update <id>", "Update a band by id", true, true));
        commands.put("remove_greater", new CommandMeta("remove_greater", "Remove all bands greater than given band", false, true));
        commands.put("remove_lower", new CommandMeta("remove_lower", "Remove all bands lower than given band", false, true));

        commands.put("remove_key", new CommandMeta("remove_key <key>", "Remove by key", true, false));
        commands.put("remove_greater_key", new CommandMeta("remove_greater_key <key>", "Remove entries with key greater than value", true, false));

        commands.put("group_counting_by_name", new CommandMeta("group_counting_by_name", "Group elements by name", false, false));
        commands.put("count_by_number_of_participants", new CommandMeta("count_by_number_of_participants <count>", "Count by number of participants", true, false));
        commands.put("print_field_descending_singles_count", new CommandMeta("print_field_descending_singles_count", "Print singlesCount in descending order", false, false));

        commands.put("execute_script", new CommandMeta("execute_script <file_name>", "Execute commands from script", true, false));
        commands.put("exit", new CommandMeta("exit", "Exit client application", false, false));
    }

    public CommandMeta get(String commandName) {
        return commands.get(commandName);
    }

    public boolean contains(String commandName) {
        return commands.containsKey(commandName);
    }

    public String buildHelpText() {
        StringBuilder help = new StringBuilder("Available client commands:\n");
        commands.forEach((name, meta) -> help.append(" ")
                .append(name)
                .append(" - ")
                .append(meta.description())
                .append("\n    Usage: ")
                .append(meta.usage())
                .append("\n"));
        return help.toString();
    }
}

