package com.book.manager.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.catalina.ssi.SSIInclude;

public class DateUtil {

    private static String defaultformat = "yyyy-MM-dd HH:mm:ss";

    /**
     * @Author dugh
     * @Description 日期格式化
     * @Date 11:49 2020/5/29
     * @Param [targetDate, format]
     * @return java.lang.String
     *
     **/
    public static String formatDate(Date targetDate,String format){
    	if(targetDate==null) {
    		return "";
    	}
        SimpleDateFormat sdf = null;
        if(format==null || format.equals("")){
            sdf = new SimpleDateFormat(defaultformat);
        }else {
            sdf = new SimpleDateFormat(format);//设置日期格式
        }

        String formatDate = sdf.format(targetDate);

        return formatDate;
    }


    /**
     * @Author dugh
     * @Description 字符串转日期
     * @Date 11:52 2020/5/29
     * @Param [targetDate, format]
     * @return java.lang.String
     *
     **/
    public static Date toDateFormat(String targetDate,String format){

        SimpleDateFormat sdf = null;
        if(format==null || format.equals("")){
            sdf = new SimpleDateFormat(defaultformat);
        }else {
            sdf = new SimpleDateFormat(format);//设置日期格式
        }

        Date date = null;
        try {
            // 注意格式需要与上面一致，不然会出现异常
            date = sdf.parse(targetDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String getNowTime(String format){
        String ret=null;
        if(format==null)
            format="yyyyMMddHHmmss";
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
            ret=df.format(new Date());
        }catch(Exception e){
            format="yyyyMMddHHmmss";
            SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
            ret=df.format(new Date());
        }
        System.out.println();// new Date()为获取当前系统时间
        return ret;
    }
    public static String getYesterdayTime(String format){
        String ret=null;
        if(format==null)
            format="yyyyMMddHHmmss";
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            int day = c.get(Calendar.DATE);
            c.set(Calendar.DATE, day - 1);
            ret=df.format(c.getTime());
        }catch(Exception e){
            format="yyyyMMddHHmmss";
            SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            int day = c.get(Calendar.DATE);
            c.set(Calendar.DATE, day - 1);
            ret=df.format(c.getTime());
        }
        System.out.println();// new Date()为获取当前系统时间
        return ret;
    }
    public static String DateRollDate(Date date,int day) {
    	long times=date.getTime()-day*24*60*60*1000;
    	Date rollDate=new Date(times);
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	return sdf.format(rollDate);
    }
    public static Date DateRollDate1(Date date,int day) {
    	long times=date.getTime()-day*24*60*60*1000;
    	Date rollDate=new Date(times);
    	return rollDate;
    }
}

