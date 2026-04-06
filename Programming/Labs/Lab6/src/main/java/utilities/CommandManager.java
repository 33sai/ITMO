// LEGACY CLASS

package utilities;

import commands.*;
import commandsabstraction.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private HashMap<String, Command> commands = new HashMap<>();

    public CommandManager(CollectionManager manager, String saveFilename) {
        commands.put("help", new Help(this.commands));
        commands.put("info", new Info(manager));
        commands.put("show", new Show(manager));
        commands.put("clear", new Clear(manager));
        commands.put("exit", new Exit());
        commands.put("save", new Save(new FileManager(saveFilename), manager));

        commands.put("insert", new Insert(manager));
        commands.put("update", new Update(manager));
        commands.put("remove_greater", new RemoveGreater(manager));
        commands.put("remove_lower", new RemoveLower(manager));

        commands.put("remove_key", new RemoveKey(manager));
        commands.put("remove_greater_key", new RemoveGreaterKey(manager));

        commands.put("group_counting_by_name", new GroupCountingByName(manager));
        commands.put("count_by_number_of_participants", new CountByNumberOfParticipants(manager));
        commands.put("print_field_descending_singles_count", new PrintFieldDescendingSinglesCount(manager));

        commands.put("execute_script", new ExecuteScript(manager,this));
    }

    public Command getCommand(String name) {
        return this.commands.get(name);
    }

    public Map<String, Command> getAllCommands() {
        return this.commands;
    }
}