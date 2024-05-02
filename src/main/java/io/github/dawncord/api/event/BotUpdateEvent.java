package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.User;

/**
 * Represents an event indicating an update related to a bot user.
 */
public class BotUpdateEvent {

    private final User user;

    /**
     * Constructs a BotUpdateEvent with the specified bot user.
     *
     * @param user The bot user associated with the event.
     */
    public BotUpdateEvent(User user) {
        this.user = user;
    }

    /**
     * Retrieves the bot user associated with the event.
     *
     * @return The bot user associated with the event.
     */
    public User getBot() {
        return user;
    }
}
