package commands;

import argumentcommands.CommandWithKeyAndElement;
import commandsabstraction.CollectionCommand;
import commandsabstraction.CommandResult;
import models.MusicBand;
import utilities.CollectionManager;
import commandsabstraction.CommandRequest;

public class Insert extends CommandWithKeyAndElement {

    public Insert(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean requiresBand() {
        return true;
    }

    @Override
    public boolean requiresArgument() {
        return true;
    }


    @Override
    protected CommandResult executeInternal(CommandRequest request) {
        String key = request.getArgument();
        if (manager.containsKey(key)) {
            return new CommandResult("Key '" + key + "' already exists.", false);
        }

        MusicBand band = MusicBand.createServerManagedCopy(request.getBand());
        manager.add(key, band);
        return new CommandResult("Band added successfully with key '" + key + "'.", true);
    }

    @Override
    public String getDescription() {
        return "Add a new band with the specified key";
    }

    @Override
    public String getUsage() {
        return "insert <key>";
    }
}
