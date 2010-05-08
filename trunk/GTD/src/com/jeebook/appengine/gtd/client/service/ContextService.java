package com.jeebook.appengine.gtd.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;

public class ContextService {

	private DataListener _dl = null;
	public ContextService( DataListener dl ) {
		_dl = dl;
	}
	
	public void New(String name) {
		// Send request to server and catch any errors.
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, GWT
				.getModuleBaseURL()
				+ "context/");
		JSONObject cd = new JSONObject();
		cd.put("name", new JSONString(name));

		try {
			builder.sendRequest(cd.toString(), new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					Window.alert(exception.getMessage());
				}

				public void onResponseReceived(Request request,
						Response response) {
					if (200 == response.getStatusCode()) {
						Window.alert(response.getStatusText());
					} else {
						ErrorService es = new ErrorService();
						es.doHttpReturn(response.getStatusCode(), response
								.getText());

						Window.alert(response.getText());
					}
				}
			});
		} catch (RequestException e) {
			Window.alert(e.getMessage());
		}
	}

	public void Get(String id ) {
		// Send request to server and catch any errors.
		String url = GWT.getModuleBaseURL()	+ "context/" + id.toString();
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

		try {
			JSONArray ja = null;
			builder.sendRequest(null, new RequestCallback() {

				public void onResponseReceived(Request request, Response response) {
					if (Response.SC_OK == response.getStatusCode()) {
						_dl.Update(ContextService.this, JSONParser.parse(response.getText()));
					} else {
						ErrorService es = new ErrorService();
						es.doHttpReturn(response.getStatusCode(), response.getText());

						Window.alert(response.getText());
					}

				}

				public void onError(Request request, Throwable exception) {
					Window.alert(exception.getMessage());
				}
			});
		} catch (RequestException e) {
			Window.alert(e.getMessage());
		}
	}
}
