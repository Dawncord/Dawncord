package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.entities.Webhook;
import org.dimas4ek.wrapper.entities.guild.Guild;

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
