/***********************************************************************************
 * The MIT License (MIT)
 * Copyright (c) 2014 Robin Chutaux
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ***********************************************************************************/
package com.andexert.calendarlistview.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class SimpleMonthAdapter extends RecyclerView.Adapter<SimpleMonthAdapter.ViewHolder> implements SimpleMonthView.OnDayClickListener {
    protected static final int MONTHS_IN_YEAR = 12;
    private final TypedArray typedArray;
    private final Context mContext;
    private final DatePickerController mController;
    private final Calendar calendar;
    private  SelectedDays<CalendarDay> selectedDays;
    /**
     * 开始月份
     */
    private final Integer firstMonth;
    /**
     * 结束月份
     */
    private final Integer lastMonth;

    /**
     * 控件模式类型
     */
    private int modelType = Config.TYPE_NORMAL;

    /**
     * 判断今天能否可以选择
     * true：可选，文字显示黑色
     * false：不可选，文字显示灰 色
     */
    private boolean isTodaySelect;

    public SimpleMonthAdapter(Context context, @AModelType int modelType, DatePickerController datePickerController, TypedArray typedArray) {
        this.typedArray = typedArray;
        calendar = Calendar.getInstance();
        firstMonth = calendar.get(Calendar.MONTH);
        lastMonth = (calendar.get(Calendar.MONTH) - 1) % MONTHS_IN_YEAR;
        this.modelType = modelType;
        mContext = context;
        mController = datePickerController;
        selectedDays = mController.setDateRangeSelected();
        init();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final SimpleMonthView simpleMonthView = new SimpleMonthView(mContext, typedArray);
        return new ViewHolder(simpleMonthView, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final SimpleMonthView monthView = viewHolder.simpleMonthView;
        //设置今天是否可以选择
        monthView.setTodaySelect(isTodaySelect);
        monthView.setModelType(modelType);
        final HashMap<String, Integer> drawingParams = new HashMap<String, Integer>();
        int month;
        int year;

        if (CalendarUtils.isFuture(mController)) {
            month = (firstMonth + (position % MONTHS_IN_YEAR)) % MONTHS_IN_YEAR;
            year = position / MONTHS_IN_YEAR + calendar.get(Calendar.YEAR) + ((firstMonth + (position % MONTHS_IN_YEAR)) / MONTHS_IN_YEAR);
        } else {
            month = (firstMonth + (position % MONTHS_IN_YEAR) + 1) % MONTHS_IN_YEAR;
            //例：2019-1-3（minYear:2018,itemCount:12） 11/12+2018+((0+1+(11%12))/12
            year = position / MONTHS_IN_YEAR + mController.getMinYear() + ((firstMonth + 1 + (position % MONTHS_IN_YEAR)) / MONTHS_IN_YEAR);
        }

        int selectedFirstDay = -1;
        int selectedLastDay = -1;
        int selectedFirstMonth = -1;
        int selectedLastMonth = -1;
        int selectedFirstYear = -1;
        int selectedLastYear = -1;

        if (selectedDays.getFirst() != null) {
            selectedFirstDay = selectedDays.getFirst().day;
            selectedFirstMonth = selectedDays.getFirst().month;
            selectedFirstYear = selectedDays.getFirst().year;
        }

        if (selectedDays.getLast() != null) {
            selectedLastDay = selectedDays.getLast().day;
            selectedLastMonth = selectedDays.getLast().month;
            selectedLastYear = selectedDays.getLast().year;
        }

        monthView.reuse();

        drawingParams.put(SimpleMonthView.VIEW_PARAMS_SELECTED_BEGIN_YEAR, selectedFirstYear);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_SELECTED_LAST_YEAR, selectedLastYear);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_SELECTED_BEGIN_MONTH, selectedFirstMonth);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_SELECTED_LAST_MONTH, selectedLastMonth);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_SELECTED_BEGIN_DAY, selectedFirstDay);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_SELECTED_LAST_DAY, selectedLastDay);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_YEAR, year);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_MONTH, month);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_WEEK_START, calendar.getFirstDayOfWeek());
        //设置限制时间
        monthView.setLimitMillis(mController.getLimitMillis());
        monthView.setMaxMillis(mController.getMaxMillis());
        monthView.setMonthParams(drawingParams);
        monthView.invalidate();
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        //显示当前时间～最大时间
        if (CalendarUtils.isFuture(mController)) {
            itemCount = (((mController.getMaxYear() - calendar.get(Calendar.YEAR)) + 1) * MONTHS_IN_YEAR);
        }
        //显示最小时间～当前时间
        else {
            int i = calendar.get(Calendar.YEAR);
            itemCount = ((calendar.get(Calendar.YEAR) - mController.getMinYear()) * MONTHS_IN_YEAR);
        }
        return itemCount;
    }

    /**
     * 设置控件模式类型
     *
     * @param modelType
     */
    public void setModelType(@AModelType int modelType) {
        this.modelType = modelType;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final SimpleMonthView simpleMonthView;

        public ViewHolder(View itemView, SimpleMonthView.OnDayClickListener onDayClickListener) {
            super(itemView);
            simpleMonthView = (SimpleMonthView) itemView;
            simpleMonthView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            simpleMonthView.setClickable(true);
            simpleMonthView.setOnDayClickListener(onDayClickListener);
        }
    }

    protected void init() {
        if (typedArray.getBoolean(R.styleable.DayPickerView_currentDaySelected, false))
            onDayTapped(new CalendarDay(System.currentTimeMillis()));
    }

    public void onDayClick(SimpleMonthView simpleMonthView, CalendarDay calendarDay) {
        if (calendarDay != null) {
            onDayTapped(calendarDay);
        }
    }

    protected void onDayTapped(CalendarDay calendarDay) {
        mController.onDayOfMonthSelected(calendarDay.year, calendarDay.month, calendarDay.day);
        setSelectedDay(calendarDay);
    }

    public void setSelectedDay(CalendarDay calendarDay) {
        switch (modelType) {
            case Config.TYPE_NORMAL:
                setSelectedDayToNormal(calendarDay);
                break;
            case Config.TYPE_SINGLE:
                setSelectedDayToSingle(calendarDay);
                break;
        }
        notifyDataSetChanged();
    }

    /**
     * normal模式下的点击后的数据处理
     *
     * @param calendarDay
     */
    private void setSelectedDayToNormal(CalendarDay calendarDay) {
        //已选择第一天，没有选择第二天
        if (selectedDays.getFirst() != null && selectedDays.getLast() == null) {
            //第一天时间戳
            long first = Utils.getTimeInMillis(selectedDays.first.year, selectedDays.first.month, selectedDays.first.day);
            long last = Utils.getTimeInMillis(calendarDay.year, calendarDay.month, calendarDay.day);
            //点击开始结束为同一天，取消两次选择
            if (first == last) {
                selectedDays.setFirst(null);
                selectedDays.setLast(null);
                return;
            }
            selectedDays.setLast(calendarDay);

            if (selectedDays.getFirst().month < calendarDay.month) {
                for (int i = 0; i < selectedDays.getFirst().month - calendarDay.month - 1; ++i)
                    mController.onDayOfMonthSelected(selectedDays.getFirst().year, selectedDays.getFirst().month + i, selectedDays.getFirst().day);
            }

            mController.onDateRangeSelected(selectedDays);
        }
        //第一天，第二天都被选择，清空第一天数据
        else if (selectedDays.getLast() != null) {
            selectedDays.setFirst(calendarDay);
            selectedDays.setLast(null);
        }
        //两天都没选择，设置第一天
        else {
            selectedDays.setFirst(calendarDay);
        }
    }

    /**
     * single模式下的点击后的数据处理
     *
     * @param calendarDay
     */
    private void setSelectedDayToSingle(CalendarDay calendarDay) {
        selectedDays.setFirst(calendarDay);
        selectedDays.setLast(null);
    }

    public static class CalendarDay implements Serializable {
        private static final long serialVersionUID = -5456695978688356202L;
        private Calendar calendar;

        int day;
        int month;
        int year;

        public CalendarDay() {
            setTime(System.currentTimeMillis());
        }

        public CalendarDay(int year, int month, int day) {
            setDay(year, month, day);
        }

        public CalendarDay(long timeInMillis) {
            setTime(timeInMillis);
        }

        public CalendarDay(Calendar calendar) {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        private void setTime(long timeInMillis) {
            if (calendar == null) {
                calendar = Calendar.getInstance();
            }
            calendar.setTimeInMillis(timeInMillis);
            month = this.calendar.get(Calendar.MONTH);
            year = this.calendar.get(Calendar.YEAR);
            day = this.calendar.get(Calendar.DAY_OF_MONTH);
        }

        public void set(CalendarDay calendarDay) {
            year = calendarDay.year;
            month = calendarDay.month;
            day = calendarDay.day;
        }

        public void setDay(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public Date getDate() {
            if (calendar == null) {
                calendar = Calendar.getInstance();
            }
            calendar.set(year, month, day);
            return calendar.getTime();
        }

        public int getYearDay() {
            if (calendar == null) {
                calendar = Calendar.getInstance();
            }
            calendar.set(year, month, day);
            return calendar.get(Calendar.DAY_OF_YEAR);
        }

        @Override
        public String toString() {
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{ year: ");
            stringBuilder.append(year);
            stringBuilder.append(", month: ");
            stringBuilder.append(month);
            stringBuilder.append(", day: ");
            stringBuilder.append(day);
            stringBuilder.append(" }");

            return stringBuilder.toString();
        }
    }

    public SelectedDays<CalendarDay> getSelectedDays() {
        return selectedDays;
    }

    /**
     * 是否设置今天可以选择
     *
     * @param isSelect
     */
    public void setTodaySelect(boolean isSelect) {
        this.isTodaySelect = isSelect;
    }

    public static class SelectedDays<K> implements Serializable {
        private static final long serialVersionUID = 3942549765282708376L;
        private K first;
        private K last;

        public K getFirst() {
            return first;
        }

        public void setFirst(K first) {
            this.first = first;
        }

        public K getLast() {
            return last;
        }

        public void setLast(K last) {
            this.last = last;
        }
    }
}