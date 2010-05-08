package com.jeebook.appengine.gtd.server.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.*;

import com.google.appengine.api.users.User;
import com.jeebook.appengine.gtd.server.model.Context;
import com.jeebook.appengine.gtd.server.persistence.JdoUtils;

public class ContextServlet extends BaseServlet {
	
	 @Override
	protected String New(User user, String json) {    
		
		JSONObject jo;
		Context context = new Context();
		context.setUser(user);
		try {
			jo = new JSONObject(json);
			context.setName(jo.get("name").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		//
	    PersistenceManager pm = JdoUtils.getPm();
	    try {
	        context = pm.makePersistent(context);
	    } finally {
	        JdoUtils.closePm();
	    }
	    
	    jo = new JSONObject(context);
	    return jo.toString();
  }
	 @Override
	@SuppressWarnings("unchecked")
	protected String Get(User user, String id) { 
	 	
		JSONArray	ja = null;
		if ( id != "/" )
		{
	        PersistenceManager pm = JdoUtils.getPm();
	        Query query = pm.newQuery(Context.class);
	        List<Context> projs = (List<Context>)query.execute();
			ja = new JSONArray(projs);
		}
		else
		{
	        PersistenceManager pm = JdoUtils.getPm();
	        Context proj = pm.getObjectById(Context.class, id);
			try {
				ja = new JSONArray(new JSONObject(proj));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ja.toString(); 
	}
	
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
