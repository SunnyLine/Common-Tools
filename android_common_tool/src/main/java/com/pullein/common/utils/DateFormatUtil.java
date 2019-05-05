package com.pullein.common.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Common-Tools<br>
 * describe ：时间转换工具类
 *
 * @author xugang
 * @date 2019/5/5
 */
public class DateFormatUtil {
    public static final String PATTERN_YMD_HM = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_YMDHMS = "yyyyMMddHHmmss";
    public static final String PATTERN_YMD = "yyyy-MM-dd";
    public static final String PATTERN_MD = "MM-dd";
    public static final String PATTERN_HMS = "HH:mm:ss";
    public static final String PATTERN_HM = "HH:mm";
    public static final String PATTERN_MD_HMS = "MM-dd HH:mm:ss";
    public static final String PATTERN_MD_HM = "MM-dd HH:mm";

    public static final long ONE_DAY = 24 * 60 * 60 * 1000;
    public static final long ONE_HOUR = 60 * 60 * 1000;

    /**
     * 东八区
     */
    public static final String GMT_8 = "GTM+08:00";

    public static String format2GMT(long timeStamp, String formatStr) {
        return format2GMT(timeStamp, formatStr, null);
    }

    public static String format2GMT(Date date, String formatStr) {
        return format2GMT(date, formatStr, null);
    }

    @SuppressLint("SimpleDateFormat")
    public static String format2GMT(Date date, String formatStr, String timeZone) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        if (!TextUtils.isEmpty(timeZone)) {
            format.setTimeZone(TimeZone.getTimeZone(timeZone));
        }
        return format.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String format2GMT(long timeStamp, String formatStr, String timeZone) {
        return format2GMT(new Date(timeStamp), formatStr, timeZone);
    }

    public static long format2UTC(String date, String formatStr) {
        return format2UTC(date, formatStr, null);
    }

    @SuppressLint("SimpleDateFormat")
    public static long format2UTC(String date, String formatStr, String timeZone) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        if (!TextUtils.isEmpty(timeZone)) {
            format.setTimeZone(TimeZone.getTimeZone(timeZone));
        }
        try {
            return format.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static String getCurTimeZone() {
        return TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT);
    }
}
