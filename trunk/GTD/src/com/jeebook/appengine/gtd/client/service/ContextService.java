package com.jeebook.appengine.gtd.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;

public class ContextService {
	
	public void New( String name )
	{
		// Send request to server and catch any errors.
	    RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, 
	    		GWT.getModuleBaseURL()+ "context/");
		JSONObject cd = new JSONObject();
		cd.put("name", new JSONString(name));
	    
	    try {
	      builder.sendRequest(cd.toString(), new RequestCallback() {
	        public void onError(Request request, Throwable exception) {
	        	  Window.alert(exception.getMessage());
	        }

	        public void onResponseReceived(Request request, Response response) {
	          if (200 == response.getStatusCode()) {
	        	  Window.alert(response.getStatusText());
	          }
	          else
	          {
	        	  ErrorService es = new ErrorService();
	        	  es.doHttpReturn(response.getStatusCode(), response.getText());
	        
	        	  Window.alert(response.getText());
	          }
	        }
	      });
	    } catch (RequestException e) {
	    	Window.alert(e.getMessage());
	    }
	}
}
