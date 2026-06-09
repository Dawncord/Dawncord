package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.Webhook;
import io.github.dawncord.api.entities.guild.Guild;

/**
 * Represents an event triggered when a webhook is updated in a guild.
 */
public record WebhookUpdateEvent(Guild guild, Webhook webhook) implements Event {
    /**
     * Constructs a WebhookUpdateEvent with the specified guild and webhook.
     *
     * @param guild   The guild associated with the webhook update event.
     * @param webhook The updated webhook.
     */
    public WebhookUpdateEvent {
    }

    /**
     * Retrieves the updated webhook.
     *
     * @return The updated webhook.
     */
    @Override
    public Webhook webhook() {
        return webhook;
    }
}
