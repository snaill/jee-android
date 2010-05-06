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
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Button;
import com.google.inject.Inject;
import com.jeebook.appengine.gtd.client.InboxPanel.InboxPanelUiBinder;
import com.jeebook.appengine.gtd.client.model.LoginInfo;
import com.jeebook.appengine.gtd.client.service.LoginServiceAsync;

/**
 * The top panel, which contains the 'welcome' message and various links.
 */
public class TopPanel extends Composite {

	private static TopPanelUiBinder uiBinder = GWT.create(TopPanelUiBinder.class);

	interface TopPanelUiBinder extends UiBinder<Widget, TopPanel> {
	}

  @Inject ShuffleConstants constants;
  
  @UiField SpanElement emailSpan;
  @UiField Anchor loginLink;
  @UiField Anchor settingsLink;
  @UiField Anchor newActionLink;
  @UiField Anchor newProjectLink;
  @UiField Anchor newContextLink;
  @UiField Anchor reportLink;
  
  @Inject
  public TopPanel(LoginServiceAsync loginService, final LoginInfo loginInfo) {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @UiHandler("newActionLink")
	void onNewActionClick(ClickEvent e) {
			new NewActionDialog().show();
	}
  
  @UiHandler("newProjectLink")
	void onNewProjectClick(ClickEvent e) {
			new NewProjectDialog().show();
	}
  
  @UiHandler("newContextLink")
	void onNewContextClick(ClickEvent e) {
			new NewContextDialog().show();
	}
}
