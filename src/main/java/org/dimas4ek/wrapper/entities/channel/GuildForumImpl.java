package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.ForumTag;
import org.dimas4ek.wrapper.action.ThreadCreateAction;
import org.dimas4ek.wrapper.entities.thread.Thread;
import org.dimas4ek.wrapper.entities.thread.ThreadImpl;
import org.dimas4ek.wrapper.types.ForumLayoutType;
import org.dimas4ek.wrapper.types.OrderType;
import org.dimas4ek.wrapper.utils.ActionExecutor;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GuildForumImpl extends ChannelImpl implements GuildForum {
    private final JSONObject forum;

    public GuildForumImpl(JSONObject forum) {
        super(forum);
        this.forum = forum;
    }

    @Override
    public String getTopic() {
        return forum.getString("topic");
    }

    @Override
    public List<ForumTag> getTags() {
        return forum.has("available_tags") ?
                JsonUtils.getEntityList(forum.getJSONArray("available_tags"), (JSONObject t) -> {
                    String emojiId = !t.isNull("emoji_id") ? t.getString("emoji_id") : t.getString("emoji_name");
                    return new ForumTag(
                            t.getString("id"),
                            t.getString("name"),
                            t.getBoolean("moderated"),
                            emojiId
                    );
                }) : Collections.emptyList();
    }

    @Override
    public ForumTag getTagById(String tagId) {
        return getTags().stream().filter(tag -> tag.getId().equals(tagId)).findAny().orElse(null);
    }

    @Override
    public ForumTag getTagById(long tagId) {
        return getTagById(String.valueOf(tagId));
    }

    @Override
    public ForumTag getTagByName(String tagName) {
        return getTags().stream().filter(tag -> tag.getName().equals(tagName)).findAny().orElse(null);
    }

    @Override
    public List<ForumTag> getTagsByEmoji(String emojiIdOrName) {
        return getTags().stream().filter(tag -> tag.getEmojiIdOrName().equals(emojiIdOrName)).collect(Collectors.toList());
    }


    @Override
    public String getDefaultReactionEmoji() {
        JSONObject defaultReactionEmoji = forum.optJSONObject("default_reaction_emoji");
        if (defaultReactionEmoji != null) {
            String emojiId = defaultReactionEmoji.optString("emoji_id");
            String emojiName = defaultReactionEmoji.optString("emoji_name");
            return emojiId != null ? emojiId : emojiName;
        }
        return null;
    }

    @Override
    public OrderType getSortOrder() {
        return EnumUtils.getEnumObject(forum, "default_sort_order", OrderType.class);
    }

    @Override
    public ForumLayoutType getForumLayout() {
        return EnumUtils.getEnumObject(forum, "default_forum_layout", ForumLayoutType.class);
    }

    @Override
    public Thread getLastThread() {
        return forum.has("last_message_id")
                ? new ThreadImpl(JsonUtils.fetchEntity("/channels/" + forum.getString("last_message_id")))
                : null;
    }

    @Override
    public GuildCategory getCategory() {
        return new GuildCategoryImpl(JsonUtils.fetchEntity("/channels/" + forum.getString("parent_id")));
    }

    @Override
    public void startThread(String name, Consumer<ThreadCreateAction> handler) {
        ActionExecutor.startForumThread(handler, this, name);
    }

    @Override
    public void startThread(String name) {
        startThread(name, null);
    }
}
