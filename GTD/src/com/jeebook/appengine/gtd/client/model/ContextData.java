package com.jeebook.appengine.gtd.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class ContextData extends JavaScriptObject {
	protected ContextData() {}
	
	public final native long getId() /*-{ return this.id }-*/;
	public final native String getName() /*-{ return this.name }-*/;
}
