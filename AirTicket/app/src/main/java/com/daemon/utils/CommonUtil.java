package com.daemon.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2016/1/15.
 */
public class CommonUtil {
    /**
     * 格式化时间 yyyy-MM-dd
     * @param time
     * @return yyyy-MM-dd
     */
    public static String getFormatDate(long time){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date=new Date(time);
        return format.format(date);
    }

    /**
     * 格式化时间 yyyy年MM月dd日
     * @param time
     * @return yyyy年MM月dd日
     */
    public static String getFormatDateOnlyYear(long time){
        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
        Date date=new Date(time);
        return format.format(date);
    }

    /**
     * 格式化时间
     * @param time
     * @return X月XX日
     */
    public static String getFormatDateOnlyMonth(long time){
        SimpleDateFormat format=new SimpleDateFormat("M月dd日", Locale.getDefault());
        Date date=new Date(time);
        return format.format(date);
    }

    /**
     * 换算折扣
     */
    public static String getFormatDiscount(String discount){
        float d = Float.valueOf(discount);
        if(d>=100){
            discount = "不打折";
        }else{
            d = d/10;
            discount = String.valueOf(d)+"折";
        }
        return discount;
    }
}
