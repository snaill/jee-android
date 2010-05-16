package com.jeebook.appengine.gtd.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.jeebook.appengine.gtd.client.command.UpdateContextListBoxCommand;
import com.jeebook.appengine.gtd.client.model.ProjectData;
import com.jeebook.appengine.gtd.client.service.AjaxRequest;

public class NewProjectDialog extends DialogBox {

	private static NewProjectDialogUiBinder uiBinder = GWT
			.create(NewProjectDialogUiBinder.class);

	interface NewProjectDialogUiBinder extends UiBinder<Widget, NewProjectDialog> {
	}

	
	@UiField TextBox nameTextBox;
	@UiField ListBox defaultContextListBox;
	
	public NewProjectDialog() {
	    // Use this opportunity to set the dialog's caption.
	    setText("Project");
	    setWidget(uiBinder.createAndBindUi(this));

	    setAnimationEnabled(true);
	    setGlassEnabled(true);
	    
	    UpdateContextListBoxCommand cmd = new UpdateContextListBoxCommand(defaultContextListBox);
	    cmd.execute();
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

	  @UiHandler("saveAndNewButton")
	  void onSaveAndNewClicked(ClickEvent event) {
	    New();
	  }
	  
	  @UiHandler("saveButton")
	  void onSaveClicked(ClickEvent event) {
	    hide();
	  }
	  
	  @UiHandler("closeButton")
	  void onSignOutClicked(ClickEvent event) {
	    hide();
	  }

	void New() {
		ProjectData pd = (ProjectData)ProjectData.createObject();
		pd.setName(nameTextBox.getText());
		String defaultContextId = defaultContextListBox.getValue(defaultContextListBox.getSelectedIndex());
		pd.setDefaultContextId(defaultContextId);
	    new AjaxRequest(RequestBuilder.POST, "project/").send(new JSONObject(pd).toString());
	}
}
