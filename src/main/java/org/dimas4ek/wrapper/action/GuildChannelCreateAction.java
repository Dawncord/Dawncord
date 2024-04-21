package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.entities.ForumTag;
import org.dimas4ek.wrapper.entities.PermissionOverride;
import org.dimas4ek.wrapper.types.*;
import org.dimas4ek.wrapper.utils.MessageUtils;

import java.util.List;

public class GuildChannelCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private final ChannelType channelType;
    private boolean hasChanges = false;

    public GuildChannelCreateAction(String guildId, ChannelType channelType) {
        this.guildId = guildId;
        this.channelType = channelType;
        this.jsonObject = mapper.createObjectNode();
    }

    private GuildChannelCreateAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    public GuildChannelCreateAction setName(String name) {
        return setProperty("name", name);
    }

    public GuildChannelCreateAction setTopic(String topic) {
        if (isTextChannelType() || isAnnouncementChannelType() || isForumMediaChannelType()) {
            setProperty("topic", topic);
        }
        return this;
    }

    public GuildChannelCreateAction setBitrate(int bitrate) {
        if (isVoiceChannelType()) {
            setProperty("bitrate", bitrate);
        }
        return this;
    }

    public GuildChannelCreateAction setUserLimit(int userLimit) {
        if (isVoiceChannelType()) {
            setProperty("user_limit", userLimit);
        }
        return this;
    }

    public GuildChannelCreateAction setRateLimit(int rateLimit) {
        if (isTextChannelType() || isVoiceChannelType() || isForumMediaChannelType()) {
            setProperty("rate_limit_per_user", rateLimit);
        }
        return this;
    }

    public GuildChannelCreateAction setPosition(int position) {
        return setProperty("position", position);
    }

    public GuildChannelCreateAction setPermissionOverrides(PermissionOverride... permissionOverrides) {
        if (permissionOverrides != null && permissionOverrides.length > 0) {
            ArrayNode jsonArray = mapper.createArrayNode();
            for (PermissionOverride permissionOverride : permissionOverrides) {
                ObjectNode override = mapper.createObjectNode();
                override.put("id", permissionOverride.getId());
                override.put("type", permissionOverride.getType().getValue());
                override.put("deny", permissionOverride.getDenied() != null && !permissionOverride.getDenied().isEmpty()
                        ? String.valueOf(permissionOverride.getDenied().stream()
                        .mapToLong(PermissionType::getValue)
                        .reduce(0L, (x, y) -> x | y))
                        : "0");
                override.put("allow", permissionOverride.getAllowed() != null && !permissionOverride.getAllowed().isEmpty()
                        ? String.valueOf(permissionOverride.getAllowed().stream()
                        .mapToLong(PermissionType::getValue)
                        .reduce(0L, (x, y) -> x | y))
                        : "0");
                jsonArray.add(override);
            }
            jsonObject.set("permission_overwrites", jsonArray);
        }
        return this;
    }

    public GuildChannelCreateAction setParentId(String parentId) {
        if (isTextChannelType() || isVoiceChannelType() || isAnnouncementChannelType() || isForumMediaChannelType()) {
            setProperty("parent_id", parentId);
        }
        return this;
    }

    public GuildChannelCreateAction setParentId(long parentId) {
        return setParentId(String.valueOf(parentId));
    }

    public GuildChannelCreateAction setNsfw(boolean nsfw) {
        if (isTextChannelType() || isVoiceChannelType() || isAnnouncementChannelType() || channelType == ChannelType.GUILD_FORUM) {
            setProperty("nsfw", nsfw);
        }
        return this;
    }

    public GuildChannelCreateAction setVoiceRegion(VoiceRegion rtcRegion) {
        if (isVoiceChannelType()) {
            setProperty("rtc_region", rtcRegion.getValue());
        }
        return this;
    }

    public GuildChannelCreateAction setVideoQuality(VideoQualityMode videoQualityMode) {
        if (isVoiceChannelType()) {
            setProperty("video_quality_mode", videoQualityMode.getValue());
        }
        return this;
    }

    public GuildChannelCreateAction setDefaultReaction(String emojiIdOrName) {
        if (isForumMediaChannelType()) {
            ObjectNode reaction = mapper.createObjectNode();
            reaction.put("emoji_id", MessageUtils.isEmojiLong(emojiIdOrName) ? emojiIdOrName : null);
            reaction.put("emoji_name", MessageUtils.isEmojiLong(emojiIdOrName) ? null : emojiIdOrName);
            setProperty("default_reaction_emoji", reaction);
        }
        return this;
    }

    public GuildChannelCreateAction setTags(List<ForumTag> tags) {
        if (isForumMediaChannelType()) {
            setForumTags(tags);
        }
        return this;
    }

    public GuildChannelCreateAction setPostsOrder(OrderType type) {
        if (isForumMediaChannelType()) {
            setProperty("default_sort_order", type.getValue());
        }
        return this;
    }

    public GuildChannelCreateAction setForumLayout(ForumLayoutType type) {
        if (channelType == ChannelType.GUILD_FORUM) {
            setProperty("default_forum_layout", type.getValue());
        }
        return this;
    }

    public GuildChannelCreateAction setThreadRateLimit(int rateLimit) {
        if (isTextChannelType() || isAnnouncementChannelType() || isForumMediaChannelType()) {
            setProperty("rate_limit_per_user", rateLimit);
        }
        return this;
    }

    private void setForumTags(List<ForumTag> tags) {
        ArrayNode array = mapper.createArrayNode();
        for (ForumTag tag : tags) {
            ObjectNode tagJson = mapper.createObjectNode();
            tagJson.put("name", tag.getName());
            tagJson.put("moderated", tag.isModerated());
            tagJson.put("emoji_id", MessageUtils.isEmojiLong(tag.getEmojiIdOrName()) ? tag.getEmojiIdOrName() : null);
            tagJson.put("emoji_name", !MessageUtils.isEmojiLong(tag.getEmojiIdOrName()) ? tag.getEmojiIdOrName() : null);
            array.add(tagJson);
        }
        setProperty("available_tags", array);
    }

    private boolean isTextChannelType() {
        return channelType == ChannelType.GUILD_TEXT;
    }

    private boolean isForumMediaChannelType() {
        return channelType == ChannelType.GUILD_FORUM || channelType == ChannelType.GUILD_MEDIA;
    }

    private boolean isAnnouncementChannelType() {
        return channelType == ChannelType.GUILD_ANNOUNCEMENT;
    }

    private boolean isVoiceChannelType() {
        return channelType == ChannelType.GUILD_VOICE || channelType == ChannelType.GUILD_STAGE_VOICE;
    }

    private JsonNode getJsonObject() {
        return jsonObject;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.post(jsonObject, Routes.Guild.Channels(guildId));
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
