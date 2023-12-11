package org.dimas4ek.wrapper.entities.guild.role;

import com.fasterxml.jackson.databind.JsonNode;

public class Tags {
    private final JsonNode tags;
    private String botId;
    private String integrationId;
    private String subscriptionId;

    public Tags(JsonNode tags) {
        this.tags = tags;
    }

    public String getBotId() {
        if (botId == null) {
            botId = tags.has("bot_id") ? tags.get("bot_id").asText() : null;
        }
        return botId;
    }

    public long getBotIdLong() {
        return Long.parseLong(getBotId());
    }

    public String getIntegrationId() {
        if (integrationId == null) {
            integrationId = tags.has("integration_id") ? tags.get("integration_id").asText() : null;
        }
        return integrationId;
    }

    public long getIntegrationIdLong() {
        return Long.parseLong(getIntegrationId());
    }

    public String getSubscriptionId() {
        if (subscriptionId == null) {
            subscriptionId = tags.has("subscription_listing_id") ? tags.get("subscription_listing_id").asText() : null;
        }
        return subscriptionId;
    }

    public long getSubscriptionIdLong() {
        return Long.parseLong(getSubscriptionId());
    }
}
