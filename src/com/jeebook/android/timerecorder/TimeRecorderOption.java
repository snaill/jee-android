package com.jeebook.android.timerecorder;

import com.jeebook.android.AppConfig;
import android.app.*;
import android.widget.*;
import android.os.Bundle;

public class TimeRecorderOption extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option);
        
        AppConfig ac = new AppConfig(this, R.string.config_name);
        ac.load();
        int hour = ac.getInt(R.string.op_interval_hour);
        int minute = ac.getInt(R.string.op_interval_minute);
        
        TimePicker tp = (TimePicker)findViewById(R.id.TimePicker01);
        tp.setIs24HourView(true);
        tp.setCurrentHour(hour);
        tp.setCurrentMinute(minute);
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	
        TimePicker tp = (TimePicker)findViewById(R.id.TimePicker01);
        AppConfig ac = new AppConfig(this, R.string.config_name);
        ac.set(R.string.op_interval_hour, tp.getCurrentHour());
    	ac.set(R.string.op_interval_minute, tp.getCurrentMinute());
    	ac.save(); 
    }
}
