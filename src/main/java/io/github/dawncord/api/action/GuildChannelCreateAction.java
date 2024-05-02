package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.ForumTag;
import io.github.dawncord.api.entities.PermissionOverride;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.types.*;
import io.github.dawncord.api.utils.MessageUtils;

import java.util.List;

/**
 * Represents an action for creating a guild channel.
 *
 * @see GuildChannel
 */
public class GuildChannelCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private final ChannelType channelType;
    private boolean hasChanges = false;
    private String createdId;

    /**
     * Create a new {@link GuildChannelCreateAction}
     *
     * @param guildId     The ID of the guild where the channel will be created.
     * @param channelType The type of the channel to be created.
     */
    public GuildChannelCreateAction(String guildId, ChannelType channelType) {
        this.guildId = guildId;
        this.channelType = channelType;
        this.jsonObject = mapper.createObjectNode();
    }

    /**
     * Create a new {@link GuildChannelCreateAction}
     */
    public GuildChannelCreateAction() {
        this.jsonObject = null;
        this.guildId = null;
        this.channelType = null;
    }

    private GuildChannelCreateAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    /**
     * Sets the name property for the guild channel.
     *
     * @param name the name to set
     * @return the modified GuildChannelCreateAction object
     */
    public GuildChannelCreateAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the topic for the guild channel. if the channel type is text, announcement, or forum media.
     *
     * @param topic the topic to set
     * @return the modified GuildChannelCreateAction object
     */
    public GuildChannelCreateAction setTopic(String topic) {
        if (isTextChannelType() || isAnnouncementChannelType() || isForumMediaChannelType()) {
            setProperty("topic", topic);
        }
        return this;
    }

    /**
     * Sets the bitrate for the guild channel. if the channel type is voice.
     *
     * @param bitrate the bitrate to set
     * @return the modified GuildChannelCreateAction object
     */
    public GuildChannelCreateAction setBitrate(int bitrate) {
        if (isVoiceChannelType()) {
            setProperty("bitrate", bitrate);
        }
        return this;
    }

    /**
     * Sets the user limit for the guild channel. if the channel type is voice.
     *
     * @param userLimit the user limit to set
     * @return the modified GuildChannelCreateAction object
     */
    public GuildChannelCreateAction setUserLimit(int userLimit) {
        if (isVoiceChannelType()) {
            setProperty("user_limit", userLimit);
        }
        return this;
    }

    /**
     * Sets the rate limit for the guild channel.
     *
     * @param rateLimit the rate limit to set
     * @return the modified GuildChannelCreateAction object
     */
    public GuildChannelCreateAction setRateLimit(int rateLimit) {
        if (isTextChannelType() || isVoiceChannelType() || isForumMediaChannelType()) {
            setProperty("rate_limit_per_user", rateLimit);
        }
        return this;
    }

    /**
     * Sets the position property for the guild channel.
     *
     * @param position the position to set
     * @return the modified GuildChannelCreateAction object
     */
    public GuildChannelCreateAction setPosition(int position) {
        return setProperty("position", position);
    }

    /**
     * Sets the permission overrides for the guild channel.
     *
     * @param permissionOverrides the permission overrides to set
     * @return the modified GuildChannelCreateAction object
     */
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

    /**
     * Sets the parent ID for the guild channel.
     *
     * @param parentId the ID of the parent channel
     * @return the modified GuildChannelCreateAction object
     */
    public GuildChannelCreateAction setParentId(String parentId) {
        if (isTextChannelType() || isVoiceChannelType() || isAnnouncementChannelType() || isForumMediaChannelType()) {
            setProperty("parent_id", parentId);
        }
        return this;
    }

    /**
     * Sets the parent ID for the guild channel.
     *
     * @param parentId the ID of the parent channel
     * @return the modified GuildChannelCreateAction object
     */
    public GuildChannelCreateAction setParentId(long parentId) {
        return setParentId(String.valueOf(parentId));
    }

    /**
     * Sets the NSFW (Not Safe For Work) flag for the guild channel if applicable.
     * This flag indicates whether the channel is intended for NSFW content.
     *
     * @param nsfw true to mark the channel as NSFW, false otherwise.
     * @return This GuildChannelCreateAction instance.
     */
    public GuildChannelCreateAction setNsfw(boolean nsfw) {
        if (isTextChannelType() || isVoiceChannelType() || isAnnouncementChannelType() || channelType == ChannelType.GUILD_FORUM) {
            setProperty("nsfw", nsfw);
        }
        return this;
    }

    /**
     * Sets the voice region for the guild channel if the channel type is voice.
     *
     * @param rtcRegion the voice region to set
     * @return the modified GuildChannelCreateAction object
     */
    public GuildChannelCreateAction setVoiceRegion(VoiceRegion rtcRegion) {
        if (isVoiceChannelType()) {
            setProperty("rtc_region", rtcRegion.getValue());
        }
        return this;
    }

    /**
     * Sets the video quality mode for the guild channel if the channel type is voice.
     *
     * @param videoQualityMode the video quality mode to set
     * @return the modified GuildChannelCreateAction object
     */
    public GuildChannelCreateAction setVideoQuality(VideoQualityMode videoQualityMode) {
        if (isVoiceChannelType()) {
            setProperty("video_quality_mode", videoQualityMode.getValue());
        }
        return this;
    }

    /**
     * Sets the default reaction for the guild channel if the channel type is forum media.
     *
     * @param emojiIdOrName the emoji ID or name to set as the default reaction
     * @return the modified GuildChannelCreateAction object
     */
    public GuildChannelCreateAction setDefaultReaction(String emojiIdOrName) {
        if (isForumMediaChannelType()) {
            ObjectNode reaction = mapper.createObjectNode();
            reaction.put("emoji_id", MessageUtils.isEmojiLong(emojiIdOrName) ? emojiIdOrName : null);
            reaction.put("emoji_name", MessageUtils.isEmojiLong(emojiIdOrName) ? null : emojiIdOrName);
            setProperty("default_reaction_emoji", reaction);
        }
        return this;
    }

    /**
     * Sets the tags for the guild channel if the channel type is forum media.
     *
     * @param tags the list of forum tags to set
     * @return the modified GuildChannelCreateAction object
     */
    public GuildChannelCreateAction setTags(List<ForumTag> tags) {
        if (isForumMediaChannelType()) {
            setForumTags(tags);
        }
        return this;
    }

    /**
     * Sets the posts order for the guild channel if the channel type is forum media.
     *
     * @param type the order type to set
     * @return the modified GuildChannelCreateAction object
     */
    public GuildChannelCreateAction setPostsOrder(OrderType type) {
        if (isForumMediaChannelType()) {
            setProperty("default_sort_order", type.getValue());
        }
        return this;
    }

    /**
     * Sets the forum layout for the guild channel if the channel type is GUILD_FORUM.
     *
     * @param type the ForumLayoutType to set
     * @return the modified GuildChannelCreateAction object
     */
    public GuildChannelCreateAction setForumLayout(ForumLayoutType type) {
        if (channelType == ChannelType.GUILD_FORUM) {
            setProperty("default_forum_layout", type.getValue());
        }
        return this;
    }

    /**
     * Sets the thread rate limit for the guild channel.
     *
     * @param rateLimit the rate limit per user to set
     * @return the modified GuildChannelCreateAction object
     */
    public GuildChannelCreateAction setThreadRateLimit(int rateLimit) {
        if (isTextChannelType() || isAnnouncementChannelType() || isForumMediaChannelType()) {
            setProperty("rate_limit_per_user", rateLimit);
        }
        return this;
    }

    /**
     * Sets the forum tags for the guild channel.
     *
     * @param tags the list of forum tags to set
     * @return the modified GuildChannelCreateAction object
     */
    private GuildChannelCreateAction setForumTags(List<ForumTag> tags) {
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
        return this;
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

    private String getCreatedId() {
        return createdId;
    }

    private JsonNode getJsonObject() {
        return jsonObject;
    }

    private void submit() {
        if (hasChanges) {
            JsonNode jsonNode = ApiClient.post(jsonObject, Routes.Guild.Channels(guildId));
            if (jsonNode != null && jsonNode.has("id")) {
                createdId = jsonNode.get("id").asText();
            }
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
