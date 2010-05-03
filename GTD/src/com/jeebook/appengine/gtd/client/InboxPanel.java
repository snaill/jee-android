package com.jeebook.appengine.gtd.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class InboxPanel extends Composite {

	private static InboxPanelUiBinder uiBinder = GWT
			.create(InboxPanelUiBinder.class);

	interface InboxPanelUiBinder extends UiBinder<Widget, InboxPanel> {
	}

	@UiField Button button;

	public InboxPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public InboxPanel(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		button.setText(firstName);
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}

}
