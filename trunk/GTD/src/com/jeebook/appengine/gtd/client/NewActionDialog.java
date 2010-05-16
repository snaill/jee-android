package com.jeebook.appengine.gtd.client;

import java.util.Date;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.jeebook.appengine.gtd.client.command.UpdateContextListBoxCommand;
import com.jeebook.appengine.gtd.client.model.ActionData;
import com.jeebook.appengine.gtd.client.model.ProjectData;
import com.jeebook.appengine.gtd.client.service.AjaxRequest;

public class NewActionDialog extends DialogBox {

	private static NewActionDialogUiBinder uiBinder = GWT
			.create(NewActionDialogUiBinder.class);

	interface NewActionDialogUiBinder extends UiBinder<Widget, NewActionDialog> {}
	
	@UiField TextBox nameTextBox;
	@UiField TextArea detailsTextArea;
	@UiField ListBox projectListBox;
	@UiField ListBox contextListBox;
	@UiField DatePicker dueTimeDatePicker;
	
	public NewActionDialog() {
	    // Use this opportunity to set the dialog's caption.
	    setText("Action");
	    setWidget(uiBinder.createAndBindUi(this));

	    setAnimationEnabled(true);
	    setGlassEnabled(true);
	    
	    updateProjectListBox();
	    
	    UpdateContextListBoxCommand cmd = new UpdateContextListBoxCommand(contextListBox);
	    cmd.execute();
	    nameTextBox.setFocus(true);
	}

	void updateProjectListBox() {
		projectListBox.clear();
		
		new AjaxRequest(RequestBuilder.GET, "project/") {
			
			@Override
			public void onSuccess(String response){
				JSONArray ja = (JSONArray)JSONParser.parse(response);
				for ( int i = 0; i < ja.size(); i ++ ) {
					ProjectData pd = (ProjectData)ja.get(i).isObject().getJavaScriptObject(); 
					projectListBox.addItem(pd.getName(), pd.getId().toString());
				}
			}
		}.send(null);	
	}

	void New() {
		ActionData ad = (ActionData)ActionData.createObject();
		ad.setName(nameTextBox.getText());
		ad.setDetails(detailsTextArea.getText());
		ad.setProjectId(projectListBox.getValue(projectListBox.getSelectedIndex()));
		ad.setContextId(contextListBox.getValue(contextListBox.getSelectedIndex()));
		Date dueTime = dueTimeDatePicker.getValue();
		if ( null != dueTime )
			ad.setDueDate(dueTime.toString());
		
		new AjaxRequest(RequestBuilder.POST, "action/").send(new JSONObject(ad).toString());	
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
		New();  
	    
		  hide();
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
	    
	    updateProjectListBox();
	  }

	  @UiHandler("addContextButton")
	  void onAddContextClicked(ClickEvent event) {
	    NewContextDialog dlg = new NewContextDialog();
	    dlg.show();
	    dlg.center();
	    
	    UpdateContextListBoxCommand cmd = new UpdateContextListBoxCommand(contextListBox);
	    cmd.execute();
	  }
}
