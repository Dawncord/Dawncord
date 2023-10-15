package org.dimas4ek.wrapper.entities;

import org.dimas4ek.wrapper.Constants;
import org.json.JSONObject;

public class UserImpl implements User{
    private final JSONObject user;

    public UserImpl(JSONObject user) {
        this.user = user;
    }

    @Override
    public boolean isBot() {
        return Constants.APPLICATION_ID.equals(user.getString("id"));
    }

    @Override
    public String getId() {
        return user.getString("id");
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }
}
