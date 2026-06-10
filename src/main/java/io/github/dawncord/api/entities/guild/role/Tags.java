package io.github.dawncord.api.entities.guild.role;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.integration.Integration;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.LazyLoader;

/**
 * Represents the tags associated with a role.
 */
public class Tags {
    private final LazyLoader loader;
    private final JsonNode tags;
    private final Guild guild;
    private User bot;
    private Integration integration;
    private String subscriptionId;

    /**
     * Constructs a new Tags object with the provided JSON node.
     *
     * @param tags The JSON node representing the tags.
     */
    public Tags(JsonNode tags, Guild guild) {
        this.tags = tags;
        this.guild = guild;
        loader = new LazyLoader(tags);
    }

    /**
     * Retrieves the bot from the tags.
     *
     * @return The bot, or null if not present.
     */
    public User getBot() {
        bot = loader.loadIfExists(bot, "bot_id",
                () -> new User(JsonUtils.fetch(Routes.User(tags.get("bot_id").asText()))));
        return bot;
    }

    /**
     * Retrieves the integration from the tags.
     *
     * @return The integration, or null if not present.
     */
    public Integration getIntegration() {
        integration = loader.loadIfExists(integration, "integration_id",
                () -> new Integration(
                        JsonUtils.fetch(Routes.Guild.Integration.Get(guild.getId(), tags.get("integration_id").asText())),
                        guild)
        );
        return integration;
    }

    /**
     * Retrieves the subscription ID from the tags.
     *
     * @return The subscription ID, or null if not present.
     */
    //todo rewrite using SKU
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
