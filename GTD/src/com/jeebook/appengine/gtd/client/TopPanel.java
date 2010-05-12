/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.jeebook.appengine.gtd.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.jeebook.appengine.gtd.client.service.AjaxRequest;

/**
 * The top panel, which contains the 'welcome' message and various links.
 */
public class TopPanel extends Composite {

	private static TopPanelUiBinder uiBinder = GWT.create(TopPanelUiBinder.class);

	interface TopPanelUiBinder extends UiBinder<Widget, TopPanel> {
	}

  @UiField SpanElement emailSpan;
  @UiField Anchor loginLink;
  @UiField Anchor newActionLink;
  @UiField Anchor reportLink;
  
  public TopPanel() {
    initWidget(uiBinder.createAndBindUi(this));
    
    new AjaxRequest(RequestBuilder.GET, "login") {
    	
    	@Override
    	public void onSuccess(String response){
    		JSONObject jo = JSONParser.parse(response).isObject();
       	  	emailSpan.setInnerText(jo.get("email").isString().stringValue());
       	  	loginLink.setText("Sign out");
       	  	loginLink.setHref(jo.get("url").isString().stringValue());
    	}
    }.send(null);
  }

  @UiHandler("newActionLink")
	void onNewActionClick(ClickEvent e) {
	  NewActionDialog dlg = new NewActionDialog();
	  dlg.show();
	  dlg.center();
	}
}