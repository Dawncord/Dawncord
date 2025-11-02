package io.github.dawncord.api.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.ThreadCreateAction;
import io.github.dawncord.api.entities.ForumTag;
import io.github.dawncord.api.entities.channel.thread.Thread;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.event.CreateEvent;
import io.github.dawncord.api.types.ForumLayoutType;
import io.github.dawncord.api.types.OrderType;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.LazyLoader;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Represents an implementation of a guild forum channel.
 */
public class GuildForum extends Channel {
    private final LazyLoader loader;
    private final JsonNode forum;
    private final Guild guild;
    private List<ForumTag> tags;
    private String defaultReactionEmoji;
    private OrderType defaultSortOrder;
    private ForumLayoutType defaultForumLayout;
    private Thread thread;
    private GuildCategory category;

    /**
     * Constructs a GuildForumImpl object with the specified JSON representation of the forum and its guild.
     *
     * @param forum The JSON representation of the guild forum.
     * @param guild The guild to which the forum belongs.
     */
    public GuildForum(JsonNode forum, Guild guild) {
        super(forum, guild);
        this.forum = forum;
        this.guild = guild;
        loader = new LazyLoader(forum);
    }

    public String getTopic() {
        return forum.get("topic").asText();
    }

    public List<ForumTag> getTags() {
        tags = loader.load(tags, () -> {
            tags = JsonUtils.getEntityList(forum.get("available_tags"), tag -> {
                String emojiId = tag.hasNonNull("emoji_id") ? tag.get("emoji_id").asText() : tag.get("emoji_name").asText();
                return new ForumTag(
                        tag.get("id").asText(),
                        tag.get("name").asText(),
                        tag.get("moderated").asBoolean(),
                        emojiId
                );
            });
            return tags;
        });
        return tags;
    }

    public ForumTag getTagById(String tagId) {
        return getTags().stream().filter(tag -> tag.getId().equals(tagId)).findAny().orElse(null);
    }

    public ForumTag getTagById(long tagId) {
        return getTagById(String.valueOf(tagId));
    }

    public ForumTag getTagByName(String tagName) {
        return getTags().stream().filter(tag -> tag.getName().equals(tagName)).findAny().orElse(null);
    }

    public List<ForumTag> getTagsByEmoji(String emojiIdOrName) {
        return getTags().stream().filter(tag -> tag.getEmojiIdOrName().equals(emojiIdOrName)).collect(Collectors.toList());
    }

    public String getDefaultReactionEmoji() {
        defaultReactionEmoji = loader.load(defaultReactionEmoji, () -> {
            JsonNode emojiNode = forum.get("default_reaction_emoji");
            defaultReactionEmoji = emojiNode.hasNonNull("emoji_id") ? emojiNode.get("emoji_id").asText() : emojiNode.get("emoji_name").asText();
            return defaultReactionEmoji;
        });
        return defaultReactionEmoji;
    }

    public OrderType getSortOrder() {
        defaultSortOrder = loader.loadEnumObject(defaultSortOrder, "default_sort_order", OrderType.class);
        return defaultSortOrder;
    }

    public ForumLayoutType getForumLayout() {
        defaultForumLayout = loader.loadEnumObject(defaultForumLayout, "default_forum_layout", ForumLayoutType.class);
        return defaultForumLayout;
    }

    public Thread getThread() {
        thread = loader.loadIfExists(thread, "last_message_id", () -> new Thread(JsonUtils.fetch(Routes.Channel.Get(forum.get("last_message_id").asText())), guild));
        return thread;
    }

    public GuildCategory getCategory() {
        category = loader.load(category, () -> guild.getCategoryById(forum.get("parent_id").asText()));
        return category;
    }

    public CreateEvent<Thread> startThread(String name, Consumer<ThreadCreateAction> handler) {
        String id = ActionExecutor.startForumThread(handler, this, name);
        return new CreateEvent<>(guild.getThreadById(id));
    }
}
