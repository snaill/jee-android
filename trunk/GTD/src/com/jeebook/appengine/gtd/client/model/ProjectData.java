package com.jeebook.appengine.gtd.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class ProjectData extends JavaScriptObject {
	protected ProjectData() {}
	
	public final native Long getId() /*-{ return this.id }-*/;
	public final native String getName() /*-{ return this.name }-*/;
	public final native Long getDefaultContextId() /*-{ return this.defaultContextId }-*/;
}