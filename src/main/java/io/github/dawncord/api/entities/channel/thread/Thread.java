package io.github.dawncord.api.entities.channel.thread;

import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.channel.MessageChannel;

import java.util.List;

/**
 * Represents a thread, a distinct conversation within a guild channel.
 * Threads can be either public or private, and they are a form of message channel.
 */
public interface Thread extends MessageChannel {

    /**
     * Retrieves the guild channel to which this thread belongs.
     *
     * @return The guild channel.
     */
    GuildChannel getChannel();

    /**
     * Retrieves the creator of this thread.
     *
     * @return The user who created the thread.
     */
    User getCreator();

    /**
     * Retrieves metadata associated with this thread.
     *
     * @return The metadata of the thread.
     */
    ThreadMetadata getMetaData();

    /**
     * Retrieves a list of members participating in this thread.
     *
     * @return The list of thread members.
     */
    List<ThreadMember> getThreadMembers();

    /**
     * Retrieves the thread member associated with the specified user ID.
     *
     * @param userId The ID of the user.
     * @return The thread member with the specified user ID.
     */
    ThreadMember getThreadMemberById(String userId);

    /**
     * Retrieves the thread member associated with the specified user ID.
     *
     * @param userId The ID of the user.
     * @return The thread member with the specified user ID.
     */
    ThreadMember getThreadMemberById(long userId);

    /**
     * Joins the thread.
     */
    void join();

    /**
     * Joins the thread with the specified user ID.
     *
     * @param userId The ID of the user to join the thread.
     */
    void join(String userId);

    /**
     * Joins the thread with the specified user ID.
     *
     * @param userId The ID of the user to join the thread.
     */
    void join(long userId);

    /**
     * Leaves the thread.
     */
    void leave();

    /**
     * Leaves the thread with the specified user ID.
     *
     * @param userId The ID of the user to leave the thread.
     */
    void leave(String userId);

    /**
     * Leaves the thread with the specified user ID.
     *
     * @param userId The ID of the user to leave the thread.
     */
    void leave(long userId);
}
