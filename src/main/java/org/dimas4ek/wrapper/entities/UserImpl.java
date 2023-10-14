package org.dimas4ek.wrapper.entities;

import org.dimas4ek.wrapper.Constants;

public class UserImpl implements User{
    private final String userId;

    public UserImpl(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean isBot() {
        return Constants.APPLICATION_ID.equals(userId);
    }
}
