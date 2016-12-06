package com.mo.study.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by motw on 2016/9/18.
 */
public class DateFormatUtil {

    public static final String defaultF = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd
     * @param date 毫秒
     * @param format 格式
     * @return
     */
    public static String convertToString(long date, String format) {
        SimpleDateFormat sdf;
        if (format == null) {
            sdf = new SimpleDateFormat(defaultF, Locale.CHINA);
        } else {
            sdf = new SimpleDateFormat(format, Locale.CHINA);
        }

        return sdf.format(new Date(date));
    }

    public static String convertToString(long date){
        return convertToString(date, null);
    }


    public static String parseUTCDate(long date, String format){
        SimpleDateFormat dateFormat;
        if (format == null) {
            dateFormat = new SimpleDateFormat(defaultF, Locale.CHINA);
        }else{
            dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        }

        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        return dateFormat.format(date);
    }

    public static String parseUTCDate(long date){
        return parseUTCDate(date, null);
    }

    /**
     * 转成UTC时间
     * @param src
     * @return
     */
//    public static long toUTC(long src){
//        return src - 8 * 60 * 60L;
//    }

    /**
     * 获取今天凌晨的时间
     */
    public static long getTodayZeroTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getYesterdayZeroTime(){
        return getTodayZeroTime() - 24 * 60 * 60 * 1000L;
    }
}
