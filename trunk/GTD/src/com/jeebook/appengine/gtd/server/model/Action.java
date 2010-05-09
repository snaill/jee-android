package com.jeebook.appengine.gtd.server.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Action {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long mId;
    
    @Persistent
    private User mUser;
    
    @Persistent
    private String mName;

    @Persistent
    private String mDetails;

    @Persistent
    private Long mProjectId;
    
    @Persistent
    private Long mContextId;
    
    @Persistent
    private Date mDueDate;

    @Persistent
    private Date mFinishDate;
    
    public final long getId() {
        return mId;
    }

    public final User getUser() {
        return mUser;
    }
    
	public final String getName() {
		return mName;
	}

	public final String getDetails() {
		return mDetails;
	}

	public final Long getProjectId() {
		return mProjectId;
	}

	public final Long getContextId() {
		return mContextId;
	}

	public final Date getDueDate() {
		return mDueDate;
	}

	public final Date getFinishDate() {
		return mFinishDate;
	}

    public final void setId( long id ) {
        mId = id;
    }

    public final void setUser( User user ) {
        mUser = user;
    }
    
	public final void setName( String name ) {
		mName = name;
	}

	public final void setDetails( String details ) {
		mDetails = details;
	}

	public final void setProjectId( long id ) {
		mProjectId = id;
	}

	public final void setContextId( long id ) {
		mContextId = id;
	}

	public final void setDueDate( Date date ) {
		mDueDate = date;
	}

	public final void setFinishDate( Date date ) {
		mFinishDate = date;
	}
	
	public static Action fromValue( User user, ActionValue value ) {
		Action action = new Action();
		action.setId(value.getId());
		action.setName(value.getName());
		action.setUser(user);
		action.setDetails(value.getDetails());
		action.setProjectId(value.getProjectId());
		action.setContextId(value.getContextId());
		action.setDueDate(value.getDueDate());
		action.setFinishDate(value.getFinishDate());
		return action;
	}
	
	public static List<ActionValue> toValue( List<Action> actions ) {
        List<ActionValue> values = new ArrayList<ActionValue>();
        for ( int i = 0; i < actions.size(); i ++ ) {
        	values.add(actions.get(i).toValue());
        }
        return values;
	}
	
	public ActionValue toValue() {
		ActionValue value = new ActionValue();
		value.setId(getId());
		value.setName(getName());
		value.setDetails(getDetails());
		value.setProjectId(getProjectId());
		value.setContextId(getContextId());
		value.setDueDate(getDueDate());
		value.setFinishDate(getFinishDate());
		return value;
	}
}
