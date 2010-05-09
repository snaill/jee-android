package com.jeebook.appengine.gtd.server.model;

import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActionValue {
    private Long mId;
   
    private String mName;

    private String mDetails;

    private Long mProjectId;
    
    private Long mContextId;
    
    private Date mDueDate;

    private Date mFinishDate;
    
    public final long getId() {
        return mId;
    }
    
	public final String getName() {
		return mName;
	}

	public final String getDetails() {
		return mDetails;
	}

	public final Long getProjectId() {
		return mProjectId;
	}

	public final Long getContextId() {
		return mContextId;
	}

	public final Date getDueDate() {
		return mDueDate;
	}

	public final Date getFinishDate() {
		return mFinishDate;
	}

    public final void setId( long id ) {
        mId = id;
    }
    
	public final void setName( String name ) {
		mName = name;
	}

	public final void setDetails( String details ) {
		mDetails = details;
	}

	public final void setProjectId( long id ) {
		mProjectId = id;
	}

	public final void setContextId( long id ) {
		mContextId = id;
	}

	public final void setDueDate( Date date ) {
		mDueDate = date;
	}

	public final void setFinishDate( Date date ) {
		mFinishDate = date;
	}	
	
	public static ActionValue fromJson( String json ) {
		ActionValue value = new ActionValue();
		JSONObject jo;
		try {
			jo = new JSONObject(json);
			if ( jo.has("id") )
				value.setId( jo.getInt("id") );
			if ( jo.has("name") )
				value.setName( jo.getString("name") );	
			if ( jo.has("details") )
				value.setDetails( jo.getString("details") );	
			if ( jo.has("projectId") )
				value.setProjectId( jo.getInt("projectId") );
			if ( jo.has("contextId") )
				value.setContextId( jo.getInt("contextId") );
			if ( jo.has("dueDate") )
				value.setDueDate( new Date(jo.getInt("dueDate")) );
			if ( jo.has("finishDate") )
				value.setFinishDate( new Date(jo.getInt("finishDate")) );
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
			jo.put("dueDate", getDueDate().getTime());
			jo.put("finishDate", getFinishDate().getTime());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jo.toString();
	}
}
