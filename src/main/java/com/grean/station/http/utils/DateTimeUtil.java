//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.http.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtil {
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final LocalDateTime BEGIN_TIME = LocalDateTime.of(2014, 1, 1, 0, 0, 0);
    private static final int[] TABLE = new int[]{1, 1, 1, 4, 4, 4, 7, 7, 7, 10, 10, 10};
    public static final String defaultFormatStringWithMesc = "yyyyMMddHHmmssSSS";
    public static final DateTimeFormatter defalutFormatWithMesc = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    public DateTimeUtil() {
    }

    public static long timeStrToTick(String timeStr) {
        LocalDateTime dateTime = LocalDateTime.parse(timeStr, DATE_TIME_FORMATTER);
        Duration dur = Duration.between(BEGIN_TIME, dateTime);
        return dur.toMinutes();
    }

    public static LocalDateTime tickToDateTime(long timetick) {
        return BEGIN_TIME.plusMinutes(timetick);
    }

    public static String tickToTimeStr(long timetick) {
        return tickToDateTime(timetick).format(DATE_TIME_FORMATTER);
    }

    public static LocalDateTime format(String timeStr) {
        return LocalDateTime.parse(timeStr, DATE_TIME_FORMATTER);
    }

    public static LocalDateTime longToLocalDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static long localDateTimeToLong(LocalDateTime ldt) {
        return ldt.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public static long timeToTick(LocalDateTime time) {
        Duration dur = Duration.between(BEGIN_TIME, time);
        return dur.toMinutes();
    }

    public static long nowTick() {
        Duration dur = Duration.between(BEGIN_TIME, LocalDateTime.now());
        return dur.toMinutes();
    }

    public static String dateTimeToString(LocalDateTime time) {
        return DATE_TIME_FORMATTER.format(time);
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalTime dateToLocalTime(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
    }

    public static Date localDateTimeToDate(LocalDateTime date) {
        Instant instant = date.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static Date localDateToDate(LocalDate date) {
        Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static Date localTimeToDate(LocalTime date, int year, Month month, int dayOfMonth) {
        Instant instant = date.atDate(LocalDate.of(year, month, dayOfMonth)).atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static LocalDate localDateFromString(String dateString, String pattern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(dateString, dtf);
    }

    public static LocalTime localTimeFromString(String timeString, String pattern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return LocalTime.parse(timeString, dtf);
    }

    public static LocalDateTime localDateTimeFromString(String dateTimeString, String pattern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTimeString, dtf);
    }

    public static Date dateTimeFromString(String dateTimeString, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date dateTime = sdf.parse(dateTimeString);
            return dateTime;
        } catch (ParseException var5) {
            throw new IllegalArgumentException(var5);
        }
    }

    public static Date firstDateOfMonth(Date today) {
        return firstDateOfMonth(dateToLocalDate(today));
    }

    public static LocalDate firstLocalDateOfMonth(LocalDate today) {
        return LocalDate.of(today.getYear(), today.getMonth(), 1);
    }

    public static Date firstDateOfMonth(LocalDate today) {
        return localDateToDate(firstLocalDateOfMonth(today));
    }

    public static Date firstDateOfYear(Date today) {
        return firstDateOfYear(dateToLocalDate(today));
    }

    public static LocalDate firstLocalDateOfYear(LocalDate today) {
        return LocalDate.of(today.getYear(), Month.JANUARY, 1);
    }

    public static Date firstDateOfYear(LocalDate today) {
        return localDateToDate(firstLocalDateOfYear(today));
    }

    public static Date firstDateOfQuarter(Date today) {
        return firstDateOfQuarter(dateToLocalDate(today));
    }

    public static LocalDate firstLocalDateOfQuarter(LocalDate today) {
        return LocalDate.of(today.getYear(), TABLE[today.getMonthValue() - 1], 1);
    }

    public static Date firstDateOfQuarter(LocalDate today) {
        return localDateToDate(firstLocalDateOfQuarter(today));
    }

    public static String tickToTimeStrWithMesc(long timetick) {
        return tickToDateTime(timetick).format(defalutFormatWithMesc);
    }

    public static String localTimeToStrWithMesc(LocalDateTime dateTime) {
        return dateTime.format(defalutFormatWithMesc);
    }
}
