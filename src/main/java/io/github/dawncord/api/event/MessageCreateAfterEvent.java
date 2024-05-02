package io.github.dawncord.api.event;

import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.MessageModifyAction;
import io.github.dawncord.api.entities.guild.GuildImpl;
import io.github.dawncord.api.entities.message.Message;
import io.github.dawncord.api.entities.message.MessageImpl;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.function.Consumer;

/**
 * Represents an event after the creation of a message.
 */
public class MessageCreateAfterEvent {
    private final String channelId;
    private final String messageId;
    private final String guildId;

    /**
     * Constructs a MessageCreateAfterEvent with the specified channel ID, message ID, and guild ID.
     *
     * @param channelId The ID of the channel where the message was created.
     * @param messageId The ID of the created message.
     * @param guildId   The ID of the guild where the message was created.
     */
    public MessageCreateAfterEvent(String channelId, String messageId, String guildId) {
        this.channelId = channelId;
        this.messageId = messageId;
        this.guildId = guildId;
    }

    /**
     * Retrieves the created message.
     *
     * @return The created message.
     */
    public Message get() {
        return new MessageImpl(
                JsonUtils.fetch(Routes.Channel.Message.Get(channelId, messageId)),
                new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(guildId)))
        );
    }

    /**
     * Modifies the created message.
     *
     * @param handler The consumer to handle the modification of the message.
     */
    public void edit(Consumer<MessageModifyAction> handler) {
        ActionExecutor.modifyMessage(handler, get(), null);
    }

    /**
     * Deletes the created message.
     */
    public void delete() {
        ApiClient.delete(Routes.Channel.Message.Get(channelId, messageId));
    }
}
