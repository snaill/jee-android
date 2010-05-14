package com.jeebook.appengine.gtd.server.model;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProjectValue {

	private String mId;
    
    private String mName;
    
    private String mDefaultContextId;
    
    public final String getId() {
        return mId;
    }

    public final String getName() {
        return mName;
    }

    public final String getDefaultContextId() {
        return mDefaultContextId;
    }  
    
    public final void setId( String id ) {
        mId = id;
    }

    public final void setName( String name ) {
        mName = name;
    }

    public final void setDefaultContextId( String id ) {
        mDefaultContextId = id;
    }   
    
	public static ProjectValue fromJson( String json ) {
		ProjectValue value = new ProjectValue();
		JSONObject jo;
		try {
			jo = new JSONObject(json);
			if ( jo.has("id") )
				value.setId( jo.getString("id") );
			if ( jo.has("name") )
				value.setName( jo.getString("name") );	
			if ( jo.has("defaultContextId") )
				value.setDefaultContextId( jo.getString("defaultContextId") );	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public static String toJson(List<ProjectValue> values) {
		JSONArray ja = new JSONArray(values);
		return ja.toString();
	}
	
	public String toJson() {
		
		JSONObject jo = new JSONObject();
		try {
			jo.put("id", getId());
			jo.put("name", getName());
			jo.put("defaultContextId", getDefaultContextId());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jo.toString();
	}
}
