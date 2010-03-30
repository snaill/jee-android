package com.jeebook.appengine.gtd.server.service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.jeebook.appengine.gtd.client.service.NotLoggedInException;

public class Component<T>  extends HttpServlet {
	protected  void	doGet(HttpServletRequest req, HttpServletResponse resp) 
	{
		try{
		checkLoggedIn();
		} catch (Exception e ){
			
		}
		String id = req.getPathInfo();
	}
	
	protected  void	doPost(HttpServletRequest req, HttpServletResponse resp) 
	{
		
	}
	
	protected  void	doDelete(HttpServletRequest req, HttpServletResponse resp) 
	{
		
	}
		
	protected  void	doPut(HttpServletRequest req, HttpServletResponse resp) 
	{
		
	}
	
    private void checkLoggedIn() throws NotLoggedInException {
        if (getUser() == null) {
          throw new NotLoggedInException("Not logged in.");
        }
      }

    private User getUser() {
        UserService userService = UserServiceFactory.getUserService();
        return userService.getCurrentUser();
    }
}
