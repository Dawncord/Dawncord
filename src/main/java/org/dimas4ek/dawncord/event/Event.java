package org.dimas4ek.dawncord.event;

import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.action.ApplicationModifyAction;
import org.dimas4ek.dawncord.entities.User;
import org.dimas4ek.dawncord.entities.UserImpl;
import org.dimas4ek.dawncord.entities.Webhook;
import org.dimas4ek.dawncord.entities.WebhookImpl;
import org.dimas4ek.dawncord.entities.application.Application;
import org.dimas4ek.dawncord.entities.application.ApplicationImpl;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.utils.ActionExecutor;
import org.dimas4ek.dawncord.utils.JsonUtils;

import java.util.function.Consumer;

public interface Event {
    Guild getGuild();

    default Application getApplication() {
        return new ApplicationImpl(JsonUtils.fetch(Routes.Application()), getGuild());
    }

    default ModifyEvent<Application> editApplication(Consumer<ApplicationModifyAction> handler) {
        ActionExecutor.modifyApplication(handler);
        return new ModifyEvent<>(getApplication());
    }

    default Webhook getWebhookById(String webhookId) {
        return new WebhookImpl(JsonUtils.fetch(Routes.Webhook.ById(webhookId)), getGuild());
    }

    default Webhook getWebhookById(long webhookId) {
        return getWebhookById(String.valueOf(webhookId));
    }

    default Webhook getWebhookByToken(String webhookId, String webhookToken) {
        return new WebhookImpl(JsonUtils.fetch(Routes.Webhook.ByToken(webhookId, webhookToken)), getGuild());
    }

    default User getBot() {
        return new UserImpl(JsonUtils.fetch(Routes.User("@me")));
    }
}
