package model;

/**
 * Represents one lesson (practice or lecture).
 */
public class Event {
    public String type;     // Практика / Лекция
    public String subject;  // Course name
    public String teacher;  // Teacher's full name
    public String room;     // Room number
    public String address;  // Address of the building
    public String start;    // Start time
    public String end;      // End time
    public String mode;     // очно / дистанционно

    @Override
    public String toString() {
        return String.format("[%s] %s | %s | %s (%s) | %s–%s | %s",
                type, subject, teacher, room, address, start, end, mode);
    }
}
