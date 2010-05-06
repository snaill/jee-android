package com.jeebook.appengine.gtd.client.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.Window;

public class ContextData extends JavaScriptObject {
	protected ContextData() {}
	
	public final native String getKey() /*-{ return this.Key }-*/;
	public final native String getName() /*-{ return this.Name }-*/;
	
	public static void New(String baseUrl, ContextData cd)
	{
		// Send request to server and catch any errors.
	    RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, baseUrl + "/Context/");
//	    JSONObject jo = new JSONObject(cd);
//	    builder.setRequestData(jo.toString());
	    
	    try {
	      builder.sendRequest(null, new RequestCallback() {
	        public void onError(Request request, Throwable exception) {
	        	  Window.alert(exception.getMessage());
	        }

	        public void onResponseReceived(Request request, Response response) {
	          if (200 == response.getStatusCode()) {
	        	  Window.alert(response.getStatusText());
	          }
	          else
	          {
	        	  Window.alert(response.getStatusText());
	          }
	        }
	      });
	    } catch (RequestException e) {
	    	Window.alert(e.getMessage());
	    }
	}
}
