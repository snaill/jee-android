package com.jeebook.appengine.gtd.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class ErrorService {
	
	public void doHttpReturn( int status, String ret ) {
		JSONValue respValue = JSONParser.parse(ret);
		JSONObject jo = respValue.isObject();
		String url = jo.get("url").isString().stringValue();
		url = url.substring(1);
		url = GWT.getHostPageBaseURL() + url;
		switch ( status ) {
			case Response.SC_UNAUTHORIZED:
				redirect(url);
				break;
		}
		
	}
	
	public static native void redirect(String url)/*-{ $wnd.location = url;	}-*/; 
}
