package com.welink.myapp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by dell on 2016/7/11.
 */
public class TimeUtils {
    private static GregorianCalendar calendar = new GregorianCalendar();
    /**
     * 获取当前时间 yyyy年MM月dd日 HH:mm
     *
     * @return
     */
    public static String getData() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        return str;
    }



    /**
     * "2014-12-22 21:31:32"
     * @return
     */
    public static String getData_yyyy_MM_dd_hh_mm_ss() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * "yyyy-MM-dd HH:mm:ss"
     * @param date1String
     * @param date2String
     * @return
     * date1String == date2String return 0;
     * date1String < date2String return <0;
     * date1String > date2String return >0;
     */
    public static boolean compareDate(String date1String, String date2String){
        if (date1String == null || date2String == null) {
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = dateFormat.parse(date1String);
            date2 = dateFormat.parse(date2String);
        } catch (ParseException e) {
            e.printStackTrace();

        }
        boolean result = date1.before(date2);
        return result;
    }

    /**
     * 获取当前时间 yyyy年MM月dd日
     *
     * @return
     */
    public static String getData_yyyy_MM_dd() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取当前时间 yyyy年MM月dd日 arg 0今天1明天2后天
     *
     * @return
     */
    public static String getThreeData_yyyy_MM_dd(int arg) {

        Date date = new Date(0);// 取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, arg);// 把日期往后增加一天.整数往后推,负数往前移动
        date = (Date) calendar.getTime(); // 这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 获取当前时间 HH
     *
     * @return
     */
    public static int getData_HH() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        int str = Integer.parseInt(formatter.format(curDate).toString());
        return str;
    }

    /**
     * 获取当前时间 mm
     *
     * @return
     */
    public static String getData_mm() {
        SimpleDateFormat formatter = new SimpleDateFormat("mm");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        int str = Integer.parseInt(formatter.format(curDate).toString());
        if (str<10){
            return "0"+str;
        }
        return Integer.toString(str);
    }

    /**
     * 比较时间
     *
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static long compare_date(String DATE1, String DATE2) {
        long days = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date dt1 = df.parse(DATE1);
            java.util.Date dt2 = df.parse(DATE2);
            long diff = dt2.getTime() - dt1.getTime();
            days = diff / (1000 * 60 * 60 * 24);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return days;
    }

    /**
     * 提供“yyyy-mm-dd”形式的字符串到毫秒的转换
     *
     * @param dateString
     * @return
     */

    public static long getMillis(String dateString) {

        String[] date = dateString.split("-");

        return getMillis(date[0], date[1], date[2]);

    }

    /**
     * 根据输入的年、月、日，转换成毫秒表示的时间
     *
     * @param year
     * @param month
     * @param day
     * @return
     */

    public static long getMillis(int year, int month, int day) {

        GregorianCalendar calendar = new GregorianCalendar(year, month, day);

        return calendar.getTimeInMillis();

    }

    /**
     * 根据输入的年、月、日，转换成毫秒表示的时间
     *
     * @param yearString
     * @param monthString
     * @param dayString
     * @return
     */

    public static long getMillis(String yearString, String monthString,

                                 String dayString) {

        int year = Integer.parseInt(yearString);

        int month = Integer.parseInt(monthString);

        int day = Integer.parseInt(dayString);

        return getMillis(year, month, day);

    }

    /**
     * 获得当前时间的毫秒表示
     *
     * @return
     */

    public static long getNow() {

        GregorianCalendar now = new GregorianCalendar();

        return now.getTimeInMillis();

    }

    /**
     * 根据输入的毫秒数，获得日期字符串
     *
     * @param millis
     * @return
     */

    public static String getDate(long millis) {

        calendar.setTimeInMillis(millis);

        return DateFormat.getDateInstance().format(calendar.getTime());

    }

    /**
     * 根据输入的毫秒数，获得年份
     *
     * @param millis
     * @return
     */

    public static int getYear(long millis) {

        calendar.setTimeInMillis(millis);

        return calendar.get(Calendar.YEAR);

    }

    /**
     * 根据输入的毫秒数，获得月份
     *
     * @param millis
     * @return
     */

    public static int getMonth(long millis) {

        calendar.setTimeInMillis(millis);

        return calendar.get(Calendar.MONTH);

    }

    /**
     * 根据输入的毫秒数，获得日期
     *
     * @param millis
     * @return
     */

    public static int getDay(long millis) {

        calendar.setTimeInMillis(millis);

        return calendar.get(Calendar.DATE);

    }

    /**
     * 根据输入的毫秒数，获得小时
     *
     * @param millis
     * @return
     */

    public static int getHour(long millis) {

        calendar.setTimeInMillis(millis);

        return calendar.get(Calendar.HOUR_OF_DAY);

    }

    /**
     * 根据输入的毫秒数，获得分钟
     *
     * @param millis
     * @return
     */

    public static int getMinute(long millis) {

        calendar.setTimeInMillis(millis);

        return calendar.get(Calendar.MINUTE);

    }

    /**
     * 根据输入的毫秒数，获得秒
     *
     * @param millis
     * @return
     */

    public static int getSecond(long millis) {

        calendar.setTimeInMillis(millis);

        return calendar.get(Calendar.SECOND);

    }

    /**
     * 获得今天日期
     *
     * @return
     */

    public static String getTodayData() {

        return getDate(getNow());

    }

    /**
     * 获得明天日期
     *
     * @return
     */

    public static String getTomoData() {

        // 86400000为一天的毫秒数

        return getDate(getNow() + 86400000);

    }

    /**
     * 获得后天日期
     *
     * @return
     */

    public static String getTheDayData() {

        return getDate(getNow() + 86400000 + 86400000);

    }





    /**
     * 当前日期为星期几
     *
     * @param pTime
     * @return
     * @throws Exception
     */
    public static int dayForWeek(String pTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    public static String getYYY_MM_DD(String data) {
        String date = null;
        String months = null;
        String days = null;
        String[] s = data.split("-");
        int year = Integer.parseInt(s[0]);
        int month = Integer.parseInt(s[1]);
        int day = Integer.parseInt(s[2]);

        if (month < 10) {
            months = "0" + month;
        } else {
            months = String.valueOf(month);
        }
        if (day < 10) {
            days = "0" + day;
        } else {
            days = String.valueOf(day);
        }
        date = year + "-" + months + "-" + days;

        return date;
    }

    /**
     * 根据年 月 获取对应的月份 天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
}
