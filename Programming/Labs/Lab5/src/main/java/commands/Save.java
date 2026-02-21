package commands;

import utilities.CollectionManager;
import utilities.FileManager;
import java.io.IOException;

public class Save implements Command {
    private FileManager fileManager;

    public Save(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public CommandResult execute(String argument, CollectionManager manager) {
        try {
            fileManager.save(manager);
            return new CommandResult("Collection saved successfully", true);
        } catch (IOException e) {
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