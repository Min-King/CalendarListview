package com.andexert.calendarlistview.library;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * @author lyd
 * @date 2019/1/3 16:15
 * @desription
 */
public class Utils {

    /**
     * 日期转时间戳(返回的时间戳：2019-1-2 0:0:0)
     *
     * @return
     */
    public static long getTimeInMillis(int year, int month, int day) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(year).append("-").append(month).append("-").append(day).append(" ").append(0).append(":").append(0).append(":").append(0);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(builder.toString());
            long millis = date.getTime()/1000;
            CalendarUtils.Log(" millis " + millis);
            return millis;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取时间限制的时间戳
     *
     * @return
     */
    public static long getLimitMillis(HashMap<String, Integer> params) {
        //判断是否设置限制时间
        if(params.containsKey(Config.VIEW_PARAMS_LIMIT_DAY)&&params.containsKey(Config.VIEW_PARAMS_LIMIT_MOUTH)&&params.containsKey(Config.VIEW_PARAMS_LIMIT_YEAR)){
            long millis = getTimeInMillis(params.get(Config.VIEW_PARAMS_LIMIT_YEAR),params.get(Config.VIEW_PARAMS_LIMIT_MOUTH),params.get(Config.VIEW_PARAMS_LIMIT_DAY));
            return millis;
        }else {
            return -1;
        }
    }

}
