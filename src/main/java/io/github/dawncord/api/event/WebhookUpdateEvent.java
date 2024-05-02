package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.Webhook;
import io.github.dawncord.api.entities.guild.Guild;

/**
 * Represents an event triggered when a webhook is updated in a guild.
 */
public class WebhookUpdateEvent implements Event {
    private final Guild guild;
    private final Webhook webhook;

    /**
     * Constructs a WebhookUpdateEvent with the specified guild and webhook.
     *
     * @param guild   The guild associated with the webhook update event.
     * @param webhook The updated webhook.
     */
    public WebhookUpdateEvent(Guild guild, Webhook webhook) {
        this.guild = guild;
        this.webhook = webhook;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    /**
     * Retrieves the updated webhook.
     *
     * @return The updated webhook.
     */
    public Webhook getWebhook() {
        return webhook;
    }
}
