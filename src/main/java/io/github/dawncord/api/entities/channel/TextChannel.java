package io.github.dawncord.api.entities.channel;

import io.github.dawncord.api.action.MessageCreateAction;
import io.github.dawncord.api.event.MessageCreateEvent;

import java.util.function.Consumer;

/**
 * Represents a text channel for sending and receiving text-based messages.
 */
public interface TextChannel extends MessageChannel {

    /**
     * Sends a message to the channel with the specified content and provides a handler for message creation events.
     *
     * @param message The content of the message to send.
     * @param handler The handler for message creation events.
     * @return An event representing the creation of the message.
     */
    MessageCreateEvent sendMessage(String message, Consumer<MessageCreateAction> handler);

    /**
     * Sends a message to the channel with the specified content.
     *
     * @param message The content of the message to send.
     * @return An event representing the creation of the message.
     */
    MessageCreateEvent sendMessage(String message);

    /**
     * Sends a message to the channel and provides a handler for message creation events.
     *
     * @param handler The handler for message creation events.
     * @return An event representing the creation of the message.
     */
    MessageCreateEvent sendMessage(Consumer<MessageCreateAction> handler);

    /**
     * Retrieves the category to which this text channel belongs.
     *
     * @return The category to which this text channel belongs.
     */
    GuildCategory getCategory();
}
