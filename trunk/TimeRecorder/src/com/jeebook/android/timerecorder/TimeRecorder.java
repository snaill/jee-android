package com.jeebook.android.timerecorder;

import java.util.*;
import java.text.*;
import android.app.*;
import android.view.*;
import android.widget.*;
import android.os.Bundle;
import android.content.Intent; 
import com.jeebook.android.*;

public class TimeRecorder extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 自定义标题栏
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);  
        setContentView(R.layout.main);         
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);  
        
        // 初始化按钮
        Button btnOption = (Button)findViewById(R.id.Button02);
        btnOption.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(TimeRecorder.this, TimeRecorderOption.class);
				startActivity(intent);
				TimeRecorder.this.finish();
			}
		});
        
        // 获取配置
        AppConfig ac = new AppConfig(this, R.string.config_name);
        ac.load();
        if ( ac.isEmpty() )
        {
        	ac.set(R.string.op_interval_hour, 8);
        	ac.set(R.string.op_interval_minute, 0);
        	ac.save();
        }
        int hour = ac.getInt(R.string.op_interval_hour);
        int minute = ac.getInt(R.string.op_interval_minute);
        
        // 计算时间
        Date date = new Date();
        date.setTime( date.getTime() + (hour * 60 + minute) * 60 * 1000 );
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        TextView msg = (TextView)findViewById(R.id.MessageView);
        msg.setText(sdf.format(date));
        
        // 设置提醒 
        Intent intent = new Intent(this, AlarmReceiver.class);  
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);  
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
//        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+5000, pendingIntent);
        am.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
    }
    
    public boolean onTouchEvent (MotionEvent event)
    {
    	finish();
    	return true;
    }
   
}