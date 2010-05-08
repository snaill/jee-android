package com.jeebook.appengine.gtd.server.service;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class LoginServlet extends BaseServlet {	

	protected  void	doGet(HttpServletRequest req, HttpServletResponse resp) 
	{
		//
	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
		JSONObject jo = new JSONObject();
		try {
	    	if ( null == user )
	    	{
	    		resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				jo.put("url", userService.createLoginURL("/Shuffle.html"));
	    	}
			else
			{
				jo.put("nikename", user.getNickname());
				jo.put("email", user.getEmail());
				jo.put("url", userService.createLogoutURL("/Shuffle.html"));				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WriteJson(jo, resp);
	}
}