package com.jeebook.appengine.gtd.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.jeebook.appengine.gtd.client.command.UpdateActionTableCommand;

public class Shuffle implements EntryPoint {
	
  interface Binder extends UiBinder<DockLayoutPanel, Shuffle> { }

  interface GlobalResources extends ClientBundle {
  }
  
  private static final Binder binder = GWT.create(Binder.class);
  
  @UiField TabLayoutPanel shuffleTabPanel;
  @UiField FlexTable inboxTable;
  
  /**
   * This method constructs the application user interface by instantiating
   * controls and hooking up event handler.
   */
  public void onModuleLoad() {

	  DockLayoutPanel outer = binder.createAndBindUi(this);
	  
	  shuffleTabPanel.addSelectionHandler(new SelectionHandler<Integer>(){
		  public void onSelection(SelectionEvent<Integer> event) {  
			  Command cmd = null;
			  switch ( event.getSelectedItem() ) {
			  case 0:
				  cmd = new UpdateActionTableCommand(inboxTable, "inbox/");
				  break;
			  case 1:
				  break;
			  case 2:
				  break;
			  }
			  
			  if ( cmd != null )
				  cmd.execute();
          } 
	  });
      // Get rid of scrollbars, and clear out the window's built-in margin,
      // because we want to take advantage of the entire client area.
      Window.enableScrolling(false);
      Window.setMargin("0px");

      RootLayoutPanel.get().add(outer);
      
//      inboxTable
  }
}
