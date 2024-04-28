package org.dimas4ek.dawncord.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.action.ThreadCreateAction;
import org.dimas4ek.dawncord.entities.ForumTag;
import org.dimas4ek.dawncord.entities.channel.thread.Thread;
import org.dimas4ek.dawncord.entities.channel.thread.ThreadImpl;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.event.CreateEvent;
import org.dimas4ek.dawncord.types.ForumLayoutType;
import org.dimas4ek.dawncord.types.OrderType;
import org.dimas4ek.dawncord.utils.ActionExecutor;
import org.dimas4ek.dawncord.utils.EnumUtils;
import org.dimas4ek.dawncord.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GuildForumImpl extends ChannelImpl implements GuildForum {
    private final JsonNode forum;
    private final Guild guild;

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

    @Override
    public void startThread(String name) {
        startThread(name, null);
    }
}
