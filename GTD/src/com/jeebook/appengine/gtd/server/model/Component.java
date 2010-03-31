package com.jeebook.appengine.gtd.server.model;

import static com.jeebook.appengine.gtd.server.persistence.JdoUtils.toKey;
import static com.jeebook.appengine.gtd.server.persistence.JdoUtils.toKeyValue;

import java.io.Serializable;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import com.jeebook.appengine.gtd.client.model.ContextValue;
import com.jeebook.appengine.gtd.client.model.KeyValue;
import com.jeebook.appengine.gtd.client.model.ContextValue.Builder;
import com.jeebook.appengine.gtd.server.persistence.JdoUtils;

public class Component<T, TJson> implements Serializable {

		public TJson Get(){
			return null;
		}
		
		public TJson New( User user, TJson tv ){
	        PersistenceManager pm = JdoUtils.getPm();
	        T task = fromJson(user, tv);
	        try {
	            task = pm.makePersistent(task);
	        } finally {
	            JdoUtils.closePm();
	        }
	        return toJson();
		}
		
		public void Delete(){
			
		}
		
		public TJson toJson()
		{
			return null;
		}
		
		public T fromJson(User user, TJson tv)
		{
			return null;
		}
	}
