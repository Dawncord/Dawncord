package io.github.dawncord.api.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.ThreadCreateAction;
import io.github.dawncord.api.entities.ForumTag;
import io.github.dawncord.api.entities.channel.thread.Thread;
import io.github.dawncord.api.entities.channel.thread.ThreadImpl;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.event.CreateEvent;
import io.github.dawncord.api.types.ForumLayoutType;
import io.github.dawncord.api.types.OrderType;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.EnumUtils;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Represents an implementation of a guild forum channel.
 */
public class GuildForumImpl extends ChannelImpl implements GuildForum {
    private final JsonNode forum;
    private final Guild guild;

    /**
     * Constructs a GuildForumImpl object with the specified JSON representation of the forum and its guild.
     *
     * @param forum The JSON representation of the guild forum.
     * @param guild The guild to which the forum belongs.
     */
    public GuildForumImpl(JsonNode forum, Guild guild) {
        super(forum, guild);
        this.forum = forum;
        this.guild = guild;
    }

    @Override
    public String getTopic() {
        return forum.get("topic").asText();
    }

    @Override
    public List<ForumTag> getTags() {
        List<ForumTag> tags = new ArrayList<>();
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
        if (forum.has("default_reaction_emoji")) {
            JsonNode emojiNode = forum.get("default_reaction_emoji");
            return emojiNode.hasNonNull("emoji_id") ? emojiNode.get("emoji_id").asText() : emojiNode.get("emoji_name").asText();
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
        if (forum.has("last_message_id")) {
            return new ThreadImpl(JsonUtils.fetch(Routes.Channel.Get(forum.get("last_message_id").asText())), guild);
        }
        return null;
    }

    @Override
    public GuildCategory getCategory() {
        if (forum.has("parent_id")) {
            return guild.getCategoryById(forum.get("parent_id").asText());
        }
        return null;
    }

    @Override
    public CreateEvent<Thread> startThread(String name, Consumer<ThreadCreateAction> handler) {
        String id = ActionExecutor.startForumThread(handler, this, name);
        return new CreateEvent<>(guild.getThreadById(id));
    }
}
