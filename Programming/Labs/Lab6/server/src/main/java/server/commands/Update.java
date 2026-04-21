package server.commands;

import server.commandbase.CommandWIthIdAndElement;
import server.commandbase.CommandWithKeyAndElement;
import common.commandsabstraction.CommandResult;
import common.models.MusicBand;
import server.utilities.CollectionManager;
import common.commandsabstraction.CommandRequest;

public class Update extends CommandWIthIdAndElement {

    public Update(CollectionManager manager) {
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
        long id = Long.parseLong(request.getArgument().trim());
        MusicBand replacement = MusicBand.createServerManagedCopy(request.getBand());

        boolean updated = manager.replaceBandById(id, replacement);
        if (!updated) {
            return new CommandResult("No band found with ID " + id, false);
        }

        return new CommandResult("Band with ID " + id + " updated.", true);

    }

    @Override
    public String getDescription() {
        return "Update a band by its ID";
    }

    @Override
    public String getUsage() {
        return "update <id>";
    }
}


