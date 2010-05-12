package com.jeebook.appengine.gtd.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.jeebook.appengine.gtd.client.model.ContextData;
import com.jeebook.appengine.gtd.client.model.ProjectData;
import com.jeebook.appengine.gtd.client.service.AjaxRequest;

public class NewActionDialog extends DialogBox {

	private static NewActionDialogUiBinder uiBinder = GWT
			.create(NewActionDialogUiBinder.class);

	interface NewActionDialogUiBinder extends UiBinder<Widget, NewActionDialog> {}
	
	@UiField TextBox nameTextBox;
	@UiField TextBox detailsTextBox;
	@UiField ListBox projectListBox;
	@UiField ListBox contextListBox;
	
	public NewActionDialog() {
	    // Use this opportunity to set the dialog's caption.
	    setText("Action");
	    setWidget(uiBinder.createAndBindUi(this));

	    setAnimationEnabled(true);
	    setGlassEnabled(true);
	    
	    FillProjectListBox();
	    FillContextListBox();
	    nameTextBox.setFocus(true);
	}

	void FillProjectListBox() {
		new AjaxRequest(RequestBuilder.GET, "project/") {
			
			@Override
			public void onSuccess(String response){
				JsArray<ProjectData> ja = asArrayOfProjectData(response);
				for ( int i = 0; i < ja.length(); i ++ ) {
					ProjectData pd = ja.get(i); 
					projectListBox.addItem(pd.getName(), pd.getId().toString());
				}
			}
		}.send(null);	
	}

	void FillContextListBox() {
		new AjaxRequest(RequestBuilder.GET, "context/") {
			
			@Override
			public void onSuccess(String response){
				JsArray<ContextData> ja = asArrayOfContextData(response);
				for ( int i = 0; i < ja.length(); i ++ ) {
					ContextData pd = ja.get(i); 
					contextListBox.addItem(pd.getName(), pd.getId().toString());
				}
			}
		}.send(null);	
	}

	void saveAction() {
		JSONObject	jo = new JSONObject();
		jo.put("name", new JSONString(nameTextBox.getText()));
		jo.put("details", new JSONString(detailsTextBox.getText()));
		jo.put("projectId", new JSONString(nameTextBox.getText()));
		jo.put("contextId", new JSONString(nameTextBox.getText()));
//		jo.put("dueDate", new JSONDate)
		
		new AjaxRequest(RequestBuilder.POST, "action/") {
			
			@Override
			public void onSuccess(String response){
				JsArray<ContextData> ja = asArrayOfContextData(response);
				for ( int i = 0; i < ja.length(); i ++ ) {
					ContextData pd = ja.get(i); 
					contextListBox.addItem(pd.getName(), pd.getId().toString());
				}
			}
		}.send(null);	
	}
	
	  private final native JsArray<ProjectData> asArrayOfProjectData(String json) /*-{
	    return eval(json);
	  }-*/;
	  private final native JsArray<ContextData> asArrayOfContextData(String json) /*-{
	    return eval(json);
	  }-*/;
	  
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
		  
	    
	  }
	  
	  @UiHandler("saveButton")
	  void onSaveClicked(ClickEvent event) {
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
	  }

	  @UiHandler("addContextButton")
	  void onAddContextClicked(ClickEvent event) {
	    NewContextDialog dlg = new NewContextDialog();
	    dlg.show();
	    dlg.center();
	  }
}
