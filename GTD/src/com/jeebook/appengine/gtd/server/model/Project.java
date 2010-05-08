package com.jeebook.appengine.gtd.server.model;

import static com.jeebook.appengine.gtd.server.persistence.JdoUtils.toKey;
import static com.jeebook.appengine.gtd.server.persistence.JdoUtils.toKeyValue;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import com.jeebook.appengine.gtd.client.model.ContextValue;
import com.jeebook.appengine.gtd.client.model.KeyValue;
import com.jeebook.appengine.gtd.client.model.ProjectValue;
import com.jeebook.appengine.gtd.client.model.ProjectValue.Builder;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Project implements Serializable {
    
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    
    @Persistent
    private User mUser;
    
    @Persistent
    private String mName;
    
    @Persistent
    private Key mDefaultContextKey;
    
    
    public final long getKey() {
        return id;
    }
    
    public final User getUser() {
        return mUser;
    }

    public final String getName() {
        return mName;
    }

    public final Key getDefaultContextKey() {
        return mDefaultContextKey;
    }    
}

