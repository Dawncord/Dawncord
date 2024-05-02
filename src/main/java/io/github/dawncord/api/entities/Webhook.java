package io.github.dawncord.api.entities;

import io.github.dawncord.api.action.WebhookModifyAction;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.image.Avatar;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.types.WebhookType;

import java.util.function.Consumer;

/**
 * Interface representing a webhook entity.
 */
public interface Webhook extends ISnowflake {

    /**
     * Retrieves the name of the webhook.
     *
     * @return The name of the webhook.
     */
    String getName();

    /**
     * Retrieves the guild associated with the webhook.
     *
     * @return The guild associated with the webhook.
     */
    Guild getGuild();

    /**
     * Retrieves the channel associated with the webhook.
     *
     * @return The channel associated with the webhook.
     */
    GuildChannel getChannel();

    /**
     * Retrieves the user associated with the webhook.
     *
     * @return The user associated with the webhook.
     */
    User getUser();

    /**
     * Retrieves the avatar of the webhook.
     *
     * @return The avatar of the webhook.
     */
    Avatar getAvatar();

    /**
     * Retrieves the token of the webhook.
     *
     * @return The token of the webhook.
     */
    String getToken();

    /**
     * Retrieves the application ID of the webhook.
     *
     * @return The application ID of the webhook.
     */
    String getApplicationId();

    /**
     * Retrieves the type of the webhook.
     *
     * @return The type of the webhook.
     */
    WebhookType getType();

    /**
     * Modifies the webhook using the provided consumer.
     *
     * @param handler The consumer to handle the modification of the webhook.
     * @return The modify event of the webhook.
     */
    ModifyEvent<Webhook> modify(Consumer<WebhookModifyAction> handler);

    /**
     * Deletes the webhook.
     */
    void delete();
}
