package com.jeebook.appengine.gtd.server.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class BaseServlet extends HttpServlet {

	protected String Get(String id) { return null; }
	protected String Get(User user) { return null; }
	protected String New(User user, String json) { return null; }
	protected String Delete(String id) { return null; }
	protected void Modify(String json) { }
	
	 @Override
	protected  void	doGet(HttpServletRequest req, HttpServletResponse resp) 
	{
		 //
		 User user = checkUser(resp);
		 if ( null == user )
			 return;
    	
		 //
		 String id = getId(req);
		 String json;
		 if ( id.isEmpty() )
			 json = Get(user);
		 else
			 json = Get(id);
		 
		 Write(json, resp);
	}
	
	 @Override
	protected  void	doPost(HttpServletRequest req, HttpServletResponse resp) 
	{
		 //
		 User user = checkUser(resp);
		 if ( null == user )
			 return;
		
		//
		String	json = Read(req);
        json = New(user, json);
		
		//
		Write(json, resp);
	}
	
	 @Override
	protected  void	doDelete(HttpServletRequest req, HttpServletResponse resp) 
	{
		 //
		 User user = checkUser(resp);
		 if ( null == user )
			 return;
		
		//
		String id = getId(req);
		String jo = Delete(id);
		
		//
		Write(jo, resp);
	}
	
	 @Override
	protected  void	doPut(HttpServletRequest req, HttpServletResponse resp) 
	{
		 //
		 User user = checkUser(resp);
		 if ( null == user )
			 return;
		
		//
		String	json = Read(req);
        Modify(json);
	}

	protected String Read(HttpServletRequest req)
	{
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)req.getInputStream()));
	        String line = null;
	        while((line = br.readLine())!=null){
	            sb.append(line);
	        }
		    return sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	protected void Write(String jo, HttpServletResponse resp)
	{
        PrintWriter out;
		try {
			out = resp.getWriter();
	        out.write(jo);
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected String getId( HttpServletRequest req ){
		String id = req.getPathInfo();
		if ( id.startsWith("/"))
			id = id.substring(1);
		return id;
	}
	
    protected User checkUser(HttpServletResponse resp) {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
    	if ( null == user )
    	{
    		try {
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, userService.createLoginURL("/Shuffle.html"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		return null;
    	}
    	
    	return user;
    }
}
