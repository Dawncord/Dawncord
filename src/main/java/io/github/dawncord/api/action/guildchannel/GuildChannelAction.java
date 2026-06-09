package io.github.dawncord.api.action.guildchannel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.Action;
import io.github.dawncord.api.entities.ForumTag;
import io.github.dawncord.api.entities.PermissionOverride;
import io.github.dawncord.api.entities.channel.GuildCategory;
import io.github.dawncord.api.types.*;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.MessageUtils;

import java.util.List;

public abstract class GuildChannelAction extends Action<GuildChannelAction> {
    protected final ChannelType channelType;
    protected final String guildId;

    protected GuildChannelAction(String guildId, ChannelType channelType) {
        super();
        this.channelType = channelType;
        this.guildId = guildId;
    }

    protected GuildChannelAction() {
        super();
        this.channelType = null;
        this.guildId = null;
    }

    /**
     * Sets the name property for the guild channel.
     *
     * @param name the name to set
     * @return the modified GuildChannelAction object
     */
    public GuildChannelAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the type of the guild channel.
     * Note: This method only applies to text channels and announcement channels.
     *
     * @param type The type of the guild channel.
     * @return This GuildChannelModifyAction for method chaining.
     */
    public GuildChannelAction setType(ChannelType type) {
        //todo remake
        // split this class by text, voice etc. channels
        if ((isTextChannelType() || isAnnouncementChannelType())
                && (type == ChannelType.GUILD_TEXT || type == ChannelType.GUILD_ANNOUNCEMENT)) {
            setProperty("type", type.getValue());
        }
        return this;
    }

    /**
     * Sets the topic for the guild channel. if the channel type is text, announcement, or forum media.
     *
     * @param topic the topic to set
     * @return the modified GuildChannelAction object
     */
    public GuildChannelAction setTopic(String topic) {
        if (isTextChannelType() || isAnnouncementChannelType() || isForumMediaChannelType()) {
            setProperty("topic", topic);
        }
        return this;
    }

    /**
     * Sets the bitrate for the guild channel. if the channel type is voice.
     *
     * @param bitrate the bitrate to set
     * @return the modified GuildChannelAction object
     */
    public GuildChannelAction setBitrate(int bitrate) {
        if (isVoiceChannelType()) {
            setProperty("bitrate", bitrate);
        }
        return this;
    }

    /**
     * Sets the rate limit for the guild channel.
     *
     * @param rateLimit the rate limit to set
     * @return the modified GuildChannelAction object
     */
    public GuildChannelAction setRateLimit(int rateLimit) {
        if (isTextChannelType() || isVoiceChannelType() || isForumMediaChannelType()) {
            setProperty("rate_limit_per_user", rateLimit);
        }
        return this;
    }

    /**
     * Sets the user limit for the guild channel. if the channel type is voice.
     *
     * @param userLimit the user limit to set
     * @return the modified GuildChannelAction object
     */
    public GuildChannelAction setUserLimit(int userLimit) {
        if (isVoiceChannelType()) {
            setProperty("user_limit", userLimit);
        }
        return this;
    }

    /**
     * Sets the position property for the guild channel.
     *
     * @param position the position to set
     * @return the modified GuildChannelAction object
     */
    public GuildChannelAction setPosition(int position) {
        return setProperty("position", position);
    }

    /**
     * Sets the parent ID for the guild channel.
     *
     * @param category the parent channel
     * @return the modified GuildChannelAction object
     */
    public GuildChannelAction setParentId(GuildCategory category) {
        if (isTextChannelType() || isVoiceChannelType() || isAnnouncementChannelType() || isForumMediaChannelType()) {
            setProperty("parent_id", category.getId());
        }
        return this;
    }

    /**
     * Sets the NSFW (Not Safe For Work) flag for the guild channel if applicable.
     * This flag indicates whether the channel is intended for NSFW content.
     *
     * @param enabled true to mark the channel as NSFW, false otherwise.
     * @return This GuildChannelAction instance.
     */
    public GuildChannelAction setNsfw(boolean enabled) {
        if (isTextChannelType() || isVoiceChannelType() || isAnnouncementChannelType() || channelType == ChannelType.GUILD_FORUM) {
            setProperty("nsfw", enabled);
        }
        return this;
    }

    /**
     * Sets the voice region for the guild channel if the channel type is voice.
     *
     * @param rtcRegion the voice region to set
     * @return the modified GuildChannelAction object
     */
    public GuildChannelAction setVoiceRegion(VoiceRegion rtcRegion) {
        //todo remake voice regions
        if (isVoiceChannelType()) {
            setProperty("rtc_region", rtcRegion.getValue());
        }
        return this;
    }

    /**
     * Sets the optimal voice region for the guild channel if it is a voice or stage voice channel.
     *
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelAction setOptimalVoiceRegion() {
        //todo remake voice regions
        if (isVoiceChannelType()) {
            String optimalVoiceRegion = null;
            JsonNode voiceRegions = JsonUtils.fetch(Routes.VoiceRegions());
            for (JsonNode region : voiceRegions) {
                if (region.get("optimal").asBoolean()) {
                    optimalVoiceRegion = region.get("id").asText();
                    break;
                }
            }
            setProperty("rtc_region", optimalVoiceRegion);
        }
        return this;
    }

    /**
     * Sets the video quality mode for the guild channel if the channel type is voice.
     *
     * @param videoQualityMode the video quality mode to set
     * @return the modified GuildChannelAction object
     */
    public GuildChannelAction setVideoQuality(VideoQualityMode videoQualityMode) {
        if (isVoiceChannelType()) {
            setProperty("video_quality_mode", videoQualityMode.getValue());
        }
        return this;
    }

    /**
     * Sets the default reaction for the guild channel if the channel type is forum media.
     *
     * @param emojiIdOrName the emoji ID or name to set as the default reaction
     * @return the modified GuildChannelAction object
     */
    public GuildChannelAction setDefaultReaction(String emojiIdOrName) {
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
     * @return the modified GuildChannelAction object
     */
    public GuildChannelAction setTags(List<ForumTag> tags) {
        if (isForumMediaChannelType()) {
            setForumTags(tags, mapper.createArrayNode());
        }
        return this;
    }

    /**
     * Sets the forum tags for the guild channel.
     *
     * @param tags the list of forum tags to set
     * @return the modified GuildChannelAction object
     */
    protected GuildChannelAction setForumTags(List<ForumTag> tags, ArrayNode array) {
        for (ForumTag tag : tags) {
            ObjectNode tagJson = mapper.createObjectNode();
            tagJson.put("name", tag.getName());
            tagJson.put("moderated", tag.isModerated());
            tagJson.put("emoji_id", MessageUtils.isEmojiLong(tag.getEmojiIdOrName()) ? tag.getEmojiIdOrName() : null);
            tagJson.put("emoji_name", !MessageUtils.isEmojiLong(tag.getEmojiIdOrName()) ? tag.getEmojiIdOrName() : null);
            array.add(tagJson);
        }
        return setProperty("available_tags", array);
    }

    /**
     * Sets the posts order for the guild channel if the channel type is forum media.
     *
     * @param type the order type to set
     * @return the modified GuildChannelAction object
     */
    public GuildChannelAction setPostsOrder(OrderType type) {
        if (isForumMediaChannelType()) {
            setProperty("default_sort_order", type.getValue());
        }
        return this;
    }

    /**
     * Sets the forum layout for the guild channel if the channel type is GUILD_FORUM.
     *
     * @param type the ForumLayoutType to set
     * @return the modified GuildChannelAction object
     */
    public GuildChannelAction setForumLayout(ForumLayoutType type) {
        if (channelType == ChannelType.GUILD_FORUM) {
            setProperty("default_forum_layout", type.getValue());
        }
        return this;
    }

    /**
     * Sets the thread rate limit for the guild channel.
     *
     * @param rateLimit the rate limit per user to set
     * @return the modified GuildChannelAction object
     */
    public GuildChannelAction setThreadRateLimit(int rateLimit) {
        if (isTextChannelType() || isAnnouncementChannelType() || isForumMediaChannelType()) {
            setProperty("rate_limit_per_user", rateLimit);
        }
        return this;
    }

    /**
     * Sets the permission overrides for the guild channel.
     *
     * @param overrides the permission overrides to set
     * @return the modified GuildChannelAction object
     */
    public GuildChannelAction setPermissionOverrides(PermissionOverride... overrides) {
        if (overrides != null && overrides.length > 0) {
            ArrayNode jsonArray = mapper.createArrayNode();
            for (PermissionOverride permissionOverride : overrides) {
                ObjectNode override = mapper.createObjectNode();
                override.put("id", permissionOverride.id());
                override.put("type", permissionOverride.type().getValue());
                override.put("deny", permissionOverride.denied() != null && !permissionOverride.denied().isEmpty()
                        ? String.valueOf(permissionOverride.denied().stream()
                                         .mapToLong(PermissionType::getValue)
                                         .reduce(0L, (x, y) -> x | y))
                        : "0");
                override.put("allow", permissionOverride.allowed() != null && !permissionOverride.allowed().isEmpty()
                        ? String.valueOf(permissionOverride.allowed().stream()
                                         .mapToLong(PermissionType::getValue)
                                         .reduce(0L, (x, y) -> x | y))
                        : "0");
                jsonArray.add(override);
            }
            jsonObject.set("permission_overwrites", jsonArray);
        }
        return this;
    }

    private boolean isTextChannelType() {
        return channelType == ChannelType.GUILD_TEXT;
    }

    protected boolean isForumMediaChannelType() {
        return channelType == ChannelType.GUILD_FORUM || channelType == ChannelType.GUILD_MEDIA;
    }

    private boolean isAnnouncementChannelType() {
        return channelType == ChannelType.GUILD_ANNOUNCEMENT;
    }

    private boolean isVoiceChannelType() {
        return channelType == ChannelType.GUILD_VOICE || channelType == ChannelType.GUILD_STAGE_VOICE;
    }
}
