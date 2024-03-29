package com.jeebook.android.timerecorder;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.*;

public class TimeRecorderOption extends PreferenceActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       
       addPreferencesFromResource(R.xml.preferences);
           
        // 初始化配置
        String strInterval = getString(R.string.pref_key_interval);
        String strAlarmTime = getString(R.string.pref_key_alarmtime);
        
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if ( !preferences.contains(strInterval))
        {
        	SharedPreferences.Editor edit = preferences.edit();
        	edit.putString(strInterval, "8:00");
        	edit.putLong(strAlarmTime, 0);
        	edit.commit();
        }
        		
        // 初始化配置界面
       CheckBoxPreference alarmtime = (CheckBoxPreference) getPreferenceScreen().findPreference(strAlarmTime);
        long atime = preferences.getLong(strAlarmTime, 0);
        if ( atime == 0 )
        {
        	alarmtime.setChecked(false);
        	alarmtime.setEnabled(false);
        }
        else
        {
        	alarmtime.setChecked(true);
        	Date date = new Date(atime);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        	alarmtime.setSummary(sdf.format(date));
        	alarmtime.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
				
				@Override
				public boolean onPreferenceChange(Preference preference, Object newValue) {
					// TODO Auto-generated method stub
					CheckBoxPreference alarmtime = (CheckBoxPreference)preference;
					alarmtime.setChecked(false);
					alarmtime.setEnabled(false);
					SharedPreferences.Editor edit = alarmtime.getEditor();
					edit.putLong(getString(R.string.pref_key_alarmtime), 0);
					edit.commit();
					
					TimeRecorder.CancelAlarm(TimeRecorderOption.this);
					return false;
				}
			});
        }
       
        Preference interval = getPreferenceScreen().findPreference(strInterval);
        interval.setDefaultValue(preferences.getString(strInterval, "Error"));
        interval.setSummary(preferences.getString(strInterval, "Error"));
        interval.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				// TODO Auto-generated method stub
				preference.setSummary(newValue.toString());
				return true;
			}
		});
    }
    
    public void onPause() {
    	super.onPause();
    	
    	finish();
    }
}
