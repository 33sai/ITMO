import console.Console;
import models.MusicBand;
import utilities.CollectionManager;
import utilities.CommandManager;
import utilities.FileManager;
import utilities.MusicBandValidator;

import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        // 1. Get filename from environment variable
        String filename = System.getenv("MUSIC_BAND_DATA");
        if (filename == null) {
            System.err.println("Error: Set MUSIC_BAND_DATA as environmental variable");
            System.exit(676767);
        }

        // 2. Create core components
        CollectionManager collectionManager = new CollectionManager();
        FileManager fileManager = new FileManager(filename);

        // 3. Load from file
        try {
            fileManager.load(collectionManager);
            System.out.println("Loaded " + collectionManager.size() + " bands from file.");
        } catch (IOException e) {
            System.out.println("Could not load from file. Starting with empty collection.");
            System.out.println("Reason: " + e.getMessage());
        }

        // 4. Create other components
        Scanner scanner = new Scanner(System.in);
        MusicBandValidator validator = new MusicBandValidator(scanner);
        CommandManager commandManager = new CommandManager(collectionManager, validator);
        Console console = new Console(collectionManager, commandManager, scanner);

        // 5. Start the program
        console.run();

        scanner.close();
    }
}