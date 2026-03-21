package commands;

import utilities.CollectionManager;
import utilities.CommandRequest;
import utilities.FileManager;

public class Save extends CollectionCommand {
    private FileManager fileManager;

    public Save(FileManager fileManager, CollectionManager manager) {
        super(manager);
        this.fileManager = fileManager;
    }

    @Override
    public CommandResult execute(CommandRequest request) {
        try {
            fileManager.save(manager);
            return new CommandResult("Collection saved successfully.", true);
        } catch (Exception e) {
            return new CommandResult("Error saving: " + e.getMessage(), false);
        }
    }

    @Override
    public String getDescription() {
        return "Save collection to file";
    }

    @Override
    public String getUsage() {
        return "save";
    }
}
