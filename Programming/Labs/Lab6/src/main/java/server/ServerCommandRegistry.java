package server;

import commands.*;
import commandsabstraction.Command;
import utilities.CollectionManager;
import utilities.FileManager;

import java.util.HashMap;
import java.util.Map;

public class ServerCommandRegistry {
    private final Map<String, Command> publicCommands = new HashMap<>();
    private final Map<String, Command> serverOnlyCommands = new HashMap<>();

    public ServerCommandRegistry(CollectionManager manager, String saveFileName) {
        publicCommands.put("help", new Help(publicCommands));
        publicCommands.put("info", new Info(manager));
        publicCommands.put("show", new Show(manager));
        publicCommands.put("clear", new Clear(manager));

        publicCommands.put("insert", new Insert(manager));
        publicCommands.put("update", new Update(manager));
        publicCommands.put("remove_greater", new RemoveGreater(manager));
        publicCommands.put("remove_lower", new RemoveLower(manager));

        publicCommands.put("remove_key", new RemoveKey(manager));
        publicCommands.put("remove_greater_key", new RemoveGreaterKey(manager));

        publicCommands.put("group_counting_by_name", new GroupCountingByName(manager));
        publicCommands.put("count_by_number_of_participants", new CountByNumberOfParticipants(manager));
        publicCommands.put("print_field_descending_singles_count", new PrintFieldDescendingSinglesCount(manager));

        serverOnlyCommands.put("save", new Save(new FileManager(saveFileName), manager));
    }

    public Command getPublicCommand(String commandName) {
        return publicCommands.get(commandName);
    }

    public Command getServerOnlyCommand(String commandName) {
        return serverOnlyCommands.get(commandName);
    }
}

