package com.jeebook.appengine.gtd.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.jeebook.appengine.gtd.client.service.ContextService;
import com.jeebook.appengine.gtd.client.service.DataListener;

public class ContextPanel extends Composite implements DataListener {

	private static ContextPanelUiBinder uiBinder = GWT
			.create(ContextPanelUiBinder.class);

	interface ContextPanelUiBinder extends UiBinder<Widget, ContextPanel> {
	}

	@UiField Button button;
	@UiField FlexTable contextTable;
	
	public ContextPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		button.setText("");
		
		ContextService cs = new ContextService(this);
		cs.Get("");
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}

	public void Update( Object sender, JSONValue jv )
	{
		JSONArray ja = jv.isArray();
		for ( int i = 0; i < ja.size(); i ++ )
		{
			contextTable.setText(i, 0, "aa");
		}
	}
}
