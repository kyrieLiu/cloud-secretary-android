package com.luck.cloud.utils;

import android.text.TextUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by liuyin on 2019/5/24 18:58
 * Describe: 时间处理工具
 */
public class TimeUtils {

    /**
     * 将utc时间数据转化为String
     *
     * @param dateTime 2018-08-05T00:00:00.000+0000  这种格式的时间
     * @return 2018-08-13 00:00:00这种格式
     */
    public static String getLineSeondTime(String dateTime) {
        if (TextUtils.isEmpty(dateTime)) {
            return "";
        }
        try {
            DateFormat dateTFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+SSSS");
            Date selectDate = dateTFormat.parse(dateTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectDate);
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 8);
            Date turnDate = calendar.getTime();
            DateFormat secondFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return secondFormat.format(turnDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将utc时间数据转化为String
     *
     * @param dateTime 2018-08-05T00:00:00.000+0000  这种格式的时间
     * @return 2018-08-13这种格式
     */
    public static String getLineDayTime(String dateTime) {
        if (TextUtils.isEmpty(dateTime)) {
            return "";
        }
        try {
            DateFormat dateTFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+SSSS");
            Date selectDate = dateTFormat.parse(dateTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectDate);
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 8);
            Date turnDate = calendar.getTime();
            DateFormat secondFormat = new SimpleDateFormat("yyyy-MM-dd");
            return secondFormat.format(turnDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String timeFormat(long timeMillis, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        return format.format(new Date(timeMillis));
    }

    public static String formatPhotoDate(long time) {
        return timeFormat(time, "yyyy-MM-dd");
    }

    public static String formatPhotoDate(String path) {
        File file = new File(path);
        if (file.exists()) {
            long time = file.lastModified();
            return formatPhotoDate(time);
        }
        return "1970-01-01";
    }

    /**
     * 将时间戳转化为时间类型
     *
     * @param dateFormat 要转化的格式
     * @param time
     * @return
     */
    public static String longToDate(SimpleDateFormat dateFormat, long time) {
        Date date = new Date(time);
        String formatTime = dateFormat.format(date);
        return formatTime;
    }

    /**
     * 转换时间格式
     * @param targetFormat  目标格式
     * @param preFormat  原始格式
     * @param time 时间
     * @return
     */
    public static String changeTimeFormat(SimpleDateFormat targetFormat, SimpleDateFormat preFormat, String time) {
        if (TextUtils.isEmpty(time)) return "";
        try {
            Date date = preFormat.parse(time);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
