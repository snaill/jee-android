package com.jeebook.appengine.gtd.server.service;

import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import com.google.appengine.api.users.User;
import com.jeebook.appengine.gtd.server.model.Action;
import com.jeebook.appengine.gtd.server.model.ActionValue;
import com.jeebook.appengine.gtd.server.persistence.JdoUtils;

@SuppressWarnings("serial")
public class ActionServlet extends BaseServlet {
	
	@Override
	protected String New(User user, String json) {

		ActionValue value = ActionValue.fromJson(json);
		Action action = Action.fromValue(user, value);

		//
		PersistenceManager pm = JdoUtils.getPm();
		try {
			action = pm.makePersistent(action);
		} finally {
			JdoUtils.closePm();
		}

		value = action.toValue();
		return value.toJson();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String Get(User user) {
		PersistenceManager pm = JdoUtils.getPm();
		Query query = pm.newQuery(Action.class);
		query.setFilter("mUser == user");
	    query.declareParameters(user.getClass().getName() + " user");
		List<Action> actions = (List<Action>)query.execute(user);
		List<ActionValue> values = Action.toValue(actions);
		return ActionValue.toJson(values);
	}

	@Override
	protected String Get(String id) {
		PersistenceManager pm = JdoUtils.getPm();
		Action action = pm.getObjectById(Action.class, id);
		List<ActionValue> values = new ArrayList<ActionValue>();
		values.add(action.toValue());
		return ActionValue.toJson(values);
	}

	@Override
	protected String Delete(String id) {
		PersistenceManager pm = JdoUtils.getPm();
		Action action = null;
		try {
			action = pm.getObjectById(Action.class, id);
			pm.deletePersistent(action);
		} finally {
			JdoUtils.closePm();
		}
		
		return action.toValue().toJson();
	}
	
	@Override
	protected void Modify(String json) {
		ActionValue value = ActionValue.fromJson(json);

		//
		PersistenceManager pm = JdoUtils.getPm();
		try {
			Action action = pm.getObjectById(Action.class, value.getId());
			action.setName(value.getName());
		} finally {
			JdoUtils.closePm();
		}
	}

}
