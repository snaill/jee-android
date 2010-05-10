package com.jeebook.appengine.gtd.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.inject.Inject;

public class Main extends ResizeComposite {
	
  interface Binder extends UiBinder<DockLayoutPanel, Main> { }

  private static final Binder binder = GWT.create(Binder.class);

  @UiField(provided=true) TopPanel topPanel;

  @Inject
  public Main(TopPanel topPanel) {
  	this.topPanel = topPanel;
  	
    // Create the UI defined in Main.ui.xml.
  	DockLayoutPanel outer = binder.createAndBindUi(this);
    initWidget(outer);
  }
}
