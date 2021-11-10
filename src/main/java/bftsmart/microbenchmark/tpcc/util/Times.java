package bftsmart.microbenchmark.tpcc.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Times {

    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private Times() {
        super();
    }

    /**
     * Returns a copy of this LocalDateTime in milliseconds with the time by
     * <code>ChronoUnit.MINUTES</code>.
     * truncated.
     * 
     * @return the number of milliseconds since the epoch of
     *         1970-01-01T00:00:00Z
     */
    public static long currentTimeMillis() {
        return LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toInstant(ZoneOffset.UTC).toEpochMilli();
    }

}
