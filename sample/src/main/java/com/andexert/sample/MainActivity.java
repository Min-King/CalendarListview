package com.andexert.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.andexert.calendarlistview.library.Config;
import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.PickerController;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;

import java.sql.Time;
import java.util.Date;


public class MainActivity extends Activity {

    private DayPickerView dayPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dayPickerView = (DayPickerView) findViewById(R.id.pickerView);
        dayPickerView.setTodaySelect(false);
        dayPickerView.setController(new Controller());
        dayPickerView.setModelType(Config.TYPE_NORMAL);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class Controller extends PickerController {
        @Override
        public int getMinYear() {
            return 2017;
        }

        @Override
        public long getMaxMillis() {
            long timeMillis = System.currentTimeMillis();
            Date date = new Date(timeMillis + (1000 * 60 * 60 * 24*5));
            return date.getTime()/1000;
        }

//        @Override
//        public SimpleMonthAdapter.SelectedDays setDateRangeSelected() {
//            SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays =new SimpleMonthAdapter.SelectedDays<>();
//            SimpleMonthAdapter.CalendarDay time=new SimpleMonthAdapter.CalendarDay();
//            SimpleMonthAdapter.CalendarDay time1=new SimpleMonthAdapter.CalendarDay();
//            time.setDay(2020,3,17);
//            time1.setDay(2020,3,29);
//            selectedDays.setFirst(time);
//            selectedDays.setLast(time1);
//            return selectedDays;
//        }
    }
}
