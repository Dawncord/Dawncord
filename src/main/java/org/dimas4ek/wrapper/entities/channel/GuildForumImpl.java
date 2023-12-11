package org.dimas4ek.wrapper.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.action.ThreadCreateAction;
import org.dimas4ek.wrapper.entities.ForumTag;
import org.dimas4ek.wrapper.entities.channel.thread.Thread;
import org.dimas4ek.wrapper.entities.channel.thread.ThreadImpl;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.types.ForumLayoutType;
import org.dimas4ek.wrapper.types.OrderType;
import org.dimas4ek.wrapper.utils.ActionExecutor;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GuildForumImpl extends ChannelImpl implements GuildForum {
    private final JsonNode forum;
    private final Guild guild;
    private String topic;
    private List<ForumTag> tags;
    private String defaultReactionEmoji;
    private OrderType sortOrder;
    private ForumLayoutType forumLayout;
    private Thread lastThread;
    private GuildCategory category;

    public GuildForumImpl(JsonNode forum, Guild guild) {
        super(forum, guild);
        this.forum = forum;
        this.guild = guild;
    }

    @Override
    public String getTopic() {
        if (topic == null) {
            topic = forum.get("topic").asText();
        }
        return topic;
    }

    @Override
    public List<ForumTag> getTags() {
        if (tags == null) {
            if (forum.has("available_tags")) {
                tags = JsonUtils.getEntityList(forum.get("available_tags"), tag -> {
                    String emojiId = tag.hasNonNull("emoji_id") ? tag.get("emoji_id").asText() : tag.get("emoji_name").asText();
                    return new ForumTag(
                            tag.get("id").asText(),
                            tag.get("name").asText(),
                            tag.get("moderated").asBoolean(),
                            emojiId
                    );
                });
            }
        }
        return tags;
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
        if (defaultReactionEmoji == null) {
            if (forum.has("default_reaction_emoji")) {
                JsonNode emojiNode = forum.get("default_reaction_emoji");
                defaultReactionEmoji = emojiNode.hasNonNull("emoji_id") ? emojiNode.get("emoji_id").asText() : emojiNode.get("emoji_name").asText();
            }
        }
        return defaultReactionEmoji;
    }

    @Override
    public OrderType getSortOrder() {
        if (sortOrder == null) {
            sortOrder = EnumUtils.getEnumObject(forum, "default_sort_order", OrderType.class);
        }
        return sortOrder;
    }

    @Override
    public ForumLayoutType getForumLayout() {
        if (forumLayout == null) {
            forumLayout = EnumUtils.getEnumObject(forum, "default_forum_layout", ForumLayoutType.class);
        }
        return forumLayout;
    }

    @Override
    public Thread getLastThread() {
        if (lastThread == null) {
            if (forum.has("last_message_id")) {
                lastThread = new ThreadImpl(JsonUtils.fetchEntity("/channels/" + forum.get("last_message_id").asText()), guild);
            }
        }
        return lastThread;
    }

    @Override
    public GuildCategory getCategory() {
        if (category == null) {
            if (forum.has("parent_id")) {
                category = guild.getCategoryById(forum.get("parent_id").asText());
            }
        }
        return category;
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
