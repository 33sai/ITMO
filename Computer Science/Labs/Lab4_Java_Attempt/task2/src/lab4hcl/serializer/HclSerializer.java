package lab4hcl.serializer;

import model.Event;
import model.Schedule;

import java.util.List;
import java.util.Map;

/**
 * Simple, readable HCL serializer for the Schedule object.
 * Produces HCL-like output that is easy to inspect.
 */
public final class HclSerializer {

    private HclSerializer() {}

    /**
     * Convert Schedule object to HCL string.
     *
     * Example output:
     * variant = 82
     * isu = 503266
     * week = "четная"
     *
     * day "tuesday" {
     *   event {
     *     type = "Практика"
     *     subject = "Алгебра и алгоритмы"
     *     ...
     *   }
     * }
     */
    public static String toHcl(Schedule s) {
        StringBuilder sb = new StringBuilder();
        sb.append("// Generated HCL from binary schedule\n\n");
        sb.append("variant = ").append(s.variant).append("\n");
        sb.append("isu = ").append(s.isu).append("\n");
        sb.append("week = \"").append(escape(s.week)).append("\"\n\n");

        for (Map.Entry<String, List<Event>> dayEntry : s.days.entrySet()) {
            String day = dayEntry.getKey();
            sb.append("day \"").append(escape(day)).append("\" {\n");
            for (Event ev : dayEntry.getValue()) {
                sb.append("  event {\n");
                sb.append("    type    = \"").append(escape(ev.type)).append("\"\n");
                sb.append("    subject = \"").append(escape(ev.subject)).append("\"\n");
                sb.append("    teacher = \"").append(escape(ev.teacher)).append("\"\n");
                sb.append("    room    = \"").append(escape(ev.room)).append("\"\n");
                sb.append("    address = \"").append(escape(ev.address)).append("\"\n");
                sb.append("    start   = \"").append(escape(ev.start)).append("\"\n");
                sb.append("    end     = \"").append(escape(ev.end)).append("\"\n");
                sb.append("    mode    = \"").append(escape(ev.mode)).append("\"\n");
                sb.append("  }\n");
            }
            sb.append("}\n\n");
        }

        return sb.toString();
    }

    // Escape double quotes in values to keep HCL valid
    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\"");
    }
}
