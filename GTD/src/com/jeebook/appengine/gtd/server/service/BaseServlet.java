package com.jeebook.appengine.gtd.server.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class BaseServlet extends HttpServlet {
	
	protected Object ReadJson(HttpServletRequest req)
	{
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)req.getInputStream()));
	        String line = null;
	        while((line = br.readLine())!=null){
	            sb.append(line);
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return JSONObject.stringToValue(sb.toString());
	}
	
	protected void WriteJson(Object o, HttpServletResponse resp)
	{
        JSONObject jo = new JSONObject(o);
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
