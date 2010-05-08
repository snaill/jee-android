package com.jeebook.appengine.gtd.server.model;

import java.io.Serializable;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Context implements Serializable {
   
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    
    @Persistent
    private User mUser;
    
    @Persistent
	private String mName;
	
	public final long getKey() {
		return id;
	}

	public final User getUser() {
	    return mUser;
	}
	
	public final String getName() {
		return mName;
	}

	public final void setUser( User user ) {
	    mUser = user;
	}
	
	public final void setName( String name ) {
		mName = name;
	}
}
