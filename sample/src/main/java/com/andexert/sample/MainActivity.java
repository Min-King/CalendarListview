package com.andexert.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.andexert.calendarlistview.library.Config;
import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.PickerController;

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
    }
}
