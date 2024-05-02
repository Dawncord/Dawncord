package io.github.dawncord.api.event;

import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.ApplicationModifyAction;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.UserImpl;
import io.github.dawncord.api.entities.Webhook;
import io.github.dawncord.api.entities.WebhookImpl;
import io.github.dawncord.api.entities.application.Application;
import io.github.dawncord.api.entities.application.ApplicationImpl;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * Represents an event that occurs within a guild.
 */
public interface Event {

    /**
     * Retrieves the guild associated with the event.
     *
     * @return The guild associated with the event.
     */
    Guild getGuild();

    /**
     * Retrieves the application associated with the event.
     *
     * @return The application associated with the event.
     */
    default Application getApplication() {
        return new ApplicationImpl(JsonUtils.fetch(Routes.Application()), getGuild());
    }

    /**
     * Modifies the application associated with the event using the provided handler.
     *
     * @param handler The handler to modify the application.
     * @return The modify event for the application.
     */
    default ModifyEvent<Application> editApplication(Consumer<ApplicationModifyAction> handler) {
        ActionExecutor.modifyApplication(handler);
        return new ModifyEvent<>(getApplication());
    }

    /**
     * Retrieves the webhook with the specified ID associated with the event's guild.
     *
     * @param webhookId The ID of the webhook.
     * @return The webhook associated with the specified ID.
     */
    default Webhook getWebhookById(String webhookId) {
        return new WebhookImpl(JsonUtils.fetch(Routes.Webhook.ById(webhookId)), getGuild());
    }

    /**
     * Retrieves the webhook with the specified ID associated with the event's guild.
     *
     * @param webhookId The ID of the webhook.
     * @return The webhook associated with the specified ID.
     */
    default Webhook getWebhookById(long webhookId) {
        return getWebhookById(String.valueOf(webhookId));
    }

    /**
     * Retrieves the webhook with the specified ID and token associated with the event's guild.
     *
     * @param webhookId    The ID of the webhook.
     * @param webhookToken The token of the webhook.
     * @return The webhook associated with the specified ID and token.
     */
    default Webhook getWebhookByToken(String webhookId, String webhookToken) {
        return new WebhookImpl(JsonUtils.fetch(Routes.Webhook.ByToken(webhookId, webhookToken)), getGuild());
    }

    /**
     * Retrieves the bot user associated with the event.
     *
     * @return The bot user associated with the event.
     */
    default User getBot() {
        return new UserImpl(JsonUtils.fetch(Routes.User("@me")));
    }

    /**
     * The logger for logging event info.
     *
     * @return The logger for logging event info.
     */
    static Logger getLogger() {
        return LoggerFactory.getLogger(Event.class);
    }
}
