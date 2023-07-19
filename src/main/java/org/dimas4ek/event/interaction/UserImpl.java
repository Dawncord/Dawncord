package org.dimas4ek.event.interaction;

import org.dimas4ek.enities.user.User;
import org.json.JSONObject;

public class UserImpl implements User {
    private final JSONObject user;
    
    public UserImpl(JSONObject user) {
        this.user = user;
    }
    
    @Override
    public String getId() {
        return user.getString("id");
    }
    
    @Override
    public String getUsername() {
        return user.getString("username");
    }
    
    @Override
    public String getDisplayName() {
        return user.getString("display_name");
    }
}
