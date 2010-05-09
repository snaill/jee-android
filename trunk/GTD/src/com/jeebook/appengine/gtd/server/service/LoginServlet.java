package com.jeebook.appengine.gtd.server.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
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
				jo.put("url", userService.createLogoutURL("/index.html"));				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Write(jo.toString(), resp);
	}
}