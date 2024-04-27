package org.dimas4ek.dawncord.entities.channel;

import org.dimas4ek.dawncord.action.ThreadCreateAction;
import org.dimas4ek.dawncord.entities.ForumTag;
import org.dimas4ek.dawncord.entities.channel.thread.Thread;
import org.dimas4ek.dawncord.event.CreateEvent;
import org.dimas4ek.dawncord.types.ForumLayoutType;
import org.dimas4ek.dawncord.types.OrderType;

import java.util.List;
import java.util.function.Consumer;

public interface GuildForum extends Channel {
    String getTopic();

    List<ForumTag> getTags();

    ForumTag getTagById(String tagId);

    ForumTag getTagById(long tagId);

    ForumTag getTagByName(String tagName);

    List<ForumTag> getTagsByEmoji(String emojiIdOrName);

    String getDefaultReactionEmoji();

    OrderType getSortOrder();

    ForumLayoutType getForumLayout();

    Thread getLastThread();

    GuildCategory getCategory();

    CreateEvent<Thread> startThread(String name, Consumer<ThreadCreateAction> handler);

    void startThread(String name);
}
