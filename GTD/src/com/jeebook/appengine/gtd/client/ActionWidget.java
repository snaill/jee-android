package com.jeebook.appengine.gtd.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.jeebook.appengine.gtd.client.model.ActionData;

public class ActionWidget extends Composite {

	private static ActionWidgetUiBinder uiBinder = GWT
			.create(ActionWidgetUiBinder.class);

	interface ActionWidgetUiBinder extends UiBinder<Widget, ActionWidget> {
	}

	@UiField HTML nameHtml;

	public ActionWidget(ActionData ad) {
		initWidget(uiBinder.createAndBindUi(this));	
		nameHtml.setHTML(ad.getName());
	}

	@UiHandler("finishButton")
	void onFinishClick(ClickEvent e) {
		Window.alert("Hello!");
	}

}
