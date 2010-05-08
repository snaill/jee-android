package com.jeebook.appengine.gtd.client;

import gwtupload.client.IUploader;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.jeebook.appengine.gtd.client.gin.ShuffleGinjector;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Shuffle implements EntryPoint, IUploader.OnFinishUploaderHandler {

    interface GlobalResources extends ClientBundle {
    }

    /**
     * This method constructs the application user interface by instantiating
     * controls and hooking up event handler.
     */
    public void onModuleLoad() {

        ShuffleGinjector ginjector = GWT.create(ShuffleGinjector.class);
        Main main = ginjector.getMain();

        // Get rid of scrollbars, and clear out the window's built-in margin,
        // because we want to take advantage of the entire client area.
        Window.enableScrolling(false);
        Window.setMargin("0px");

        RootLayoutPanel.get().add(main);
    }

    public void onFinish(IUploader uploader) {
        // DO SOMETHING on done...

    }
}
