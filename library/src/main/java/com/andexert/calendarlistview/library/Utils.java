package com.andexert.calendarlistview.library;

import android.util.Log;

import java.util.Calendar;
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
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);
        long millis = calendar.getTimeInMillis() / 1000;
        CalendarUtils.Log(" millis " + millis);
        return millis;
    }

    /**
     * 获取时间限制的时间戳
     *
     * @return
     */
    public static long getLimitMillis(HashMap<String, Integer> params) {
        //判断是否设置限制时间
        if(params.containsKey(Config.VIEW_PARAMS_LIMIT_DAY)&&params.containsKey(Config.VIEW_PARAMS_LIMIT_MOUTH)&&params.containsKey(Config.VIEW_PARAMS_LIMIT_YEAR)){
            return getTimeInMillis(params.get(Config.VIEW_PARAMS_LIMIT_YEAR),params.get(Config.VIEW_PARAMS_LIMIT_MOUTH),params.get(Config.VIEW_PARAMS_LIMIT_DAY));
        }else {
            return -1;
        }
    }

}
