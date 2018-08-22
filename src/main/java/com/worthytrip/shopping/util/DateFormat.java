package com.worthytrip.shopping.util;

import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateFormat {
    /**
     * 获取当前时间：yyyyMMddHHmmss
     *
     * @return
     */
    public static String getCurTimeLong() {
        Date date = new Date();
        SimpleDateFormat df0 = new SimpleDateFormat("yyyyMMddHHmmss");
        return df0.format(date);
    }

    /**
     * 获取当前时间：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurTimeString() {
        Date date = new Date();
        SimpleDateFormat df0 = new SimpleDateFormat("yyyy-MM-dd");
        return df0.format(date);
    }

    /**
     * 日期格式化
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static String formatFromString(String dateStr) throws ParseException {
        int strLength = dateStr.length();
        SimpleDateFormat df0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

        if (strLength == 19) {
            df0.parse(dateStr);
        } else if (strLength == 16) {
            Date date1 = df1.parse(dateStr);
            dateStr = df0.format(date1);
        } else if (strLength == 10) {
            Date date2 = df2.parse(dateStr);
            dateStr = df0.format(date2);
        }

        return dateStr;
    }

    /**
     * 推迟或提前当前时间：年（1表示延后1年，-1表示提前1年）
     *
     * @param year
     * @return
     */
    public static String addYear(int year) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        // calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.add(Calendar.YEAR, year);
        date = calendar.getTime();
        // System.out.println(date);

        SimpleDateFormat df0 = new SimpleDateFormat("yyyy-MM-dd");
        return df0.format(date);
    }

    /**
     * 续租一年（1表示延后1年，-1表示提前1年）
     *
     * @param year
     * @return
     */
    public static String reletYear(String nowDay, int addyear) {
        SimpleDateFormat df0 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = df0.parse(nowDay);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        // calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.add(Calendar.YEAR, addyear);
        date = calendar.getTime();
        // System.out.println(date);
        return df0.format(date);
    }

    /**
     * 时间字符串转时间戳
     *
     * @author lishuai
     * @date 18-3-1 上午10:19
     */
    public static long str2Long(String yyyyMMDDHHMMSS) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.YYYYMMDDHHMMSS);
        Date date = null;
        try {
            date = sdf.parse(yyyyMMDDHHMMSS);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }


    public static String addDay(int day) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        date = calendar.getTime();
        SimpleDateFormat df0 = new SimpleDateFormat("yyyy-MM-dd");
        return df0.format(date);
    }

    public static String addDay(String time, int day) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date src = null;
        try {
            src = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(src);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat df0 = new SimpleDateFormat("yyyy-MM-dd");
        return df0.format(src);
    }

    /**
     * 获取时间距今天有几天
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static Integer moreThanToday(String time) {

        if (StringUtil.isNullOrBlank(time)) {
            return 0;
        }
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parameter = null;
        Integer fromNow = 0;
        try {
            parameter = sdf.parse(time);
            Date now = sdf.parse(sdf.format(date));
            //将时间转为毫秒
            long later = parameter.getTime();
            long today = now.getTime();
            fromNow = (int) ((later - today) / 1000 / 60 / 60 / 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fromNow;
    }

    public static int subDaysFromTimeString(String day1, String day2) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = format.parse(day1);
        Date date2 = format.parse(day2);
        return (int) ((date2.getTime() - date1.getTime()) / 1000 / 3600 / 24);
    }


}
