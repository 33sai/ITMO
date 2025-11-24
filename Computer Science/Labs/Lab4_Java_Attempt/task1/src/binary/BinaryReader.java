package binary;

import model.Event;
import model.Schedule;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads Schedule back from the custom binary format.
 */
public class BinaryReader {

    public static Schedule readSchedule(String path) throws IOException {
        Schedule s = new Schedule();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(path))) {
            s.variant = dis.readInt();
            s.isu = dis.readInt();
            s.week = readString(dis);

            int numDays = dis.readInt();
            for (int i = 0; i < numDays; i++) {
                String day = readString(dis);
                int numEvents = dis.readInt();
                List<Event> events = new ArrayList<>();
                for (int j = 0; j < numEvents; j++) {
                    Event e = new Event();
                    e.type = readString(dis);
                    e.subject = readString(dis);
                    e.teacher = readString(dis);
                    e.room = readString(dis);
                    e.address = readString(dis);
                    e.start = readString(dis);
                    e.end = readString(dis);
                    e.mode = readString(dis);
                    events.add(e);
                }
                s.days.put(day, events);
            }
        }
        return s;
    }

    private static String readString(DataInputStream dis) throws IOException {
        int len = dis.readInt();
        if (len <= 0) return "";
        byte[] bytes = new byte[len];
        dis.readFully(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
