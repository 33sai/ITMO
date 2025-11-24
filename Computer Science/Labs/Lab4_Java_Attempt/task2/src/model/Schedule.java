package model;

import java.util.*;

/**
 * Represents the full schedule for a student.
 */
public class Schedule {
    public int variant;
    public int isu;
    public String week;
    public Map<String, List<Event>> days = new LinkedHashMap<>();

    public void addEvent(String day, Event ev) {
        days.computeIfAbsent(day, d -> new ArrayList<>()).add(ev);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Variant=").append(variant)
          .append(" | ISU=").append(isu)
          .append(" | Week=").append(week).append("\n");
        for (var day : days.entrySet()) {
            sb.append("\nDay: ").append(day.getKey()).append("\n");
            for (Event ev : day.getValue()) {
                sb.append("  ").append(ev).append("\n");
            }
        }
        return sb.toString();
    }
}
