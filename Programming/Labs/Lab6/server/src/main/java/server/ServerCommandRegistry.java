package server;

import common.commandsabstraction.Command;
import common.network.CommandMeta;
import server.commands.*;
import server.utilities.CollectionManager;
import server.utilities.FileManager;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerCommandRegistry {
    private final Map<String, Command> publicCommands = new LinkedHashMap<>();
    private final Map<String, Command> serverOnlyCommands = new LinkedHashMap<>();

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

    public Map<String, CommandMeta> exportPublicCommandMeta() {
        return publicCommands.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new CommandMeta(
                                entry.getValue().getUsage(),
                                entry.getValue().getDescription(),
                                entry.getValue().requiresArgument(),
                                entry.getValue().requiresBand()
                        ),
                        (left, right) -> left,
                        LinkedHashMap::new
                ));
    }
}
