package com.jeebook.appengine.gtd.client.command;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ListBox;
import com.jeebook.appengine.gtd.client.model.ContextData;
import com.jeebook.appengine.gtd.client.service.AjaxRequest;

public class UpdateContextListBoxCommand implements Command {

	ListBox		mContextListBox;
	public UpdateContextListBoxCommand( ListBox listbox ) {
		mContextListBox = listbox;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		mContextListBox.clear();
			
		new AjaxRequest(RequestBuilder.GET, "context/") {
			
			@Override
			public void onSuccess(String response){
				JSONArray ja = (JSONArray)JSONParser.parse(response);
				for ( int i = 0; i < ja.size(); i ++ ) {
					ContextData pd = (ContextData)ja.get(i).isObject().getJavaScriptObject(); 
					mContextListBox.addItem(pd.getName(), pd.getId().toString());
				}
			}
		}.send(null);	
	}
}
