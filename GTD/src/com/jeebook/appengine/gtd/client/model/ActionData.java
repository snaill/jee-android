package com.jeebook.appengine.gtd.client.model;

import java.util.Date;
import com.google.gwt.core.client.JavaScriptObject;

public class ActionData extends JavaScriptObject {
	protected ActionData() {}
	
	public final native Long getId() /*-{ return this.id }-*/;
	public final native String getName() /*-{ return this.name }-*/;
	public final native String getDetails() /*-{ return this.details }-*/;
	public final native Long getProjectId() /*-{ return this.projectId }-*/;
	public final native Long getContextId() /*-{ return this.contextId }-*/;
	public final native Date getDueDate() /*-{ return this.dueDate }-*/;
	public final native Date getFinishDate() /*-{ return this.finishDate }-*/;
}