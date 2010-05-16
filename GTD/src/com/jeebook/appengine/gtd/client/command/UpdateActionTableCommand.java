package com.jeebook.appengine.gtd.client.command;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.FlexTable;
import com.jeebook.appengine.gtd.client.ActionWidget;
import com.jeebook.appengine.gtd.client.model.ActionData;
import com.jeebook.appengine.gtd.client.service.AjaxRequest;

public class UpdateActionTableCommand implements Command {

	FlexTable 	mTable;
	String		mUri;
	public UpdateActionTableCommand(FlexTable table, String uri ) {
		mTable = table;
		mUri = uri;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		mTable.clear();
		
		new AjaxRequest(RequestBuilder.GET, mUri) {
			
			@Override
			public void onSuccess(String response){
				JSONArray ja = (JSONArray)JSONParser.parse(response);
				for ( int i = 0; i < ja.size(); i ++ ) {
					ActionData ad = (ActionData)ja.get(i).isObject().getJavaScriptObject(); 
					ActionWidget aw = new ActionWidget(ad);
					mTable.setWidget(i, 0, aw);
				}
			}
		}.send(null);
	}

}
