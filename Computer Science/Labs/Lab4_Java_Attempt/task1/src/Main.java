import model.Schedule;
import parser.TomlManualParser;
import binary.BinaryWriter;
import binary.BinaryReader;

/**
 * Main program for Task 1.
 * Parses TOML -> Binary -> Reads Binary -> Prints result.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        String tomlPath = "schedule_503266_v82.toml";
        String binPath  = "schedule_503266_v82.bin";

        // Parse TOML
        Schedule s = TomlManualParser.parseFile(tomlPath);
        System.out.println("Parsed schedule:\n" + s);

        // Write to binary
        BinaryWriter.writeSchedule(s, binPath);
        System.out.println("Binary file written: " + binPath);

        // Read back
        Schedule read = BinaryReader.readSchedule(binPath);
        System.out.println("\nRestored from binary:\n" + read);
    }
}
