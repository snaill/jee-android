package com.jeebook.appengine.gtd.server.model;

import java.io.Serializable;
import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import com.jeebook.appengine.gtd.server.persistence.JdoUtils;

public class Component<T, TJson> implements Serializable {

		public TJson toJson(T task)
		{
			return null;
		}
}
