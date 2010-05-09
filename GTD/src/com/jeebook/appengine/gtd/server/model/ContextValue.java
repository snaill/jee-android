package com.jeebook.appengine.gtd.server.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ContextValue {

	private Long mId;
    
	private String mName;
	
	public final long getKey() {
		return mId;
	}

	public final String getName() {
		return mName;
	}
	
	public final void setKey( long id ) {
		mId = id;
	}
	
	public final void setName( String name ) {
		mName = name;
	}
	
	public static ContextValue fromJson( String json ) {
		ContextValue value = new ContextValue();
		JSONObject jo;
		try {
			jo = new JSONObject(json);
			if ( jo.has("key") )
				value.setKey( jo.getInt("key") );
			if ( jo.has("name") )
				value.setName( jo.getString("name") );	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public static String toJson(List<ContextValue> values) {
		JSONArray ja = new JSONArray(values);
		return ja.toString();
	}
	
	public String toJson() {
		
		JSONObject jo = new JSONObject();
		try {
			jo.put("key", getKey());
			jo.put("name", getName());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jo.toString();
	}
}
