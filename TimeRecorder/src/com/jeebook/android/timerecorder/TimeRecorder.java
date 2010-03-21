package com.jeebook.android.timerecorder;

import java.util.*;
import android.app.*;
import android.view.*;
import android.widget.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.content.Context;
import android.content.Intent; 
import android.content.SharedPreferences;

public class TimeRecorder extends Activity {
	Timer _timer = new Timer();
	Date _date = new Date();

	Handler handler = new Handler() {
        public void handleMessage(Message msg) {  
            switch (msg.what) {      
            	case 1: {
            		long time = _date.getTime() - System.currentTimeMillis();
            		if ( time < 0 )
            		{	
            			time = 0;
            			_timer.cancel();
            		}
            		TextView messager = (TextView)findViewById(R.id.MessageView);
                	messager.setText(String.format("%d ms", time));
            		break;
            	}
            }      
            super.handleMessage(msg);  
        } 
	};
	
	TimerTask task = new TimerTask(){  
		  
	        public void run() {  
	            Message message = new Message();      
	            message.what = 1;      
	            handler.sendMessage(message);    
	        }   
	}; 
	
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
				ShowOption();
			}
		});
        
        // 获取配置
        String strInterval = getString(R.string.pref_key_interval);
        String strAlarmTime = getString(R.string.pref_key_alarmtime);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if ( !preferences.contains(strInterval))
        {
        	ShowOption();
        	return;
        }
        
        long atime = preferences.getLong(strAlarmTime, 0);
        if ( atime != 0 )
        {
        	_date.setTime(atime);
        }
        else
        {
        	String interval = preferences.getString(strInterval, "0:00");
        	int hour = Integer.valueOf(interval.split(":")[0]);
        	int minute = Integer.valueOf(interval.split(":")[1]);
        
        	// 计算时间
        	_date.setTime( _date.getTime() + (hour * 60 + minute) * 60 * 1000 );
        
        	// 设置提醒 
        	StartAlarm(this, _date);
        	
        	// 保存提醒
           	SharedPreferences.Editor edit = preferences.edit();
            edit.putLong(strAlarmTime, _date.getTime());
            edit.commit();
        }
        
        // 倒计时
        java.util.Random r=new java.util.Random();
        int rnd = ( r.nextInt() >>> 1 ) % 100;
        _timer.scheduleAtFixedRate(task, 0, rnd);
    }
    
    public boolean onTouchEvent (MotionEvent event)
    {
    	finish();
    	return true;
    }
   
    public void ShowOption()
    {
		Intent intent = new Intent();
		intent.setClass(TimeRecorder.this, TimeRecorderOption.class);
		startActivity(intent);
		finish();
    }
    
    public static void StartAlarm(Context context, Date date)
    {
        Intent intent = new Intent(context, AlarmReceiver.class);  
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);  
        AlarmManager am = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+5000, pendingIntent);
//        am.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent); 	
    }
    
    public static void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, AlarmReceiver.class);  
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);  
        AlarmManager am = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        am.cancel(pendingIntent);
    }    
}