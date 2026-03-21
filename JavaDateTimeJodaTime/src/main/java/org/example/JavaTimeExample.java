package org.example;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class JavaTimeExample {

    public static void main(String[] args) {

        // Current date and time
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Now: " + now);

        // Specific date
        LocalDate date = LocalDate.of(2026, 3, 21);
        System.out.println("Date: " + date);

        // Timezone example
        ZonedDateTime zoned = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        System.out.println("Brazil Time: " + zoned);

        // Formatting
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("Formatted: " + now.format(formatter));

        // Adding time
        LocalDateTime future = now.plusDays(5).plusHours(3);
        System.out.println("Future: " + future);

        // Difference between dates
        Duration duration = Duration.between(now, future);
        System.out.println("Hours difference: " + duration.toHours());
    }
}