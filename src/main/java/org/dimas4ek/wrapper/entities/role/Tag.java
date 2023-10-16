package org.dimas4ek.wrapper.entities.role;

import org.json.JSONObject;

public class Tag {
    private final JSONObject tag;

    public Tag(JSONObject tag) {
        this.tag = tag;
    }

    public String getBotId() {
        return tag.getString("bot_id");
    }

    public String getIntegrationId() {
        return tag.getString("integration_id");
    }

    public String getSubscriptionListingId() {
        return tag.getString("subscription_listing_id");
    }
}
