package com.jeebook.appengine.gtd.client.gin;


import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.jeebook.appengine.gtd.client.formatter.ActionDateFormatter;
import com.jeebook.appengine.gtd.client.model.LoginInfo;

public class ShuffleGinModule extends AbstractGinModule {

  protected void configure() {
  	bind(ActionDateFormatter.class).in(Singleton.class);

  	bind(LoginInfo.class).in(Singleton.class);

  	// event hub (TODO hide this from project classes) 
  	bind(HandlerManager.class).in(Singleton.class);
  }

}
