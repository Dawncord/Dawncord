package org.dimas4ek.dawncord.types;

public enum WebhookType {
    Incoming(1, "Incoming Webhooks can post messages to channels with a generated token"),
    Channel(2, "Follower	Channel Follower Webhooks are internal webhooks used with Channel Following to post new messages into channels"),
    Application(3, "Application webhooks are webhooks used with Interactions");

    private final int value;
    private final String description;

    WebhookType(int value, String description) {
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
