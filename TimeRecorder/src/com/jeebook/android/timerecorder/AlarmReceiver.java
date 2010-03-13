package com.jeebook.android.timerecorder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent i) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(context, TimeRecorderAlarm.class);
		intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
