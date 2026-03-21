package commands;

import argumentcommands.CommandWIthIdAndElement;
import argumentcommands.CommandWithKeyAndElement;
import commandsabstraction.CollectionCommand;
import commandsabstraction.CommandResult;
import models.MusicBand;
import utilities.CollectionManager;
import commandsabstraction.CommandRequest;

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
        MusicBand bandToUpdate = request.getBand();
        Long id = Long.parseLong(request.getArgument());

        String key = "";
        for (String k : manager.getKeys()) {
            if (manager.get(k).getId() == id) {
                key = k;
                break;
            }
        }

        bandToUpdate.setId(id);
        manager.add(key, bandToUpdate);
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
