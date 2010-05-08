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

public class BaseServlet extends HttpServlet {

	protected JSONObject Get(User user, String pathInfo) { return null; }
	protected JSONObject New(User user, JSONObject jo) { return null; }
	protected JSONObject Delete(User user, String pathInfo) { return null; }
	protected JSONObject Modify(User user, JSONObject jo) {	return null; }
	
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
		JSONObject jo = Get(user, pi);
		
		//
		if ( jo == null )
		{
			resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return;
		}
		
		//
		WriteJson(jo, resp);
	}
	
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
    		WriteJson(jo, resp);
    		return;
    	}
		
		//
		JSONObject	jo = ReadJson(req);
        jo = New(user, jo);
		
        //
		if ( jo == null )
		{
			resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return;
		}
		
		//
		WriteJson(jo, resp);
	}
	
	protected  void	doDelete(HttpServletRequest req, HttpServletResponse resp) 
	{
		//
		if ( false == checkUser(resp) )
			return;
		
		//
		String pi = req.getPathInfo();
		JSONObject jo = Delete(getUser(), pi);
		
		//
		if ( jo == null )
		{
			resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return;
		}
		
		//
		WriteJson(jo, resp);
	}
		
	protected  void	doPut(HttpServletRequest req, HttpServletResponse resp) 
	{
		//
		if ( false == checkUser(resp) )
			return;
		
		//
		JSONObject	jo = ReadJson(req);
        jo = Modify(getUser(), jo);
		
        //
		if ( jo == null )
		{
			resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return;
		}
		
		//
		WriteJson(jo, resp);
	}

	
	protected JSONObject ReadJson(HttpServletRequest req)
	{
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)req.getInputStream()));
	        String line = null;
	        while((line = br.readLine())!=null){
	            sb.append(line);
	        }
		    return new JSONObject(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	protected void WriteJson(JSONObject jo, HttpServletResponse resp)
	{
        PrintWriter out;
		try {
			out = resp.getWriter();
	        out.write(jo.toString());
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
