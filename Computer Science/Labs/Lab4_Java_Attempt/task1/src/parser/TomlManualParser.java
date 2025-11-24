package parser;

import model.Event;
import model.Schedule;

import java.io.*;

/**
 * Very simple manual TOML parser that supports:
 *  - key = "value"
 *  - [[day.events]] sections with event properties
 * No regex, no external libraries.
 */
public class TomlManualParser {

    public static Schedule parseFile(String path) throws IOException {
        Schedule schedule = new Schedule();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            String currentDay = null;
            Event current = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                // array of tables [[day.events]]
                if (line.startsWith("[[") && line.endsWith("]]")) {
                    String name = line.substring(2, line.length() - 2).trim();
                    int dot = name.indexOf('.');
                    currentDay = (dot > 0) ? name.substring(0, dot) : name;
                    current = new Event();
                    continue;
                }

                int eq = line.indexOf('=');
                if (eq < 0) continue;
                String key = line.substring(0, eq).trim();
                String val = line.substring(eq + 1).trim();

                // remove quotes if any
                if (val.startsWith("\"") && val.endsWith("\""))
                    val = val.substring(1, val.length() - 1);

                // top-level keys
                if (currentDay == null) {
                    switch (key) {
                        case "variant" -> schedule.variant = Integer.parseInt(val);
                        case "isu" -> schedule.isu = Integer.parseInt(val);
                        case "week" -> schedule.week = val;
                    }
                    continue;
                }

                // inside an event table
                if (current != null) {
                    switch (key) {
                        case "type" -> current.type = val;
                        case "subject" -> current.subject = val;
                        case "teacher" -> current.teacher = val;
                        case "room" -> current.room = val;
                        case "address" -> current.address = val;
                        case "start" -> current.start = val;
                        case "end" -> current.end = val;
                        case "mode" -> current.mode = val;
                    }

                    // push event when all required fields filled
                    if (current.subject != null && current.end != null) {
                        schedule.addEvent(currentDay, current);
                        current = new Event();
                    }
                }
            }
        }

        return schedule;
    }
}
