package commands;

import utilities.CollectionManager;

/**
 * Base class for commands that operate on the collection.
 */
public abstract class CollectionCommand implements Command {
    protected CollectionManager manager;

    public CollectionCommand(CollectionManager manager) {
        this.manager = manager;
    }
}
