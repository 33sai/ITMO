import console.Console;
import utilities.CollectionManager;
import utilities.CommandManager;
import utilities.FileManager;
import utilities.MusicBandValidator;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filename = System.getenv("MUSIC_BAND_DATA");
        if (filename == null) {
            System.err.println("Error: Set MUSIC_BAND_DATA as environmental variable");
            System.exit(1);
        }

        CollectionManager collectionManager = new CollectionManager();
        FileManager fileManager = new FileManager(filename);

        try {
            fileManager.load(collectionManager);
            System.out.println("Loaded " + collectionManager.size() + " bands from file.");
        } catch (IOException e) {
            System.out.println("Could not load from file. Starting with empty collection.");
            System.out.println("Reason: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        MusicBandValidator validator = new MusicBandValidator(scanner);
        CommandManager commandManager = new CommandManager(collectionManager, filename);
        Console console = new Console(commandManager, validator, scanner);

        console.run();
        scanner.close();
    }
}