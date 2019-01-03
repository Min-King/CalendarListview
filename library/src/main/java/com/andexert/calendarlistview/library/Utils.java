package com.andexert.calendarlistview.library;

import android.util.Log;

import java.util.Calendar;

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
        return calendar.getTimeInMillis() / 1000;
    }

}
