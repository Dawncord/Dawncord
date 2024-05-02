package io.github.dawncord.api.types;

/**
 * Represents the types of webhooks in Discord.
 */
public enum WebhookType {
    /**
     * Incoming Webhooks can post messages to channels with a generated token.
     */
    INCOMING(1),

    /**
     * Channel Follower Webhooks are internal webhooks used with Channel Following to post new messages into channels.
     */
    CHANNEL(2),

    /**
     * Application webhooks are webhooks used with Interactions.
     */
    APPLICATION(3);

    private final int value;

    WebhookType(int value) {
        this.value = value;
    }

    /**
     * Gets the value representing the webhook type.
     *
     * @return The value representing the webhook type.
     */
    public int getValue() {
        return value;
    }
}
