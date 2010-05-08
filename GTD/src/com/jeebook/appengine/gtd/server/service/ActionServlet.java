package com.jeebook.appengine.gtd.server.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.users.User;
import com.jeebook.appengine.gtd.server.model.Context;
import com.jeebook.appengine.gtd.server.persistence.JdoUtils;

public class ActionServlet extends BaseServlet {
	
	protected JSONObject New(User user, JSONObject jo) {         
		Context context = new Context();
	//	context.setKey(JdoUtils.getPm().)
		context.setUser(user);
		try {
			context.setName(jo.get("name").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		//
	    PersistenceManager pm = JdoUtils.getPm();
	    try {
	
	        pm.makePersistent(context);
	    } finally {
	        JdoUtils.closePm();
	    }
	    
	    return null;
  }
	
	protected  void	doGet(HttpServletRequest req, HttpServletResponse resp) 
	{
/*		if ( false == checkUser(resp) )
			return;
		
		String id = req.getPathInfo();
		if ( id == null )
		{
	        PersistenceManager pm = JdoUtils.getPm();
	        Query query = pm.newQuery(Project.class);
	        List<Project> projs = (List<Context>)query.execute();
	        Project proj = pm.getObjectById(Project.class, id);
			JSONArray	ja = new JSONArray(projs);
	        PrintWriter out;
			try {
				out = resp.getWriter();
		        out.write(ja.toString());
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else
		{
	        PersistenceManager pm = JdoUtils.getPm();
	        Project proj = pm.getObjectById(Project.class, id);
	        JSONObject jo = new JSONObject(proj);
	        PrintWriter out;
			try {
				out = resp.getWriter();
		        out.write(jo.toString());
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
*/	}

	protected  void	doDelete(HttpServletRequest req, HttpServletResponse resp) 
	{
		String id = req.getPathInfo();
		if ( id == null )
			return;
		
	    PersistenceManager pm = JdoUtils.getPm();
        Context proj = null;
        try {
        	proj = pm.getObjectById(Context.class, id);
            pm.deletePersistent(proj);
        } finally {
            JdoUtils.closePm();
        }
	}
		
	protected  void	doPut(HttpServletRequest req, HttpServletResponse resp) 
	{
		String id = req.getPathInfo();

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
		Context proj = (Context)JSONObject.stringToValue(sb.toString());

        //
        PersistenceManager pm = JdoUtils.getPm();
        try {
        	Context proj2 = pm.getObjectById(Context.class, id);
	
        } finally {
            JdoUtils.closePm();
        }		
	}

}
