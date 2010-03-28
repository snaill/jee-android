package com.jeebook.android.timerecorder;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.Window;

public class TimeRecorderAlarm extends Activity {
    /** Called when the activity is first created. */
	private MediaPlayer _mp;
	private boolean _finish = false;
	
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
    	finishAlarm();
    	return true;
    }
    
    public void onPause() {
    	super.onPause();
    	
    	if ( !_finish )
    		finishAlarm();
    }
    
    public void finishAlarm() {
    	//
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor edit = preferences.edit();
		edit.putLong(getString(R.string.pref_key_alarmtime), 0);
		edit.commit();
		
		//
    	_mp.stop();
    	_mp.release();
    	finish();
    	
    	_finish = true;
    }
}
