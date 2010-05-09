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
public class Context implements Serializable {
   
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long mId;
    
    @Persistent
    private User mUser;
    
    @Persistent
	private String mName;
	
	public final long getKey() {
		return mId;
	}

	public final User getUser() {
	    return mUser;
	}
	
	public final String getName() {
		return mName;
	}
	
	public final void setKey( long id ) {
		mId = id;;
	}

	public final void setUser( User user ) {
	    mUser = user;
	}
	
	public final void setName( String name ) {
		mName = name;
	}
	
	public static Context fromValue( User user, ContextValue value ) {
		Context context = new Context();
		context.setKey(value.getKey());
		context.setName(value.getName());
		context.setUser(user);
		return context;
	}
	
	public static List<ContextValue> toValue( List<Context> contexts ) {
        List<ContextValue> values = new ArrayList<ContextValue>();
        for ( int i = 0; i < contexts.size(); i ++ ) {
        	values.add(contexts.get(i).toValue());
        }
        return values;
	}
	
	public ContextValue toValue() {
		ContextValue value = new ContextValue();
		value.setKey(getKey());
		value.setName(getName());
		return value;
	}
}
