package com.jeebook.appengine.gtd.server.service;

import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
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

	@SuppressWarnings("unchecked")
	@Override
	protected String Get(User user) {
		PersistenceManager pm = JdoUtils.getPm();
		Query query = pm.newQuery(Context.class);
		query.setFilter("mUser == user");
	    query.declareParameters(user.getClass().getName() + " user");
		List<Context> contexts = (List<Context>)query.execute(user);
		List<ContextValue> values = Context.toValue(contexts);
		return ContextValue.toJson(values);
	}

	@Override
	protected String Get(String id) {
		PersistenceManager pm = JdoUtils.getPm();
		Context context = pm.getObjectById(Context.class, id);
		List<ContextValue> values = new ArrayList<ContextValue>();
		values.add(context.toValue());
		return ContextValue.toJson(values);
	}

	@Override
	protected String Delete(String id) {
		PersistenceManager pm = JdoUtils.getPm();
		Context context = null;
		try {
			context = pm.getObjectById(Context.class, id);
			pm.deletePersistent(context);
		} finally {
			JdoUtils.closePm();
		}
		
		return context.toValue().toJson();
	}
	
	@Override
	protected void Modify(String json) {
		ContextValue value = ContextValue.fromJson(json);

		//
		PersistenceManager pm = JdoUtils.getPm();
		try {
			Context context = pm.getObjectById(Context.class, value.getId());
			context.setName(value.getName());
		} finally {
			JdoUtils.closePm();
		}
	}
}
