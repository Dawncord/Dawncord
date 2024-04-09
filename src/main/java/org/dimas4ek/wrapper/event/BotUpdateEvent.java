package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.entities.User;

public class BotUpdateEvent {
    private final User user;

    public BotUpdateEvent(User user) {
        this.user = user;
    }

    public User getBot() {
        return user;
    }
}
