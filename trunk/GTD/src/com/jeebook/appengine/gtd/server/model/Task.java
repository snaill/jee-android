package com.jeebook.appengine.gtd.server.model;

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Task {

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
    private Long mProjectKey;
    
    @Persistent
    private Long mContextKey;
    
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

	public final Long getProjectId() {
		return mProjectKey;
	}

	public final Long getContextIds() {
		return mContextKey;
	}

	public final Date getDueDate() {
		return mDueDate;
	}

	public final Date getFinishDate() {
		return mFinishDate;
	}
}
