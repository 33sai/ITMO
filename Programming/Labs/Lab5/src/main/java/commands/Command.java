package commands;

import utilities.CollectionManager;

/**
 * All commands must implement this interface.
 */
public interface Command {
    /**
     * This method is used to execute a command.
     * @param argument argument for the command (can be empty).
     * @param manager collection manager to operate on.
     * @return  the result after executing the command.
     */
    CommandResult execute(String argument, CollectionManager manager);


    /**
     * @return a short description of a command.
     */
    String getDescription();

    /**
     * @return  an example of the usage.
     */
    String getUsage();
}
