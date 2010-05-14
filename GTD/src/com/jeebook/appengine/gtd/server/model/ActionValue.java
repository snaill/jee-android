package com.jeebook.appengine.gtd.server.model;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActionValue {
    private String mId;
   
    private String mName;

    private String mDetails;

    private String mProjectId;
    
    private String mContextId;
    
    private String mDueDate;

    private String mFinishDate;
    
    public final String getId() {
        return mId;
    }
    
	public final String getName() {
		return mName;
	}

	public final String getDetails() {
		return mDetails;
	}

	public final String getProjectId() {
		return mProjectId;
	}

	public final String getContextId() {
		return mContextId;
	}

	public final String getDueDate() {
		return mDueDate;
	}

	public final String getFinishDate() {
		return mFinishDate;
	}

    public final void setId( String id ) {
        mId = id;
    }
    
	public final void setName( String name ) {
		mName = name;
	}

	public final void setDetails( String details ) {
		mDetails = details;
	}

	public final void setProjectId( String id ) {
		mProjectId = id;
	}

	public final void setContextId( String id ) {
		mContextId = id;
	}

	public final void setDueDate( String date ) {
		mDueDate = date;
	}

	public final void setFinishDate( String date ) {
		mFinishDate = date;
	}	
	
	public static ActionValue fromJson( String json ) {
		ActionValue value = new ActionValue();
		JSONObject jo;
		try {
			jo = new JSONObject(json);
			if ( jo.has("id") )
				value.setId( jo.getString("id") );
			if ( jo.has("name") )
				value.setName( jo.getString("name") );	
			if ( jo.has("details") )
				value.setDetails( jo.getString("details") );	
			if ( jo.has("projectId") )
				value.setProjectId( jo.getString("projectId") );
			if ( jo.has("contextId") )
				value.setContextId( jo.getString("contextId") );
			if ( jo.has("dueDate") )
				value.setDueDate( jo.getString("dueDate") );
			if ( jo.has("finishDate") )
				value.setFinishDate( jo.getString("finishDate") );
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public static String toJson(List<ActionValue> values) {
		JSONArray ja = new JSONArray(values);
		return ja.toString();
	}
	
	public String toJson() {
		
		JSONObject jo = new JSONObject();
		try {
			jo.put("id", getId());
			jo.put("name", getName());
			jo.put("details", getDetails());
			jo.put("projectId", getProjectId());
			jo.put("contextId", getContextId());
			jo.put("dueDate", getDueDate());
			jo.put("finishDate", getFinishDate());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jo.toString();
	}
}
