package com.jeebook.appengine.gtd.server.model;

import static com.jeebook.appengine.gtd.server.persistence.JdoUtils.toKey;
import static com.jeebook.appengine.gtd.server.persistence.JdoUtils.toKeyValue;
import static com.jeebook.appengine.gtd.server.persistence.JdoUtils.toKeyValues;
import static com.jeebook.appengine.gtd.server.persistence.JdoUtils.toKeys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;
import com.jeebook.appengine.gtd.client.model.ContextValue;
import com.jeebook.appengine.gtd.client.model.KeyValue;
import com.jeebook.appengine.gtd.client.model.ProjectValue;
import com.jeebook.appengine.gtd.client.model.TaskValue;
import com.jeebook.appengine.gtd.client.model.TaskValue.Builder;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Task extends Component<Task, TaskValue> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long mId;
    
    @Persistent
    private User user;
    
    @Persistent
    private String mTitle;

    @Persistent
    private Text mDetails;

    @Persistent
    private Key mProjectKey;
    
    @Persistent
    private List<Key> mContextKeys;
    
    @Persistent
    private Date mDueDate;

    @Persistent
    private Date mFinishDate;
    
    public final long getKey() {
        return mId;
    }

    public final User getUser() {
        return user;
    }
    
	public final String getTitle() {
		return mTitle;
	}

	public final Text getDetails() {
		return mDetails;
	}

	public final Key getProjectId() {
		return mProjectKey;
	}

	public final List<Key> getContextIds() {
		return mContextKeys;
	}

	public final Date getDueDate() {
		return mDueDate;
	}

	public final Date getFinishDate() {
		return mFinishDate;
	}
}
