package com.jeebook.appengine.gtd.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Event.NativePreviewEvent;

public class NewActionDialog extends DialogBox {

	private static NewActionDialogUiBinder uiBinder = GWT
			.create(NewActionDialogUiBinder.class);

	interface NewActionDialogUiBinder extends UiBinder<Widget, NewActionDialog> {}
	
	@UiField TextBox titleTextBox;
	
	public NewActionDialog() {
	    // Use this opportunity to set the dialog's caption.
	    setText("Action");
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

	  @UiHandler("closeButton")
	  void onCloseClicked(ClickEvent event) {
	    hide();
	  }
	  
	  @UiHandler("addProjectButton")
	  void onAddProjectClicked(ClickEvent event) {
	    NewProjectDialog dlg = new NewProjectDialog();
	    dlg.show();
	    dlg.center();
	  }

	  @UiHandler("addContextButton")
	  void onAddContextClicked(ClickEvent event) {
	    NewContextDialog dlg = new NewContextDialog();
	    dlg.show();
	    dlg.center();
	  }
}
