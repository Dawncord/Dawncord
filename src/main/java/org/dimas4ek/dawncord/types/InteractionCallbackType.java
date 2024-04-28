package org.dimas4ek.dawncord.types;

public enum InteractionCallbackType {
    PONG(1, "ACK a Ping"),
    CHANNEL_MESSAGE_WITH_SOURCE(4, "respond to an interaction with a message"),
    DEFERRED_CHANNEL_MESSAGE_WITH_SOURCE(5, "ACK an interaction and edit a response later, the user sees a loading state"),
    DEFERRED_UPDATE_MESSAGE(6, "for components, ACK an interaction and edit the original message later; the user does not see a loading state"),
    UPDATE_MESSAGE(7, "for components, edit the message the component was attached to"),
    APPLICATION_COMMAND_AUTOCOMPLETE_RESULT(8, "respond to an autocomplete interaction with suggested choices"),
    MODAL(9, "respond to an interaction with a popup modal"),
    PREMIUM_REQUIRED(10, "respond to an interaction with an upgrade button, only available for apps with monetization enabled");

    private final int value;
    private final String description;

    InteractionCallbackType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
