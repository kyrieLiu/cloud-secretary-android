package com.luck.cloud.function.mine.work;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private int daysOfMonth = 0;
    private int dayOfWeek = 0;

    private static DateFormat dateTFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
    private static DateFormat pointFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

    private static DateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static DateFormat dayUnderlineFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat minuteStrikeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static DateFormat secondFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static DateFormat biasFormatMinute = new SimpleDateFormat("yyyy/MM/dd HH:mm");


    public boolean isLeapYear(int year) {
        if (year % 100 == 0 && year % 400 == 0) {
            return true;
        } else if (year % 100 != 0 && year % 4 == 0) {
            return true;
        }
        return false;
    }

    public int getDaysOfMonth(boolean isLeapyear, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                daysOfMonth = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                daysOfMonth = 30;
                break;
            case 2:
                if (isLeapyear) {
                    daysOfMonth = 29;
                } else {
                    daysOfMonth = 28;
                }

        }
        return daysOfMonth;
    }

    public int getWeekdayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return dayOfWeek;
    }

    /**
     * 将utc时间数据转化为String
     *
     * @param dateTime 2018-08-05T00:00:00.000+0000  这种格式的时间
     * @return 2018.08.13这种格式
     */
    public static String getStandardTime(String dateTime) {
        try {

            Date date = dateTFormat.parse(dateTime);
            return dayFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将2018-08-13 00:00:00格式时间数据转化为Date
     * @param dateTime
     * @return
     */
    public static Date getStandardDate(String dateTime) {
        try {

            Date date = secondFormat.parse(dateTime);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 将utc时间数据转化为String
     *
     * @param dateTime 2018-08-05T00:00:00.000+0000  这种格式的时间
     * @return 2018-08-13这种格式
     */
    public static String getUnderlineDay(String dateTime) {
        try {

            Date date = dateTFormat.parse(dateTime);
            return dayUnderlineFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将utc时间数据转化为String
     *
     * @param dateTime 2018-08-05T00:00:00.000+0000  这种格式的时间
     * @return 2018-08-13 00:00:00这种格式
     */
    public static String getlineSeondDay(String dateTime) {
        if (TextUtils.isEmpty(dateTime)) {
            return "";
        }
        try {
            Date selectDate = dateTFormat.parse(dateTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectDate);
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 8);
            Date turnDate = calendar.getTime();
            return secondFormat.format(turnDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将utc时间数据转化为String
     *
     * @param dateTime 2018-08-05T00:00:00  这种格式的时间
     * @return 2018/08/13 00:00这种格式
     */
    public static String getBiasMinuteDay(String dateTime) {
        if (TextUtils.isEmpty(dateTime)) {
            return "";
        }
        try {
            Date selectDate = dateTFormat.parse(dateTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectDate);
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 8);
            Date turnDate = calendar.getTime();
            return biasFormatMinute.format(turnDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将utc时间数据转化为String
     *
     * @param dateTime 2018-08-05T00:00:00.000+0000  这种格式的时间
     * @return 2018-08-13 00:00这种格式
     */
    public static String getMinuteTime(String dateTime) {
        if (TextUtils.isEmpty(dateTime)) {
            return "";
        }
        try {
            Date selectDate = dateTFormat.parse(dateTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectDate);
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 8);
            Date turnDate = calendar.getTime();
            return minuteStrikeFormat.format(turnDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 将时间转化为以天为单位的
     *
     * @param dateTime 2018-08-05 00:00:00.0  这种格式的数据
     * @return 2018.08.05这种数据格式
     */
    public static String getDay(String dateTime) {
        try {

            Date date = pointFormat.parse(dateTime);
            return dayFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将时间转化为以天为单位的
     *
     * @param dateTime 2018-08-05 00:00:00.0  这种格式的数据
     * @return 2018-08-05
     */
    public static String pointDayToLineDay(String dateTime) {
        if (TextUtils.isEmpty(dateTime)) return "";
        try {
            Date date = pointFormat.parse(dateTime);
            return dayUnderlineFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 2018-11-20 00:00:00转化为 2018-11-20
     *
     * @param dateTime
     * @return
     */
    public static String secondToLineDay(String dateTime) {
        if (TextUtils.isEmpty(dateTime)) {
            return "";
        }
        try {
            Date date = secondFormat.parse(dateTime);
            return dayUnderlineFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 2018-11-20 00:00:00转化为 2018-11-20 00:00
     *
     * @param dateTime
     * @return
     */
    public static String secondToLineMinute(String dateTime) {
        if (TextUtils.isEmpty(dateTime)) {
            return "";
        }
        try {
            Date date = secondFormat.parse(dateTime);
            return minuteStrikeFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * @param dateStr        日期时间  2018-09-11 12:12:34
     * @param datePattern    对应的yyyy-MM-dd HH:mm:ss
     * @param newDatePattern 需要转换成的  yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String translateDateStr(String dateStr, String datePattern, String newDatePattern) {
        String s = null;
        try {
            long dateTimeMillis = getDateTimeMillis(dateStr, datePattern);

            s = formatDate(dateTimeMillis, newDatePattern);
        } catch (Exception e) {
            s = null;
        }

        return s;
    }

    /**
     * 毫秒时间转换成一定格式的日期,14002020304--->2016-12-12 14:12:43
     *
     * @param timeMillis  时间戳毫秒值
     * @param datePattern yyyy-MM-dd yyyy-MM-dd HH:mm:ss
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDate(long timeMillis, String datePattern) {
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(datePattern);

        String dateString = simpleDateFormat.format(new Date(timeMillis));
        return dateString;
    }

    /**
     * 获取一个日期的毫秒值
     *
     * @param dateTime    日期时间  2018-09-11 12:12:34
     * @param datePattern yyyy-MM-dd HH:mm:ss
     * @return 日期的毫秒值
     */
    public static long getDateTimeMillis(String dateTime, String datePattern) {
        if (TextUtils.isEmpty(dateTime) || TextUtils.isEmpty(datePattern))
            return 0;
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(datePattern);
        try {
            return simpleDateFormat.parse(dateTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取 SimpleDateFormat
     *
     * @param datePattern
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat getSimpleDateFormat(String datePattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        return simpleDateFormat;
    }
}
