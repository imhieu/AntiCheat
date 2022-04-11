package com.lighter.util;

import java.util.*;

public class DateUtil
{
    public static String formatDateDiff(final long timeInMillis) {
        final GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(timeInMillis);
        return formatDateDiff(new GregorianCalendar(), gregorianCalendar);
    }
    
    public static String formatDateDiff(final Calendar calendar, final Calendar calendar2) {
        boolean b = false;
        if (calendar2.equals(calendar)) {
            return "now";
        }
        if (calendar2.after(calendar)) {
            b = true;
        }
        final StringBuilder sb = new StringBuilder();
        final int[] array = { 1, 2, 5, 11, 12, 13 };
        final String[] array2 = { "years", "years", "month", "month", "days", "days", "hours", "hours", "minutes", "minutes", "seconds", "seconds" };
        for (int n = 0, n2 = 0; n2 < array.length && n <= 2; ++n2) {
            final int dateDiff = dateDiff(array[n2], calendar, calendar2, b);
            if (dateDiff > 0) {
                ++n;
                sb.append(" ").append(dateDiff).append(" ").append(array2[n2 * 2 + ((dateDiff > 1) ? 1 : 0)]);
            }
        }
        if (sb.length() == 0) {
            return "now";
        }
        return String.valueOf(sb).trim();
    }
    
    static int dateDiff(final int n, final Calendar calendar, final Calendar calendar2, final boolean b) {
        int n2 = 0;
        long timeInMillis = calendar.getTimeInMillis();
        while ((b && !calendar.after(calendar2)) || (!b && !calendar.before(calendar2))) {
            timeInMillis = calendar.getTimeInMillis();
            calendar.add(n, b ? 1 : -1);
            ++n2;
        }
        --n2;
        calendar.setTimeInMillis(timeInMillis);
        return n2;
    }
}
