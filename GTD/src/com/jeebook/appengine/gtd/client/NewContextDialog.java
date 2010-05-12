package com.jeebook.appengine.gtd.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.jeebook.appengine.gtd.client.service.AjaxRequest;

public class NewContextDialog extends DialogBox {

	private static NewContextDialogUiBinder uiBinder = GWT
			.create(NewContextDialogUiBinder.class);

	interface NewContextDialogUiBinder extends UiBinder<Widget, NewContextDialog> {
	}

	@UiField TextBox nameTextBox;
	
	public NewContextDialog() {
	    // Use this opportunity to set the dialog's caption.
	    setText("About the Mail Sample");
	    setWidget(uiBinder.createAndBindUi(this));

	    setAnimationEnabled(true);
	    setGlassEnabled(true);
	}

	  @Override
	  protected void onPreviewNativeEvent(NativePreviewEvent preview) {
	    super.onPreviewNativeEvent(preview);

	    NativeEvent evt = preview.getNativeEvent();
	    if (evt.getType().equals("keydown")) {
	      // Use the popup's key preview hooks to close the dialog when either
	      // enter or escape is pressed.
	      switch (evt.getKeyCode()) {
	        case KeyCodes.KEY_ENTER:
	        case KeyCodes.KEY_ESCAPE:
	          hide();
	          break;
	      }
	    }
	  }

	  @UiHandler("saveButton")
	  void onSaveClicked(ClickEvent event) {
		  New();		  
		  hide();
	  }
	  
	  @UiHandler("saveAndNewButton")
	  void onSaveAndNewClicked(ClickEvent event) {
		  New();
		  
		  nameTextBox.setText("");
		  nameTextBox.setFocus(true);
	  }
	  
	  @UiHandler("closeButton")
	  void onCloseClicked(ClickEvent event) {
	    hide();
	  }
	  
		void New() {
			JSONObject cd = new JSONObject();
			cd.put("name", new JSONString(nameTextBox.getText()));			
		    new AjaxRequest(RequestBuilder.POST, "context/").send(cd.toString());
		}
}
