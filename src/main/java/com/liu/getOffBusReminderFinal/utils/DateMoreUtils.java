package com.liu.getOffBusReminderFinal.utils;



import org.apache.commons.lang3.Validate;

import java.util.Calendar;
import java.util.Date;

/**
 * @author liushuaibiao
 * @date 2023/4/27 18:12
 */
public abstract class DateMoreUtils {
    public static final long MILLIS_PER_SECOND = 1000L;
    public static final long MILLIS_PER_MINUTE = 60000L;
    public static final long MILLIS_PER_HOUR = 3600000L;
    public static final long MILLIS_PER_DAY = 86400000L;
    private static final int[] MONTH_LENGTH = new int[]{0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public DateMoreUtils() {
    }

    public static boolean isSameDay(final Date date1, final Date date2) {
        return DateUtils.isSameDay(date1, date2);
    }

    public static boolean isSameTime(final Date date1, final Date date2) {
        return date1.compareTo(date2) == 0;
    }

    public static boolean isBetween(final Date date, final Date start, final Date end) {
        if (date != null && start != null && end != null && !start.after(end)) {
            return !date.before(start) && !date.after(end);
        } else {
            throw new IllegalArgumentException("some date parameters is null or dateBein after dateEnd");
        }
    }

    public static Date truncate(final Date date, int field) {
        return DateUtils.truncate(date, field);
    }

    public static Date ceiling(final Date date, int field) {
        return DateUtils.ceiling(date, field);
    }

    public static Date addMonths(final Date date, int amount) {
        return DateUtils.addMonths(date, amount);
    }

    public static Date subtractMonths(final Date date, int amount) {
        return DateUtils.addMonths(date, -amount);
    }

    public static Date addWeeks(final Date date, int amount) {
        return DateUtils.addWeeks(date, amount);
    }

    public static Date subtractWeeks(final Date date, int amount) {
        return DateUtils.addWeeks(date, -amount);
    }

    public static Date addDays(final Date date, final int amount) {
        return DateUtils.addDays(date, amount);
    }

    public static Date subtractDays(final Date date, int amount) {
        return DateUtils.addDays(date, -amount);
    }

    public static Date addHours(final Date date, int amount) {
        return DateUtils.addHours(date, amount);
    }

    public static Date subtractHours(final Date date, int amount) {
        return DateUtils.addHours(date, -amount);
    }

    public static Date addMinutes(final Date date, int amount) {
        return DateUtils.addMinutes(date, amount);
    }

    public static Date subtractMinutes(final Date date, int amount) {
        return DateUtils.addMinutes(date, -amount);
    }

    public static Date addSeconds(final Date date, int amount) {
        return DateUtils.addSeconds(date, amount);
    }

    public static Date subtractSeconds(final Date date, int amount) {
        return DateUtils.addSeconds(date, -amount);
    }

    public static Date setYears(final Date date, int amount) {
        return DateUtils.setYears(date, amount);
    }

    public static Date setMonths(final Date date, int amount) {
        return DateUtils.setMonths(date, amount);
    }

    public static Date setDays(final Date date, int amount) {
        return DateUtils.setDays(date, amount);
    }

    public static Date setHours(final Date date, int amount) {
        return DateUtils.setHours(date, amount);
    }

    public static Date setMinutes(final Date date, int amount) {
        return DateUtils.setMinutes(date, amount);
    }

    public static Date setSeconds(final Date date, int amount) {
        return DateUtils.setSeconds(date, amount);
    }

    public static Date setMilliseconds(final Date date, int amount) {
        return DateUtils.setMilliseconds(date, amount);
    }

    public static int getDayOfWeek(final Date date) {
        return get(date, 7);
    }

    public static int getDayOfYear(final Date date) {
        return get(date, 6);
    }

    public static int getWeekOfMonth(final Date date) {
        return get(date, 4);
    }

    public static int getWeekOfYear(final Date date) {
        return get(date, 3);
    }

    private static int get(final Date date, int field) {
        Validate.notNull(date, "The date must not be null", new Object[0]);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(field);
    }

    public static boolean isLeapYear(final Date date) {
        return isLeapYear(get(date, 1));
    }

    public static boolean isLeapYear(int y) {
        boolean result = false;
        if (y % 4 == 0 && (y < 1582 || y % 100 != 0 || y % 400 == 0)) {
            result = true;
        }

        return result;
    }

    public static int getMonthLength(final Date date) {
        int year = get(date, 1);
        int month = get(date, 2);
        return getMonthLength(year, month);
    }

    public static int getMonthLength(int year, int month) {
        if (month >= 1 && month <= 12) {
            if (month == 2) {
                return isLeapYear(year) ? 29 : 28;
            } else {
                return MONTH_LENGTH[month];
            }
        } else {
            throw new IllegalArgumentException("Invalid month: " + month);
        }
    }
}
