package cc.piner.accountbook.utils;

import java.util.Calendar;

/**
 * <p>createDate 22-9-4</p>
 * <p>fileName   DateUtil</p>
 *
 * @author KeQing
 * @version 1.0
 */
public class DateUtil {
    public static long getWeekStartMS(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }
    public static long getMonthStartMS(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }
    public static long getTodayMS() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    // 获取到的月份为0-11
    public static int getMonthDays() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int[] days = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return days[month];

    }

    public static int getMonthRemainDays() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int[] days = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return days[month] - cal.get(Calendar.DAY_OF_MONTH) + 1;
    }

    public static int getWeekRemainDays() {
        Calendar instance = Calendar.getInstance();
        int week = instance.get(Calendar.DAY_OF_WEEK);
        if (instance.getFirstDayOfWeek() == Calendar.SUNDAY) {
            week = week - 1;
            if (week == 0) {
                week = 7;
            }
        }
        return 8 - week;
    }
}
