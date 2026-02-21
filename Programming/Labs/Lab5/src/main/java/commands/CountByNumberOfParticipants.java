package commands;

import utilities.CollectionManager;

public class CountByNumberOfParticipants implements Command {

    @Override
    public CommandResult execute(String argument, CollectionManager manager) {
        if (argument.isEmpty()) {
            return new CommandResult("Usage: count_by_number_of_participants <number>", false);
        }
        long number;
        try {
            number = Long.parseLong(argument);
        } catch (NumberFormatException e) {
            return new CommandResult("Invalid number. Please enter a valid long.", false);
        }

        long count = manager.getBandsByParticipants(number).size();
        return new CommandResult("Number of bands with " + number + " participants: " + count, true, count);
    }

    @Override
    public String getDescription() {
        return "Count bands with the given number of participants";
    }

    @Override
    public String getUsage() {
        return "count_by_number_of_participants <number>";
    }
}