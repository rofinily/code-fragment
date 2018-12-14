package me.anchore.util;


import java.util.Calendar;

import static me.anchore.util.DateType.DAY;
import static me.anchore.util.DateType.HOUR;
import static me.anchore.util.DateType.MILLISECOND;
import static me.anchore.util.DateType.MINUTE;
import static me.anchore.util.DateType.MONTH;
import static me.anchore.util.DateType.SECOND;
import static me.anchore.util.DateType.YEAR;

/**
 * example:
 * DateTruncator.truncate(..., DateType.YEAR, DateType.MONTH)
 * will truncate time to YEAR-MONTH
 * for input: 2017/11/29 12:23:34
 * output as: 2017/11/01 00:00:00
 *
 * @author anchore
 * @date 2017/11/29
 */
public final class DateTruncator {

    static final DateType[] ALL = {YEAR, MONTH, DAY, HOUR, MINUTE, SECOND, MILLISECOND};

    public static long truncate(Calendar c, DateType... dateTypes) {
        int[] values = new int[dateTypes.length];
        for (int i = 0, len = dateTypes.length; i < len; i++) {
            values[i] = dateTypes[i].get(c);
        }

        c.clear();

        for (int i = 0, len = dateTypes.length; i < len; i++) {
            dateTypes[i].set(values[i], c);
        }
        return c.getTimeInMillis();
    }
}