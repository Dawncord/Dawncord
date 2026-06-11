package io.github.dawncord.api.types;

/**
 * Represents the status of application event webhooks.
 */
public enum EventWebhookStatus {
    DISABLED(1, "Webhook events are disabled by developer"),
    ENABLED(2, "Webhook events are enabled by developer"),
    DISABLED_BY_DISCORD(3, "Webhook events are disabled by Discord, usually due to inactivity");

    private final int value;
    private final String description;

    EventWebhookStatus(int value, String description) {
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
