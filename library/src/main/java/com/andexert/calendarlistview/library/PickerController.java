package com.andexert.calendarlistview.library;

/**
 * @author lyd
 * @date 2019/1/4 0004 9:54
 * @desription
 */
public abstract class PickerController implements DatePickerController {

    @Override
    public long getLimitMillis() {
        return -1;
    }

    @Override
    public long getMaxMillis() {
        return -1;
    }

    @Override
    public int getMaxYear() {
        return 0;
    }

    @Override
    public int getMinYear() {
        return 0;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {

    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {

    }

    @Override
    public SimpleMonthAdapter.SelectedDays setDateRangeSelected() {
        return new SimpleMonthAdapter.SelectedDays();
    }
}
