package commands;

import models.MusicBand;
import utilities.CollectionManager;
import utilities.MusicBandValidator;
import models.*;

import java.time.LocalDate;

public class Update implements Command {
    private MusicBandValidator validator;

    public Update(MusicBandValidator validator) {
        this.validator = validator;
    }

    @Override
    public CommandResult execute(String argument, CollectionManager manager) {
        if (argument.isEmpty()) {
            return new CommandResult("Usage: update <id>", false);
        }

        long id;
        try {
            id = Long.parseLong(argument);
        } catch (NumberFormatException e) {
            return new CommandResult("ID must be a number.", false);
        }

        MusicBand existing = manager.getBandById(id);
        if (existing == null) {
            return new CommandResult("No band found with ID " + id, false);
        }

        // Find the key for this band
        String key = null;
        for (String k : manager.getKeys()) {
            if (manager.get(k).getId() == id) {
                key = k;
                break;
            }
        }
        if (key == null) return new CommandResult("Error: band found but key missing? Should not happen.", false);

        System.out.println("Enter new values for the band (leave empty to keep current value):");

        // Ask for each field, using current as default
        String newName = askWithDefault("Name", existing.getName());
        Double newX = askDoubleWithDefault("X coordinate", existing.getCoordinates().getX());
        Integer newY = askIntWithDefault("Y coordinate", existing.getCoordinates().getY());
        Long newParticipants = askLongWithDefault("Number of participants", existing.getNumberOfParticipants());
        Integer newSingles = askIntWithDefault("Singles count", existing.getSinglesCount());
        String newEst = askStringWithDefault("Establishment date (YYYY-MM-DD)",
                existing.getEstablishmentDate() == null ? "" : existing.getEstablishmentDate().toString());
        String newGenre = askStringWithDefault("Genre",
                existing.getGenre() == null ? "" : existing.getGenre().name());
        String newStudio = askStringWithDefault("Studio name",
                existing.getStudio() == null || existing.getStudio().getName() == null ? "" : existing.getStudio().getName());

        // Build updated band
        try {
            Coordinates newCoords = new Coordinates(
                    newX != null ? newX : existing.getCoordinates().getX(),
                    newY != null ? newY : existing.getCoordinates().getY()
            );
            LocalDate estDate = (newEst == null || newEst.isEmpty()) ? existing.getEstablishmentDate() : LocalDate.parse(newEst);
            MusicGenre genre = (newGenre == null || newGenre.isEmpty()) ? existing.getGenre() : MusicGenre.valueOf(newGenre.toUpperCase());
            Studio studio = (newStudio == null || newStudio.isEmpty()) ? existing.getStudio() : new Studio(newStudio);

            MusicBand updated = new MusicBand(
                    newName != null ? newName : existing.getName(),
                    newCoords,
                    newParticipants != null ? newParticipants : existing.getNumberOfParticipants(),
                    newSingles != null ? newSingles : existing.getSinglesCount(),
                    estDate,
                    genre,
                    studio
            );
            updated.setId(id);  // preserve ID
            manager.add(key, updated);  // replace

            return new CommandResult("Band with ID " + id + " updated.", true);
        } catch (Exception e) {
            return new CommandResult("Error during update: " + e.getMessage(), false);
        }
    }

    // Helper methods
    private String askWithDefault(String field, String current) {
        System.out.print(field + " [" + (current == null ? "null" : current) + "]: ");
        String input = validator.getScanner().nextLine().trim();
        return input.isEmpty() ? null : input;
    }

    private Double askDoubleWithDefault(String field, double current) {
        System.out.print(field + " [" + current + "]: ");
        String input = validator.getScanner().nextLine().trim();
        if (input.isEmpty()) return null;
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number, keeping current.");
            return null;
        }
    }

    private Integer askIntWithDefault(String field, Integer current) {
        System.out.print(field + " [" + (current == null ? "null" : current) + "]: ");
        String input = validator.getScanner().nextLine().trim();
        if (input.isEmpty()) return null;
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number, keeping current.");
            return null;
        }
    }

    private Long askLongWithDefault(String field, Long current) {
        System.out.print(field + " [" + (current == null ? "null" : current) + "]: ");
        String input = validator.getScanner().nextLine().trim();
        if (input.isEmpty()) return null;
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number, keeping current.");
            return null;
        }
    }

    private String askStringWithDefault(String field, String current) {
        System.out.print(field + " [" + (current.isEmpty() ? "null" : current) + "]: ");
        String input = validator.getScanner().nextLine().trim();
        return input.isEmpty() ? null : input;
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