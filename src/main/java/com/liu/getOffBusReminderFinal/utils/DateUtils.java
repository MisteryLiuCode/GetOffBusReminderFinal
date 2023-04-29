package com.liu.getOffBusReminderFinal.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.FastDateFormat;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * @author liushuaibiao
 * @date 2023/4/27 18:11
 */
public class DateUtils extends DateMoreUtils {

    public static final String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_SHORT_DATETIME = "yyyyMMddHHmmss";
    public static final String DATE_SHORT_DATETIMES = "yyyyMMddHHmmssSSS";
    public static final String DATE_MM_DATETIME = "MMddHHmmssSSS";
    public static final String DATE_SHORT_MONTH_ONLY = "yyyy-MM";
    public static final String DATE_SHORT_MONTH = "yyyyMM";
    public static final String DATE_FORMAT_DATE_ONLY = "yyyy-MM-dd";
    public static final String DATE_FORMAT_DATE_ONLY2 = "yyyy/MM/dd";
    public static final String DATE_SHORT_DATE_ONLY = "yyyyMMdd";
    public static final String DATE_SHORT_DATE_ONLY2 = "yyMMdd";
    public static final String DATE_SHORT_TIME_ONLY = "HHmmss";
    public static final String DATE_MILLISECOND_ONLY = "HHmmssSSS";
    public static final String DATE_SHORT_WEEK_DAY = "EEEE";
    public static final String DATE_HOUR_ONLY = "HH";
    public static final FastDateFormat FDT_DATE_TIME = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    public static final FastDateFormat FDF_SHORT_DATE_TIME = FastDateFormat.getInstance("yyyyMMddHHmmss");
    public static final FastDateFormat FDF_SHORT_DATE_TIMES = FastDateFormat.getInstance("yyyyMMddHHmmssSSS");
    public static final FastDateFormat FDF_DATE_ONLY = FastDateFormat.getInstance("yyyy-MM-dd");
    public static final FastDateFormat FDF_DATE_ONLY2 = FastDateFormat.getInstance("yyyy/MM/dd");
    public static final FastDateFormat FDF_SHORT_DATE_ONLY = FastDateFormat.getInstance("yyyyMMdd");
    public static final FastDateFormat FDF_SHORT_TIME_ONLY = FastDateFormat.getInstance("HHmmss");
    public static final FastDateFormat FDF_MILLISECOND_ONLY = FastDateFormat.getInstance("HHmmssSSS");
    public static final FastDateFormat FDF_SHORT_MONTH_ONLY = FastDateFormat.getInstance("yyyy-MM");
    public static final FastDateFormat FDF_SHORT_WEEK_DAY = FastDateFormat.getInstance("EEEE");
    public static final FastDateFormat FDF_HOUR_ONLY = FastDateFormat.getInstance("HH");

    public DateUtils() {
    }

    public static long nowTimeMillis() {
        return System.currentTimeMillis();
    }


    public static String getCurrTime() {
        return FDT_DATE_TIME.format(new Date());
    }

    public static String getCurrShotTime() {
        return FDF_SHORT_DATE_TIME.format(new Date());
    }

    public static String getCurrDate() {
        return FDF_DATE_ONLY.format(new Date());
    }

    public static String getCurrShortDate() {
        return FDF_SHORT_DATE_ONLY.format(new Date());
    }

    public static String getCurrTimeOnly() {
        return FDF_SHORT_TIME_ONLY.format(new Date());
    }

    public static String getCurrDateTime() {
        return FDF_SHORT_DATE_TIME.format(new Date());
    }

    public static String format(Date date, FastDateFormat fdf) {
        validateDateNotNull(date);
        return fdf.format(date);
    }

    public static String format(Date date, String pattern) {
        FastDateFormat fdf = FastDateFormat.getInstance(pattern);
        return format(date, fdf);
    }

    public static Date parse(String date, FastDateFormat fdf) throws ParseException {
        return fdf.parse(date);
    }

    public static Date parse(String date, String pattern) throws ParseException {
        FastDateFormat fdf = FastDateFormat.getInstance(pattern);
        return parse(date, fdf);
    }

    public static Date dateParse(String date, String pattern) {
        FastDateFormat fdf = FastDateFormat.getInstance(pattern);
        return fdf.parse(date, new ParsePosition(0));
    }

    public static int getSeason(Date date) {
        validateDateNotNull(date);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(2);
        return month / 3 + 1;
    }

    public static long getDayDiff(Date date1, Date date2) {
        validateDateNotNull(date1);
        validateDateNotNull(date2);
        return (date2.getTime() - date1.getTime()) / 86400000L;
    }

    public static long getDayDiff(String date1Str, String date2Str) throws ParseException {
        Date date1 = parse(date1Str, "yyyy-MM-dd");
        Date date2 = parse(date2Str, "yyyy-MM-dd");
        validateDateNotNull(date1);
        validateDateNotNull(date2);
        return (date2.getTime() - date1.getTime()) / 86400000L;
    }

    public static int compareDate(Date date1, Date date2, int field) {
        validateDateNotNull(date1);
        validateDateNotNull(date2);
        date1 = truncate(date1, field);
        date2 = truncate(date2, field);
        return date1.compareTo(date2);
    }

    private static void validateDateNotNull(final Date date) {
        Validate.isTrue(date != null, "The date must not be null", new Object[0]);
    }

    public static String lastMonthDayth(int day) {
        Calendar cal = Calendar.getInstance();
        cal.add(2, -1);
        cal.set(5, day);
        return format(cal.getTime(), "yyyy-MM-dd");
    }

    public static String lastMonthDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(5, 0);
        return format(cal.getTime(), "yyyy-MM-dd");
    }

    public static String currentMonthFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(5, 1);
        return format(cal.getTime(), "yyyy-MM-dd");
    }

    public static String currentMonthLastDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(5, cal.getActualMaximum(5));
        return format(cal.getTime(), "yyyy-MM-dd");
    }

    public static LocalDate dateLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static String compareDays(Date start, Date end) {
        if (null != start && null != end) {
            LocalDateTime startDate = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime endDate = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            long until = startDate.until(endDate, ChronoUnit.HOURS);
            return until < 24L ? BigDecimal.ONE.toPlainString() : String.valueOf(until / 24L);
        } else {
            throw new RuntimeException("参数异常");
        }
    }

    public static String getDatePoor(String endDateStr, String nowDateStr) {
        long nd = 86400000L;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        long diff = 0L;

        try {
            diff = format.parse(endDateStr).getTime() - format.parse(nowDateStr).getTime();
        } catch (ParseException var9) {
            var9.printStackTrace();
        }

        long day = diff / nd;
        return String.valueOf(day);
    }

    public static long compareMinute(Date start, Date end) {
        if (null != start && null != end) {
            LocalDateTime startDate = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime endDate = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return startDate.until(endDate, ChronoUnit.MINUTES);
        } else {
            throw new RuntimeException("参数异常");
        }
    }


    public static String beforeDay(int day) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now().minusDays((long) day).withHour(0).withMinute(0).withSecond(0));
    }

    public static String formatMedianLine(String timeStr) {
        return StringUtils.isNotBlank(timeStr) && timeStr.contains("-") ? timeStr.replace("-", "") : timeStr;
    }
}
