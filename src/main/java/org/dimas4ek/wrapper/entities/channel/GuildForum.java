package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.action.ThreadCreateAction;
import org.dimas4ek.wrapper.entities.ForumTag;
import org.dimas4ek.wrapper.entities.channel.thread.Thread;
import org.dimas4ek.wrapper.types.ForumLayoutType;
import org.dimas4ek.wrapper.types.OrderType;

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

    void startThread(String name, Consumer<ThreadCreateAction> handler);

    void startThread(String name);
}
