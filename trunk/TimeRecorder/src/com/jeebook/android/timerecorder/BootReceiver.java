package com.jeebook.android.timerecorder;

import java.util.Date;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent i) {
		// TODO Auto-generated method stub
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String strAlarmTime = context.getString(R.string.pref_key_alarmtime);
        if ( !preferences.contains(strAlarmTime))
        	return;

        long atime = preferences.getLong(strAlarmTime, 0);
        if ( atime == 0 )
        	return;
        
        Date date = new Date(atime);
        TimeRecorder.StartAlarm(context, date);
	}
}