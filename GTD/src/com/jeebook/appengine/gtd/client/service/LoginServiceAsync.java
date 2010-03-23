package com.jeebook.appengine.gtd.client.service;


import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jeebook.appengine.gtd.client.model.LoginInfo;

public interface LoginServiceAsync {
  public void login(String requestUri, AsyncCallback<LoginInfo> async);
}
