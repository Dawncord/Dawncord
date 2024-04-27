package org.dimas4ek.dawncord.event;

import org.dimas4ek.dawncord.entities.Webhook;
import org.dimas4ek.dawncord.entities.guild.Guild;

public class WebhookUpdateEvent implements Event {
    private final Guild guild;
    private final Webhook webhook;

    public WebhookUpdateEvent(Guild guild, Webhook webhook) {
        this.guild = guild;
        this.webhook = webhook;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    public Webhook getWebhook() {
        return webhook;
    }
}
