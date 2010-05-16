package com.jeebook.appengine.gtd.server.service;

import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import com.google.appengine.api.users.User;
import com.jeebook.appengine.gtd.server.model.Project;
import com.jeebook.appengine.gtd.server.model.ProjectValue;
import com.jeebook.appengine.gtd.server.persistence.JdoUtils;

@SuppressWarnings("serial")
public class ProjectServlet extends BaseServlet {
	
	@Override
	protected String New(User user, String json) {

		ProjectValue value = ProjectValue.fromJson(json);
		Project project = Project.fromValue(user, value);

		//
		PersistenceManager pm = JdoUtils.getPm();
		try {
			project = pm.makePersistent(project);
		} finally {
			JdoUtils.closePm();
		}

		value = project.toValue();
		return value.toJson();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String Get(User user) {
		PersistenceManager pm = JdoUtils.getPm();
		Query query = pm.newQuery(Project.class);
		query.setFilter("mUser == user");
	    query.declareParameters(user.getClass().getName() + " user");
		List<Project> projects = (List<Project>)query.execute(user);
		List<ProjectValue> values = Project.toValue(projects);
		return ProjectValue.toJson(values);
	}

	@Override
	protected String Get(String id) {
		PersistenceManager pm = JdoUtils.getPm();
		Project project = pm.getObjectById(Project.class, id);
		List<ProjectValue> values = new ArrayList<ProjectValue>();
		values.add(project.toValue());
		return ProjectValue.toJson(values);
	}

	@Override
	protected String Delete(String id) {
		PersistenceManager pm = JdoUtils.getPm();
		Project project = null;
		try {
			project = pm.getObjectById(Project.class, id);
			pm.deletePersistent(project);
		} finally {
			JdoUtils.closePm();
		}
		
		return project.toValue().toJson();
	}
	
	@Override
	protected void Modify(String json) {
		ProjectValue value = ProjectValue.fromJson(json);

		//
		PersistenceManager pm = JdoUtils.getPm();
		try {
			Project project = pm.getObjectById(Project.class, value.getId());
			project.setName(value.getName());
		} finally {
			JdoUtils.closePm();
		}
	}
}
