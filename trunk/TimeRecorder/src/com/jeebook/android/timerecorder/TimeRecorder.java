package com.jeebook.android.timerecorder;

import android.app.*;
import android.view.*;
import android.widget.*;
import android.os.Bundle;

public class TimeRecorder extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btnOk = (Button)findViewById(R.id.Button01);
        btnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onTouchEvent(null);
			}
		});
        
        Button btnOption = (Button)findViewById(R.id.Button02);
        btnOption.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
 //       java.text.DateFormat df = new java.text.
    }
    
    public boolean onTouchEvent (MotionEvent event)
    {
    	finish();
    	return true;
    }
   
}