package com.jeebook.appengine.gtd.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class ContextData extends JavaScriptObject {
	protected ContextData() {}
	
	public final native String getKey() /*-{ return this.key }-*/;
	public final native String getName() /*-{ return this.name }-*/;
}
