package io.github.dawncord.api.entities.guild.role;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Represents the tags associated with a role.
 */
public class Tags {

    private final JsonNode tags;
    private String botId;
    private String integrationId;
    private String subscriptionId;

    /**
     * Constructs a new Tags object with the provided JSON node.
     *
     * @param tags The JSON node representing the tags.
     */
    public Tags(JsonNode tags) {
        this.tags = tags;
    }

    /**
     * Retrieves the bot ID from the tags.
     *
     * @return The bot ID, or null if not present.
     */
    public String getBotId() {
        if (botId == null) {
            botId = tags.has("bot_id") ? tags.get("bot_id").asText() : null;
        }
        return botId;
    }

    /**
     * Retrieves the bot ID as a long value.
     *
     * @return The bot ID as a long value, or 0 if not present.
     */
    public long getBotIdLong() {
        return Long.parseLong(getBotId());
    }

    /**
     * Retrieves the integration ID from the tags.
     *
     * @return The integration ID, or null if not present.
     */
    public String getIntegrationId() {
        if (integrationId == null) {
            integrationId = tags.has("integration_id") ? tags.get("integration_id").asText() : null;
        }
        return integrationId;
    }

    /**
     * Retrieves the integration ID as a long value.
     *
     * @return The integration ID as a long value, or 0 if not present.
     */
    public long getIntegrationIdLong() {
        return Long.parseLong(getIntegrationId());
    }

    /**
     * Retrieves the subscription ID from the tags.
     *
     * @return The subscription ID, or null if not present.
     */
    public String getSubscriptionId() {
        if (subscriptionId == null) {
            subscriptionId = tags.has("subscription_listing_id") ? tags.get("subscription_listing_id").asText() : null;
        }
        return subscriptionId;
    }

    /**
     * Retrieves the subscription ID as a long value.
     *
     * @return The subscription ID as a long value, or 0 if not present.
     */
    public long getSubscriptionIdLong() {
        return Long.parseLong(getSubscriptionId());
    }
}
