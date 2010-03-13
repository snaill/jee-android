package com.jeebook.android;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import android.content.*;

public class AppConfig {
	private Context _context;
	private String _file;
	private Properties _prop = new Properties();
	
	public AppConfig(Context context, int fileId) {
		_context = context;
		_file = context.getResources().getString(fileId);
	}
	
	public AppConfig(Context context, String file) {
		_context = context;
		_file = file;
	}

	public boolean isEmpty() {
		return _prop.isEmpty();
	}
	
	public Properties load() {
		try {
			FileInputStream s = _context.openFileInput(_file);
			_prop.load(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _prop;
	}

	public void save()
	{
		save(_prop);
	}
	
	public void save(Properties properties) {
		try {
			FileOutputStream s = _context.openFileOutput(_file, 0);
			properties.store(s, "");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public int getInt( int keyId )
	{
		return getInt(_context.getResources().getString(keyId));
	}
	
	public int getInt( String key )
	{
		String value = _prop.getProperty(key, "0");
		return Integer.parseInt(value);
	}
	
	public String getString( int keyId )
	{
		return getString(_context.getResources().getString(keyId));
	}
	
	public String getString( String key )
	{
		return _prop.getProperty(key, "");
	}
	
	public void set(int keyId, int value )
	{
		set(_context.getResources().getString(keyId), value);
	}
	
	public void set(String key, int value )
	{
		set(key, ((Integer)value).toString());
	}
	
	public void set(String key, String value )
	{
		_prop.setProperty(key, value);
	}
}
