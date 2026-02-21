package utilities;

import commands.*;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private HashMap<String, Command> commands = new HashMap<>();

    public CommandManager(CollectionManager manager, MusicBandValidator validator) {
        // Basic operational commands
        commands.put("help", new Help(commands));
        commands.put("info", new Info());
        commands.put("show", new Show());
        commands.put("clear", new Clear());
        commands.put("exit", new Exit());
        commands.put("save", new Save(new FileManager("bands.xml")));

        // Commands that need validator
        commands.put("insert", new Insert(validator));
        commands.put("update", new Update(validator));
        commands.put("remove_greater", new RemoveGreater(validator));
        commands.put("remove_lower", new RemoveLower(validator));

        // Key based commands
        commands.put("remove_key", new RemoveKey());
        commands.put("remove_greater_key", new RemoveGreaterKey());

        // Analysis commands
        commands.put("group_counting_by_name", new GroupCountingByName());
        commands.put("count_by_number_of_participants", new CountByNumberOfParticipants());
        commands.put("print_field_descending_singles_count", new PrintFieldDescendingSinglesCount());
    }

    public Command getCommand(String name) {
        return this.commands.get(name);
    }

    public Map<String, Command> getAllCommands() {
        return this.commands;
    }
}
