package lab4hcl;

import lab4hcl.serializer.HclSerializer;
import model.Schedule;
import binary.BinaryReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Reads the custom binary schedule and writes an HCL file.
 *
 * Usage:
 *  - Ensure schedule_503266_v82.bin is present (copy from Task1 output).
 *  - Run this class (class path must include compiled Task1 classes).
 *
 * Output:
 *  - schedule_503266_v82.hcl
 */
public class MainHcl {
    public static void main(String[] args) {
        String binPath = "schedule_503266_v82.bin";
        String outHcl = "schedule_503266_v82.hcl";

        try {
            System.out.println("Reading binary schedule from: " + binPath);
            Schedule s = BinaryReader.readSchedule(binPath);
            System.out.println("Schedule loaded. Serializing to HCL...");

            String hcl = HclSerializer.toHcl(s);
            Files.writeString(Path.of(outHcl), hcl);

            System.out.println("HCL successfully written to: " + outHcl);
            System.out.println("\n--- HCL preview (first 30 lines) ---");
            String[] lines = hcl.split("\\R");
            for (int i = 0; i < Math.min(lines.length, 30); i++) {
                System.out.println(lines[i]);
            }
            System.out.println("-------------------------------------");
             
        } catch (IOException e) {
            System.err.println("File I/O error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error during HCL serialization: " + e.getMessage());
        }
    }
}
