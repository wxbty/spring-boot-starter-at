package site.zfei.at.util;

import java.time.Clock;
import java.util.concurrent.TimeUnit;

/**
 * @author fgh
 */
public class Now {

    private static final Clock clock = Clock.systemUTC();

    public static long millis() {
        return clock.millis();
    }

    public static long seconds() {
        return seconds(millis());
    }

    public static long seconds(long millis) {
        return TimeUnit.MILLISECONDS.toSeconds(millis);
    }

}
