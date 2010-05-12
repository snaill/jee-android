package com.jeebook.appengine.gtd.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class Shuffle implements EntryPoint {
	
  interface Binder extends UiBinder<DockLayoutPanel, Shuffle> { }

  interface GlobalResources extends ClientBundle {
  }
  
  private static final Binder binder = GWT.create(Binder.class);
  
  /**
   * This method constructs the application user interface by instantiating
   * controls and hooking up event handler.
   */
  public void onModuleLoad() {

	  DockLayoutPanel outer = binder.createAndBindUi(this);

      // Get rid of scrollbars, and clear out the window's built-in margin,
      // because we want to take advantage of the entire client area.
      Window.enableScrolling(false);
      Window.setMargin("0px");

      RootLayoutPanel.get().add(outer);
  }
}
