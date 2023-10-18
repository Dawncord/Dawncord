package org.dimas4ek.wrapper.entities.role;

import org.json.JSONObject;

public class Tags {
    private final JSONObject tags;

    public Tags(JSONObject tags) {
        this.tags = tags;
    }

    public String getBotId() {
        return tags.getString("bot_id");
    }

    public long getBotIdLong() {
        return Long.parseLong(getBotId());
    }

    public String getIntegrationId() {
        return tags.getString("integration_id");
    }

    public long getIntegrationIdLong() {
        return Long.parseLong(getIntegrationId());
    }

    public String getSubscriptionId() {
        return tags.getString("subscription_listing_id");
    }

    public long getSubscriptionIdLong() {
        return Long.parseLong(getSubscriptionId());
    }
}
