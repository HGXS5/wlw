//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.utils;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeHelper {
    public TimeHelper() {
    }

    public static Timestamp getCurrentTimestamp() {
        return getTimestamp(new DateTime());
    }

    public static Timestamp getFormatSecondTimestamp() {
        Timestamp curTime = getCurrentTimestamp();
        DateTime dateTime = new DateTime(curTime);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");
        String strTime = formatter.print(dateTime);
        return getTimestamp(formatter.parseDateTime(strTime));
    }

    public static Timestamp getFormatMinuteTimestamp() {
        Timestamp curTime = getCurrentTimestamp();
        DateTime dateTime = new DateTime(curTime);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMddHHmm00");
        String strTime = formatter.print(dateTime);
        return getTimestamp(formatter.parseDateTime(strTime));
    }

    public static Timestamp getFormatDayTimestamp() {
        Timestamp curTime = getCurrentTimestamp();
        DateTime dateTime = new DateTime(curTime);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd000000");
        String strTime = formatter.print(dateTime);
        return getTimestamp(formatter.parseDateTime(strTime));
    }

    public static String getCurrentTimeString() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");
        return formatter.print(DateTime.now());
    }

    public static Timestamp getDataTime_yyyyMMddHHmmss(String datatime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");
        return getTimestamp(formatter.parseDateTime(datatime));
    }

    public static Timestamp getDataTime_yyyy_MM_ddHHmmss(String datatime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss");
        return getTimestamp(formatter.parseDateTime(datatime));
    }

    public static String formatDataTime_yyyyMMddHHmmss(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        } else {
            DateTime dateTime = new DateTime(timestamp);
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");
            return formatter.print(dateTime);
        }
    }

    public static String formatDataTime_yyyyMMdd(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        } else {
            DateTime dateTime = new DateTime(timestamp);
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd");
            return formatter.print(dateTime);
        }
    }

    public static String formatDataTime_yyyy_MM_dd_HH_mm_ss(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        } else {
            DateTime dateTime = new DateTime(timestamp);
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            return formatter.print(dateTime);
        }
    }

    public static String formatDataTime_HH_mm_ss(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        } else {
            DateTime dateTime = new DateTime(timestamp);
            DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm:ss");
            return formatter.print(dateTime);
        }
    }

    public static Timestamp parseDevTime(String devtime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return getTimestamp(formatter.parseDateTime(devtime));
    }

    public static Timestamp parseTimeHHmm(String devtime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
        return getTimestamp(formatter.parseDateTime(devtime));
    }

    public static Timestamp parseTimeHHmmss(String devtime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm:ss");
        return getTimestamp(formatter.parseDateTime(devtime));
    }

    public static Timestamp parseSpecTime(String datatime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy.MM.dd HH:mm:ss");
        return getTimestamp(formatter.parseDateTime(datatime).withSecondOfMinute(0));
    }

    public static Timestamp formatSLMessageDate(String datatime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyMMddHHmm");
        return getTimestamp(formatter.parseDateTime(datatime));
    }

    public static Timestamp formatSWRealTime(String datatime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("ssmmHHdd");
        DateTime dt = formatter.parseDateTime(datatime);
        dt = dt.withYear((new DateTime()).getYear());
        dt = dt.withMonthOfYear((new DateTime()).getMonthOfYear());
        return getTimestamp(dt);
    }

    public static Timestamp formatSWHourly(String datatime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("mmHHddMMyy");
        return getTimestamp(formatter.parseDateTime(datatime));
    }

    public static Timestamp getQueryDate(String datatime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd");
        return getTimestamp(formatter.parseDateTime(datatime));
    }

    public static Timestamp getTimestamp(DateTime dt) {
        return new Timestamp(dt.getMillis());
    }

    public static String getQN() {
        DateTime dateTime = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMddHHmmssSSS");
        return formatter.print(dateTime);
    }

    public static String getSystemTimeGJ() {
        DateTime dateTime = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");
        return formatter.print(dateTime);
    }

    public static String getDT_Str(Timestamp datatime) {
        DateTime dateTime = new DateTime(datatime);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        return formatter.print(dateTime);
    }

    public static String getyyMMddHHmmss_Str(Timestamp datatime) {
        DateTime dateTime = new DateTime(datatime);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyMMddHHmmss");
        return formatter.print(dateTime);
    }

    public static String getyyMMddHHmmss_Str(DateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyMMddHHmmss");
        return formatter.print(dateTime);
    }

    public static String getSms_Str(Timestamp datatime) {
        DateTime dateTime = new DateTime(datatime);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd HH:mm");
        return formatter.print(dateTime);
    }

    public static String getDT_Str(Date date) {
        DateTime dateTime = new DateTime(date);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        return formatter.print(dateTime);
    }

    public static String getSQLDay(Timestamp datatime) {
        DateTime dateTime = new DateTime(datatime);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        return formatter.print(dateTime);
    }

    public static String getSQLTime(Timestamp datatime) {
        DateTime dateTime = new DateTime(datatime);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm:ss");
        return formatter.print(dateTime);
    }

    public static String getSQLMonth(Timestamp datatime) {
        DateTime dateTime = new DateTime(datatime);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM");
        return formatter.print(dateTime);
    }

    public static String getOperationPostTime_Str(Timestamp datatime) {
        DateTime dateTime = new DateTime(datatime);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy年MM月dd日 HH:mm");
        return formatter.print(dateTime);
    }

    public static DateTime getSQLDayTime(Timestamp datatime) {
        String strTime = getSQLDay(datatime);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        return formatter.parseDateTime(strTime);
    }

    public static String formatGJDatatTime(String datatime) {
        DateTime dateTime = new DateTime(getDataTime_yyyyMMddHHmmss(datatime));
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy年MM月dd日 HH:mm:ss");
        return formatter.print(dateTime);
    }

    public static String getSQLMonth(String sql_day) {
        return sql_day.substring(0, 7);
    }

    public static Timestamp getDaysAgo(Timestamp timestamp, int days) {
        DateTime datetime = (new DateTime(timestamp)).minusDays(days).withTime(0, 0, 0, 0);
        return jodaToTimestamp(datetime);
    }

    public static Timestamp getDaysLater(Timestamp timestamp, int days) {
        DateTime datetime = (new DateTime(timestamp)).plusDays(days).withTime(23, 59, 59, 999);
        return jodaToTimestamp(datetime);
    }

    public static Timestamp getMinutesAgo(Timestamp timestamp, int minutes) {
        DateTime datetime = (new DateTime(timestamp)).minusMinutes(minutes);
        return jodaToTimestamp(datetime);
    }

    public static Timestamp getMinutesLater(Timestamp timestamp, int minutes) {
        DateTime datetime = (new DateTime(timestamp)).plusMinutes(minutes).minusMillis(1);
        return jodaToTimestamp(datetime);
    }

    public static Timestamp jodaToTimestamp(DateTime dt) {
        return new Timestamp(dt.getMillis());
    }

    public static Timestamp getSameDaysAgo(Timestamp startDate, Timestamp endDate) {
        Integer days = Days.daysBetween(new LocalDate(startDate), new LocalDate(endDate)).getDays();
        return getDaysAgo(startDate, days);
    }

    public static Integer getDaysBetween(Timestamp startDate, Timestamp endDate) {
        return Days.daysBetween(new LocalDate(startDate), new LocalDate(endDate)).getDays() + 1;
    }

    public static Timestamp getStartDate(String startDay) {
        return getDaysAgo(getQueryDate(startDay), 0);
    }

    public static Timestamp getEndDate(String endDay) {
        return getDaysLater(getQueryDate(endDay), 0);
    }

    public static Integer getSecondOfDate(Timestamp datatime) {
        Integer seconds = (new DateTime(datatime)).secondOfMinute().get();
        return seconds == 0 ? 60 : seconds;
    }

    public static boolean setDatetime(String date, String time) {
        String osName = System.getProperty("os.name");

        try {
            String command;
            if (osName.matches("^(?i)Windows.*$")) {
                command = " cmd /c date " + date;
                Runtime.getRuntime().exec(command);
                command = " cmd /c time " + time;
                Runtime.getRuntime().exec(command);
            } else {
                if (!osName.matches("^(?i)Linux.*$")) {
                    return false;
                }

                command = "date -s \"" + date + " " + time + "\"";
                Runtime.getRuntime().exec(command);
            }

            return true;
        } catch (IOException var4) {
            return false;
        }
    }

    public static boolean compareHourMinute(int hour, int minute, java.util.Date startDate, java.util.Date endDate) {
        DateTime startTime = new DateTime(startDate.getTime());
        DateTime endTime = new DateTime(endDate.getTime());
        String strStart = "2019-01-01 " + startTime.getHourOfDay() + ":" + startTime.getMinuteOfHour() + ":00";
        String strEnd = "2019-01-01 " + endTime.getHourOfDay() + ":" + endTime.getMinuteOfHour() + ":00";
        String strDate = "2019-01-01 " + hour + ":" + minute + ":00";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            java.util.Date date = formatter.parse(strDate);
            java.util.Date dateStart = formatter.parse(strStart);
            java.util.Date dateEnd = formatter.parse(strEnd);
            return date.getTime() >= dateStart.getTime() && date.getTime() <= dateEnd.getTime();
        } catch (Exception var13) {
            return false;
        }
    }
}
