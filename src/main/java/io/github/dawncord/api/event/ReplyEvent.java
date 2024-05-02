package io.github.dawncord.api.event;

import io.github.dawncord.api.action.MessageCreateAction;
import io.github.dawncord.api.action.MessageModifyAction;
import io.github.dawncord.api.action.ModalCreateAction;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.entities.message.modal.Modal;

import java.util.function.Consumer;

/**
 * Represents an event that allows replying to interactions.
 */
public interface ReplyEvent extends Event {

    /**
     * Replies to the event with the specified message, optionally providing a handler for further modifications.
     *
     * @param message The message to reply with.
     * @param handler A handler for further modifications to the message.
     * @return The event representing the reply action.
     */
    CallbackEvent<MessageModifyAction> reply(String message, Consumer<MessageCreateAction> handler);

    /**
     * Replies to the event with the specified message.
     *
     * @param message The message to reply with.
     * @return The event representing the reply action.
     */
    CallbackEvent<MessageModifyAction> reply(String message);

    /**
     * Replies to the event, allowing further modifications with the provided handler.
     *
     * @param handler A handler for further modifications to the message.
     * @return The event representing the reply action.
     */
    CallbackEvent<MessageModifyAction> reply(Consumer<MessageCreateAction> handler);

    /**
     * Defers the reply to the event with an optional indication of message ephemeral status.
     *
     * @param ephemeral Indicates if the reply should be ephemeral (only visible to the user).
     * @return The event representing the deferred reply action.
     */
    CallbackEvent<MessageCreateAction> deferReply(boolean ephemeral);

    /**
     * Defers the reply to the event.
     *
     * @return The event representing the deferred reply action.
     */
    CallbackEvent<MessageCreateAction> deferReply();

    /**
     * Replies to the event with a modal, optionally providing a handler for further modifications.
     *
     * @param handler A handler for further modifications to the modal.
     * @return The event representing the reply action with a modal.
     */
    CallbackEvent<MessageModifyAction> replyModal(Consumer<ModalCreateAction> handler);

    /**
     * Replies to the event with the provided modal.
     *
     * @param modal The modal to reply with.
     * @return The event representing the reply action with the modal.
     */
    CallbackEvent<MessageModifyAction> replyModal(Modal modal);

    /**
     * Retrieves the member associated with the event.
     *
     * @return The member associated with the event.
     */
    GuildMember getMember();

    /**
     * Retrieves the channel associated with the event.
     *
     * @return The channel associated with the event.
     */
    GuildChannel getChannel();

    /**
     * Retrieves the channel associated with the specified channel ID.
     *
     * @param channelId The ID of the channel to retrieve.
     * @return The channel associated with the specified ID.
     */
    GuildChannel getChannelById(String channelId);

    /**
     * Retrieves the channel associated with the specified channel ID.
     *
     * @param channelId The ID of the channel to retrieve.
     * @return The channel associated with the specified ID.
     */
    GuildChannel getChannelById(long channelId);
}
