package com.jeebook.appengine.gtd.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.users.User;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Project implements Serializable {
    
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long mId;
    
    @Persistent
    private User mUser;
    
    @Persistent
    private String mName;
    
    @Persistent
    private Long mDefaultContextId;
    
    public final Long getId() {
        return mId;
    }
    
    public final User getUser() {
        return mUser;
    }

    public final String getName() {
        return mName;
    }

    public final Long getDefaultContextId() {
        return mDefaultContextId;
    }  
    
    public final void setId( Long id ) {
        mId = id;
    }
    
    public final void setUser( User user ) {
        mUser = user;
    }

    public final void setName( String name ) {
        mName = name;
    }

    public final void setDefaultContextId( Long id ) {
        mDefaultContextId = id;
    } 
    
	public static Project fromValue( User user, ProjectValue value ) {
		Project project = new Project();
		if ( null != value.getId() )
			project.setId(Long.parseLong(value.getId()));
		project.setName(value.getName());
		project.setUser(user);
		if ( null != value.getDefaultContextId() )
			project.setDefaultContextId(Long.parseLong(value.getDefaultContextId()));
		return project;
	}
	
	public static List<ProjectValue> toValue( List<Project> projects ) {
        List<ProjectValue> values = new ArrayList<ProjectValue>();
        for ( int i = 0; i < projects.size(); i ++ ) {
        	values.add(projects.get(i).toValue());
        }
        return values;
	}
	
	public ProjectValue toValue() {
		ProjectValue value = new ProjectValue();
		value.setId(getId().toString());
		value.setName(getName());
		value.setDefaultContextId(getDefaultContextId().toString());
		return value;
	}
}

