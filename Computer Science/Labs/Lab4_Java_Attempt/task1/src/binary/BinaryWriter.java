package binary;

import model.Event;
import model.Schedule;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Writes Schedule into a custom binary format.
 */
public class BinaryWriter {
    public static void writeSchedule(Schedule s, String outPath) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(outPath))) {
            dos.writeInt(s.variant);
            dos.writeInt(s.isu);
            writeString(dos, s.week);

            dos.writeInt(s.days.size());
            for (Map.Entry<String, List<Event>> entry : s.days.entrySet()) {
                writeString(dos, entry.getKey());
                dos.writeInt(entry.getValue().size());
                for (Event e : entry.getValue()) {
                    writeString(dos, e.type);
                    writeString(dos, e.subject);
                    writeString(dos, e.teacher);
                    writeString(dos, e.room);
                    writeString(dos, e.address);
                    writeString(dos, e.start);
                    writeString(dos, e.end);
                    writeString(dos, e.mode);
                }
            }
        }
    }

    private static void writeString(DataOutputStream dos, String s) throws IOException {
        if (s == null) s = "";
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        dos.writeInt(bytes.length);
        dos.write(bytes);
    }
}
