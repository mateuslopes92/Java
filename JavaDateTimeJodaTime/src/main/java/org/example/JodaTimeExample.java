package org.example;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class JodaTimeExample {

    public static void main(String[] args) {
        System.out.println("-------JodaTimeExample-------");

        // Current date and time
        DateTime now = DateTime.now();
        System.out.println("Now: " + now);

        // Specific date
        LocalDate date = new LocalDate(2026, 3, 21);
        System.out.println("Date: " + date);

        // Timezone
        DateTime zoned = DateTime.now(DateTimeZone.forID("America/Sao_Paulo"));
        System.out.println("Brazil Time: " + zoned);

        // Formatting
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
        System.out.println("Formatted: " + now.toString(formatter));

        // Adding time
        DateTime future = now.plusDays(5).plusHours(3);
        System.out.println("Future: " + future);

        // Difference
        Duration duration = new Duration(now, future);
        System.out.println("Hours difference: " + duration.getStandardHours());

        System.out.println("-----------------------------");
        System.out.println("\n");
    }
}