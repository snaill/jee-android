package com.jeebook.appengine.gtd.server.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.core.client.GWT;

@SuppressWarnings("serial")
public class BaseServlet extends HttpServlet {

	protected String Get(User user, String pathInfo) { return null; }
	protected String New(User user, String jo) { return null; }
	protected String Delete(User user, String pathInfo) { return null; }
	protected String Modify(User user, String jo) {	return null; }
	
	 @Override
	protected  void	doGet(HttpServletRequest req, HttpServletResponse resp) 
	{
		//
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
    	if ( null == user )
    	{
    		try {
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, userService.createLoginURL(GWT.getHostPageBaseURL()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		return;
    	}
    	
		//
		String pi = req.getPathInfo();
		String jo = Get(user, pi);
		
		//
		if ( jo == null || jo.isEmpty() )
		{
			resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return;
		}
		
		//
		Write(jo, resp);
	}
	
	 @Override
	protected  void	doPost(HttpServletRequest req, HttpServletResponse resp) 
	{
		//
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
    	if ( null == user )
    	{
    		resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    		
    		JSONObject jo = new JSONObject();
    		try {
				jo.put("url", userService.createLoginURL("Shuffle.html"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		Write(jo.toString(), resp);
    		return;
    	}
		
		//
		String	jo = Read(req);
        jo = New(user, jo);
		
        //
		if ( jo == null )
		{
			resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return;
		}
		
		//
		Write(jo, resp);
	}
	
	 @Override
	protected  void	doDelete(HttpServletRequest req, HttpServletResponse resp) 
	{
		//
		if ( false == checkUser(resp) )
			return;
		
		//
		String pi = req.getPathInfo();
		String jo = Delete(getUser(), pi);
		
		//
		if ( jo == null )
		{
			resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return;
		}
		
		//
		Write(jo, resp);
	}
	
	 @Override
	protected  void	doPut(HttpServletRequest req, HttpServletResponse resp) 
	{
		//
		if ( false == checkUser(resp) )
			return;
		
		//
		String	jo = Read(req);
        jo = Modify(getUser(), jo);
		
        //
		if ( jo == null )
		{
			resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return;
		}
		
		//
		Write(jo, resp);
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
	
    protected User getUser() {
        UserService userService = UserServiceFactory.getUserService();
        return userService.getCurrentUser();
    }
    
    protected boolean checkUser(HttpServletResponse resp) {
    	if ( null != getUser() )
    		return true;
    	
    	resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    	return false;
    }
}
