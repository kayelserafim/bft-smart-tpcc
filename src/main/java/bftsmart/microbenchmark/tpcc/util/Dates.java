package bftsmart.microbenchmark.tpcc.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Dates {

    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private Dates() {
        super();
    }

    /**
     * Returns a copy of this LocalDateTime in milliseconds with the time by
     * <code>ChronoUnit.MINUTES</code> truncated.
     * 
     * @return the number of milliseconds since the epoch of
     *         1970-01-01T00:00:00Z
     */
    public static long now() {
        return LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    /**
     * Formats this date-time using the specified formatter.
     * 
     * @param timeMillis
     *            - the number of milliseconds from 1970-01-01T00:00:00Z
     * @param timeFormatter
     *            - the formatter to use, not null
     * @return the formatted date-time string, not null
     */
    public static String format(long timeMillis, DateTimeFormatter timeFormatter) {
        return Instant.ofEpochMilli(timeMillis).atOffset(ZoneOffset.UTC).format(timeFormatter);
    }

}
