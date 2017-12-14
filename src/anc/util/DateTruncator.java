package anc.util;


import java.util.Calendar;

import static anc.util.DateType.DAY;
import static anc.util.DateType.HOUR;
import static anc.util.DateType.MILLISECOND;
import static anc.util.DateType.MINUTE;
import static anc.util.DateType.MONTH;
import static anc.util.DateType.SECOND;
import static anc.util.DateType.YEAR;

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

    public static void main(String[] args) {
        int time = Integer.MAX_VALUE >> 4;
        long t = System.nanoTime();
        Calendar c = Calendar.getInstance();
//        BICubeYearMonthDayColumn column = new BICubeYearMonthDayColumn(null, null, null);
//        for (int i = 0; i < time; i++) {
//            column.convertDate(System.currentTimeMillis(), c);
//        }
        System.out.println((System.nanoTime() - t) / 1000000);

        t = System.nanoTime();
        c = Calendar.getInstance();
//        DateType[] dateTypes = {DateType.YEAR, DateType.MONTH, DateType.DAY};
        for (int i = 0; i < time; i++) {
            c.setTimeInMillis(System.currentTimeMillis());
            truncate(c, YEAR, DateType.MONTH, DAY);
        }
        System.out.println((System.nanoTime() - t) / 1000000);
    }

}
