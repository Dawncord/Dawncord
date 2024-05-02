package io.github.dawncord.api.entities.channel;

import io.github.dawncord.api.action.MessageModifyAction;
import io.github.dawncord.api.action.PollCreateAction;
import io.github.dawncord.api.action.ThreadCreateAction;
import io.github.dawncord.api.entities.channel.thread.Thread;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.entities.message.Message;
import io.github.dawncord.api.entities.message.poll.Poll;
import io.github.dawncord.api.event.CreateEvent;
import io.github.dawncord.api.event.ModifyEvent;

import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a channel for sending and receiving messages.
 */
public interface MessageChannel extends Channel {

    /**
     * Retrieves all messages in the channel.
     *
     * @return A list of messages in the channel.
     */
    List<Message> getMessages();

    /**
     * Retrieves the last message sent in the channel.
     *
     * @return The last message sent in the channel.
     */
    Message getLastMessage();

    /**
     * Retrieves a message by its ID.
     *
     * @param messageId The ID of the message to retrieve.
     * @return The message with the specified ID.
     */
    Message getMessageById(String messageId);

    /**
     * Retrieves a message by its ID.
     *
     * @param messageId The ID of the message to retrieve.
     * @return The message with the specified ID.
     */
    Message getMessageById(long messageId);

    /**
     * Retrieves the rate limit for sending messages in the channel.
     *
     * @return The rate limit for sending messages.
     */
    int getRateLimit();

    /**
     * Checks if the channel is marked as NSFW (Not Safe For Work).
     *
     * @return True if the channel is NSFW, false otherwise.
     */
    boolean isNsfw();

    /**
     * Deletes a specified number of messages from the channel.
     *
     * @param count The number of messages to delete.
     */
    void deleteMessages(int count);

    /**
     * Sets the typing indicator in the channel.
     */
    void setTyping();

    /**
     * Retrieves all pinned messages in the channel.
     *
     * @return A list of pinned messages.
     */
    List<Message> getPinnedMessages();

    /**
     * Pins a message by its ID.
     *
     * @param messageId The ID of the message to pin.
     */
    void pinMessage(String messageId);

    /**
     * Pins a message by its ID.
     *
     * @param messageId The ID of the message to pin.
     */
    void pinMessage(long messageId);

    /**
     * Unpins a message by its ID.
     *
     * @param messageId The ID of the message to unpin.
     */
    void unpinMessage(String messageId);

    /**
     * Unpins a message by its ID.
     *
     * @param messageId The ID of the message to unpin.
     */
    void unpinMessage(long messageId);

    /**
     * Starts a new thread with the specified message ID and provides a handler for thread creation events.
     *
     * @param messageId The ID of the message to start the thread with.
     * @param handler   The handler for thread creation events.
     * @return An event representing the creation of the thread.
     */
    CreateEvent<Thread> startThread(String messageId, Consumer<ThreadCreateAction> handler);

    /**
     * Starts a new thread with the specified message ID and provides a handler for thread creation events.
     *
     * @param messageId The ID of the message to start the thread with.
     * @param handler   The handler for thread creation events.
     * @return An event representing the creation of the thread.
     */
    CreateEvent<Thread> startThread(long messageId, Consumer<ThreadCreateAction> handler);

    /**
     * Starts a new thread and provides a handler for thread creation events.
     *
     * @param handler The handler for thread creation events.
     * @return An event representing the creation of the thread.
     */
    CreateEvent<Thread> startThread(Consumer<ThreadCreateAction> handler);

    /**
     * Starts a new thread.
     *
     * @return An event representing the creation of the thread.
     */
    CreateEvent<Thread> startThread();

    /**
     * Modifies a message with the specified ID and provides a handler for message modification events.
     *
     * @param messageId The ID of the message to modify.
     * @param handler   The handler for message modification events.
     * @return An event representing the modification of the message.
     */
    ModifyEvent<Message> modifyMessageById(String messageId, Consumer<MessageModifyAction> handler);

    /**
     * Modifies a message with the specified ID and provides a handler for message modification events.
     *
     * @param messageId The ID of the message to modify.
     * @param handler   The handler for message modification events.
     * @return An event representing the modification of the message.
     */
    ModifyEvent<Message> modifyMessageById(long messageId, Consumer<MessageModifyAction> handler);

    /**
     * Creates a new poll and provides a handler for poll creation events.
     *
     * @param handler The handler for poll creation events.
     * @return An event representing the creation of the poll.
     */
    CreateEvent<Poll> createPoll(Consumer<PollCreateAction> handler);

    /**
     * Retrieves the poll associated with the specified message ID.
     *
     * @param messageId The ID of the message associated with the poll.
     * @return The poll associated with the specified message ID, or null if not found.
     */
    Poll getPoll(String messageId);

    /**
     * Retrieves the poll associated with the specified message ID.
     *
     * @param messageId The ID of the message associated with the poll.
     * @return The poll associated with the specified message ID, or null if not found.
     */
    Poll getPoll(long messageId);

    /**
     * Retrieves all polls in the channel.
     *
     * @return A list of all polls in the channel.
     */
    List<Poll> getPolls();

    /**
     * Retrieves all polls in the channel with the specified question.
     *
     * @param question The question of the polls to retrieve.
     * @return A list of polls with the specified question.
     */
    List<Poll> getPolls(String question);

    /**
     * Retrieves the voters of the specified poll answer in the specified message.
     *
     * @param messageId The ID of the message associated with the poll.
     * @param answerId  The ID of the answer in the poll.
     * @return A list of guild members who voted for the specified poll answer.
     */
    List<GuildMember> getPollVoters(String messageId, String answerId);
}
