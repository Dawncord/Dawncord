package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.action.ApplicationModifyAction;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.entities.Webhook;
import org.dimas4ek.wrapper.entities.WebhookImpl;
import org.dimas4ek.wrapper.entities.application.Application;
import org.dimas4ek.wrapper.entities.application.ApplicationImpl;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.utils.ActionExecutor;
import org.dimas4ek.wrapper.utils.JsonUtils;

import java.util.function.Consumer;

public interface Event {
    Guild getGuild();

    default Application getApplication() {
        return new ApplicationImpl(JsonUtils.fetchEntity("/applications/@me"), getGuild());
    }

    default void editApplication(Consumer<ApplicationModifyAction> handler) {
        ActionExecutor.modifyApplication(handler);
    }

    default Webhook getWebhookById(String webhookId) {
        return new WebhookImpl(JsonUtils.fetchEntity("/webhooks/" + webhookId), getGuild());
    }

    default Webhook getWebhookById(long webhookId) {
        return getWebhookById(String.valueOf(webhookId));
    }

    default Webhook getWebhookByToken(String webhookId, String webhookToken) {
        return new WebhookImpl(JsonUtils.fetchEntity("/webhooks/" + webhookId + "/" + webhookToken), getGuild());
    }

    default User getBot() {
        return new UserImpl(JsonUtils.fetchEntity("/users/@me"));
    }
}
