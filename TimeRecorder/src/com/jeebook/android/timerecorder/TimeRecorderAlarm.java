package com.jeebook.android.timerecorder;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;

public class TimeRecorderAlarm extends Activity {
    /** Called when the activity is first created. */
	private MediaPlayer _mp;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 自定义标题栏
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);  
        setContentView(R.layout.alarm);         
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);  

        // 初始化铃声
        _mp = MediaPlayer.create(this, R.raw.alarm);
        _mp.start();
    }
    
    public boolean onTouchEvent (MotionEvent event)
    {
    	_mp.stop();
    	_mp.release();
    	finish();
    	return true;
    }
}
