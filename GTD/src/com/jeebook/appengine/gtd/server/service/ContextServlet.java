package com.jeebook.appengine.gtd.server.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.*;

import com.google.appengine.api.users.User;
import com.jeebook.appengine.gtd.server.model.Context;
import com.jeebook.appengine.gtd.server.model.ContextValue;
import com.jeebook.appengine.gtd.server.persistence.JdoUtils;

@SuppressWarnings("serial")
public class ContextServlet extends BaseServlet {
	
	 @Override
	protected String New(User user, String json) {    
		
		 ContextValue value = ContextValue.fromJson(json);
		 Context context = Context.fromValue(user, value);
	
		//
		PersistenceManager pm = JdoUtils.getPm();
		try {
		    context = pm.makePersistent(context);
		} finally {
		    JdoUtils.closePm();
		}
		
		value = context.toValue();
		return value.toJson();
	}
	 
	 @Override
	@SuppressWarnings("unchecked")
	protected String Get(User user, String id) { 
	 	
		if ( id != "/" )
		{
	        PersistenceManager pm = JdoUtils.getPm();
	        Query query = pm.newQuery(Context.class);
	        List<Context> contexts = (List<Context>)query.execute();
	        List<ContextValue> values = Context.toValue(contexts);
	        return ContextValue.toJson(values);
		}
		else
		{
	        PersistenceManager pm = JdoUtils.getPm();
	        Context context = pm.getObjectById(Context.class, id);
	        List<ContextValue> values = new ArrayList<ContextValue>();
	        values.add(context.toValue());
	        return ContextValue.toJson(values);
		}
	}
	
	protected  void	doDelete2(HttpServletRequest req, HttpServletResponse resp) 
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
		
	protected  void	doPut2(HttpServletRequest req, HttpServletResponse resp) 
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
