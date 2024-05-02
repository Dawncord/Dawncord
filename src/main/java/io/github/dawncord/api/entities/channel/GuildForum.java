package io.github.dawncord.api.entities.channel;

import io.github.dawncord.api.action.ThreadCreateAction;
import io.github.dawncord.api.entities.ForumTag;
import io.github.dawncord.api.entities.channel.thread.Thread;
import io.github.dawncord.api.event.CreateEvent;
import io.github.dawncord.api.types.ForumLayoutType;
import io.github.dawncord.api.types.OrderType;

import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a forum channel within a guild.
 */
public interface GuildForum extends Channel {

    /**
     * Gets the topic of the forum.
     *
     * @return The topic of the forum.
     */
    String getTopic();

    /**
     * Gets the tags associated with this forum.
     *
     * @return The tags associated with this forum.
     */
    List<ForumTag> getTags();

    /**
     * Gets a tag by its ID.
     *
     * @param tagId The ID of the tag.
     * @return The tag with the specified ID.
     */
    ForumTag getTagById(String tagId);

    /**
     * Gets a tag by its ID.
     *
     * @param tagId The ID of the tag.
     * @return The tag with the specified ID.
     */
    ForumTag getTagById(long tagId);

    /**
     * Gets a tag by its name.
     *
     * @param tagName The name of the tag.
     * @return The tag with the specified name.
     */
    ForumTag getTagByName(String tagName);

    /**
     * Gets tags associated with a specific emoji.
     *
     * @param emojiIdOrName The ID or name of the emoji.
     * @return The tags associated with the specified emoji.
     */
    List<ForumTag> getTagsByEmoji(String emojiIdOrName);

    /**
     * Gets the default reaction emoji for threads in this forum.
     *
     * @return The default reaction emoji.
     */
    String getDefaultReactionEmoji();

    /**
     * Gets the sort order of threads in this forum.
     *
     * @return The sort order of threads.
     */
    OrderType getSortOrder();

    /**
     * Gets the layout type of the forum.
     *
     * @return The layout type of the forum.
     */
    ForumLayoutType getForumLayout();

    /**
     * Gets the last thread posted in this forum.
     *
     * @return The last thread posted in this forum.
     */
    Thread getLastThread();

    /**
     * Gets the category to which this forum belongs.
     *
     * @return The category to which this forum belongs.
     */
    GuildCategory getCategory();

    /**
     * Starts a new thread in this forum.
     *
     * @param name    The name of the new thread.
     * @param handler The handler for configuring the thread creation.
     * @return An event representing the creation of the new thread.
     */
    CreateEvent<Thread> startThread(String name, Consumer<ThreadCreateAction> handler);
}
