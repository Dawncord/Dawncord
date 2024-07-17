package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.ForumTag;
import io.github.dawncord.api.entities.PermissionOverride;
import io.github.dawncord.api.entities.channel.GuildCategory;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.event.ButtonEvent;
import io.github.dawncord.api.event.SelectMenuEvent;
import io.github.dawncord.api.event.SlashCommandEvent;
import io.github.dawncord.api.types.*;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.MessageUtils;

import java.util.List;

/**
 * Represents an action for updating a guild channel.
 *
 * @see GuildChannel
 */
public class GuildChannelModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final GuildChannel channel;
    private boolean hasChanges = false;

    /**
     * Create a new {@link GuildChannelModifyAction}
     *
     * @param channel The guild channel to be modified.
     */
    public GuildChannelModifyAction(GuildChannel channel) {
        this.channel = channel;
        jsonObject = mapper.createObjectNode();
    }

    private GuildChannelModifyAction setProperty(String key, Object value) {
        jsonObject.set(key, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    /**
     * Sets the archived status for the guild channel.
     *
     * @param enabled true to set the channel as archived, false otherwise
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setArchived(boolean enabled) {
        if (channel.getType() == ChannelType.PUBLIC_THREAD || channel.getType() == ChannelType.PRIVATE_THREAD
                || channel.getType() == ChannelType.ANNOUNCEMENT_THREAD) {
            setProperty("archived", enabled);
        }
        return this;
    }

    /**
     * Sets the archive duration for the guild channel.
     *
     * @param minutes the duration of the archive in minutes
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setArchiveDuration(int minutes) {
        if (channel.getType() == ChannelType.PUBLIC_THREAD || channel.getType() == ChannelType.PRIVATE_THREAD
                || channel.getType() == ChannelType.ANNOUNCEMENT_THREAD) {
            setProperty("auto_archive_duration", minutes);
        }
        return this;
    }

    /**
     * Sets the locked status for the guild channel.
     *
     * @param enabled true to set the channel as locked, false otherwise
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setLocked(boolean enabled) {
        if (channel.getType() == ChannelType.PUBLIC_THREAD || channel.getType() == ChannelType.PRIVATE_THREAD
                || channel.getType() == ChannelType.ANNOUNCEMENT_THREAD) {
            setProperty("locked", enabled);
        }
        return this;
    }

    /**
     * Sets the invitable status for the guild channel.
     *
     * @param enabled true to set the channel as invitable, false otherwise
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setInvitable(boolean enabled) {
        if (channel.getType() == ChannelType.PRIVATE_THREAD) {
            setProperty("invitable", enabled);
        }
        return this;
    }

    /**
     * Sets the name property of the guild channel.
     *
     * @param name the new name for the GuildChannelModifyAction object
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the type of the guild channel.
     * Note: This method only applies to text channels and announcement channels.
     *
     * @param type The type of the guild channel.
     * @return This GuildChannelModifyAction for method chaining.
     */
    public GuildChannelModifyAction setType(ChannelType type) {
        if ((channel.getType() == ChannelType.GUILD_TEXT || channel.getType() == ChannelType.GUILD_ANNOUNCEMENT)
                && (type == ChannelType.GUILD_TEXT || type == ChannelType.GUILD_ANNOUNCEMENT)) {
            setProperty("type", type.getValue());
        }
        return this;
    }

    /**
     * Sets the position for the guild channel.
     *
     * @param position the new position of the guild channel
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setPosition(int position) {
        return setProperty("position", position);
    }

    /**
     * Sets the topic for the guild channel if it is a forum or media channel.
     *
     * @param topic the new topic for the guild channel
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setTopic(String topic) {
        if (channel.getType() == ChannelType.GUILD_FORUM || channel.getType() == ChannelType.GUILD_MEDIA) {
            setProperty("topic", topic);
        }
        return this;
    }

    /**
     * Sets the NSFW status for the guild channel if it is a text, announcement, forum, or media channel.
     *
     * @param enabled true to set the channel as NSFW, false otherwise
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setNsfw(boolean enabled) {
        if (channel.getType() == ChannelType.GUILD_TEXT || channel.getType() == ChannelType.GUILD_ANNOUNCEMENT
                || channel.getType() == ChannelType.GUILD_FORUM || channel.getType() == ChannelType.GUILD_MEDIA) {
            setProperty("nsfw", enabled);
        }
        return this;
    }

    /**
     * Sets the rate limit for the guild channel if it is a text, voice, stage voice, forum, or media channel.
     *
     * @param seconds the rate limit in seconds
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setRateLimit(int seconds) {
        if (channel.getType() == ChannelType.GUILD_TEXT || channel.getType() == ChannelType.GUILD_VOICE
                || channel.getType() == ChannelType.GUILD_STAGE_VOICE || channel.getType() == ChannelType.GUILD_FORUM
                || channel.getType() == ChannelType.GUILD_MEDIA) {
            setProperty("rate_limit_per_user", seconds);
        }
        return this;
    }

    /**
     * Sets the bitrate for the guild channel if it is a voice or stage voice channel.
     *
     * @param bitrate the bitrate value to set
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setBitrate(int bitrate) {
        if (channel.getType() == ChannelType.GUILD_VOICE || channel.getType() == ChannelType.GUILD_STAGE_VOICE) {
            setProperty("bitrate", bitrate);
        }
        return this;
    }

    /**
     * Sets the user limit for the guild channel if it is a voice or stage voice channel.
     *
     * @param limit the new user limit for the guild channel
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setUserLimit(int limit) {
        if (channel.getType() == ChannelType.GUILD_VOICE || channel.getType() == ChannelType.GUILD_STAGE_VOICE) {
            setProperty("user_limit", limit);
        }
        return this;
    }

    /**
     * Sets the permission overrides for the guild channel.
     *
     * @param overrides the permission overrides to set
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setPermissionOverrides(PermissionOverride... overrides) {
        if (channel.getType() != ChannelType.PUBLIC_THREAD || channel.getType() != ChannelType.PRIVATE_THREAD
                || channel.getType() != ChannelType.ANNOUNCEMENT_THREAD) {

            ArrayNode overridesArray = mapper.createArrayNode();

            for (PermissionOverride override : overrides) {
                ObjectNode overrideJson = mapper.createObjectNode();
                overrideJson.put("id", override.getId());
                overrideJson.put("type", override.getType().getValue());
                overrideJson.put("deny", override.getDenied() != null && !override.getDenied().isEmpty()
                        ? String.valueOf(override.getDenied().stream()
                        .filter(this::checkChannelType)
                        .mapToLong(ChannelPermissionType::getValue)
                        .reduce(0L, (x, y) -> x | y))
                        : "0");
                overrideJson.put("allow", override.getAllowed() != null && !override.getAllowed().isEmpty()
                        ? String.valueOf(override.getAllowed().stream()
                        .filter(this::checkChannelType)
                        .mapToLong(ChannelPermissionType::getValue)
                        .reduce(0L, (x, y) -> x | y))
                        : "0");
                overridesArray.add(overrideJson);
            }

            setProperty("permission_overwrites", overridesArray);
        }
        return this;
    }

    /**
     * Clears the permission overrides for the guild channel if the channel type is not a public thread, private thread, or announcement thread.
     *
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction clearPermissionOverrides() {
        if (channel.getType() != ChannelType.PUBLIC_THREAD || channel.getType() != ChannelType.PRIVATE_THREAD
                || channel.getType() != ChannelType.ANNOUNCEMENT_THREAD) {
            setProperty("permission_overwrites", mapper.createArrayNode());
        }
        return this;
    }

    /**
     * Sets the category for the guild channel if the channel type is not a public thread, private thread, or announcement thread.
     *
     * @param category the category to set for the guild channel
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setCategory(GuildCategory category) {
        if (channel.getType() != ChannelType.PUBLIC_THREAD || channel.getType() != ChannelType.PRIVATE_THREAD
                || channel.getType() != ChannelType.ANNOUNCEMENT_THREAD) {
            setProperty("parent_id", category.getId());
        }
        return this;
    }

    /**
     * Sets the voice region for the guild channel if it is a voice or stage voice channel.
     *
     * @param voiceRegion the voice region to set for the guild channel
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setVoiceRegion(VoiceRegion voiceRegion) {
        if (channel.getType() == ChannelType.GUILD_VOICE || channel.getType() == ChannelType.GUILD_STAGE_VOICE) {
            setProperty("rtc_region", voiceRegion.getValue());
        }
        return this;
    }

    /**
     * Sets the optimal voice region for the guild channel if it is a voice or stage voice channel.
     *
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setOptimalVoiceRegion() {
        if (channel.getType() == ChannelType.GUILD_VOICE || channel.getType() == ChannelType.GUILD_STAGE_VOICE) {
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
     * Sets the video quality mode for the guild channel if it is a voice or stage voice channel.
     *
     * @param mode the video quality mode to set
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setVideoQuality(VideoQualityMode mode) {
        if (channel.getType() == ChannelType.GUILD_VOICE || channel.getType() == ChannelType.GUILD_STAGE_VOICE) {
            setProperty("video_quality_mode", mode.getValue());
        }
        return this;
    }

    /**
     * Sets the flags for the guild channel.
     *
     * @param flag  the initial flag to set
     * @param flags additional flags to set
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setFlags(ChannelFlag flag, ChannelFlag... flags) {
        int value = 0;
        value = setFlagValue(value, flag);
        for (ChannelFlag f : flags) {
            value = setFlagValue(value, f);
        }
        return setProperty("channel_flags", value);
    }

    /**
     * Sets the tags for the guild channel if it is a forum or media channel.
     *
     * @param tags the list of forum tags to set
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setTags(List<ForumTag> tags) {
        if (channel.getType() == ChannelType.GUILD_FORUM || channel.getType() == ChannelType.GUILD_MEDIA) {
            setForumTags(tags, mapper.createArrayNode());
        }
        return this;
    }

    /**
     * Updates the tags for the guild channel if it is a forum or media channel.
     *
     * @param tags the list of forum tags to update
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction updateTags(List<ForumTag> tags) {
        if (channel.getType() == ChannelType.GUILD_FORUM || channel.getType() == ChannelType.GUILD_MEDIA) {
            setForumTags(tags, (ArrayNode) JsonUtils.fetch(Routes.Channel.Get(channel.getId())).get("available_tags"));
        }
        return this;
    }

    /**
     * Clears the tags for the guild channel if it is a forum or media channel.
     *
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction clearTags() {
        if (channel.getType() == ChannelType.GUILD_FORUM || channel.getType() == ChannelType.GUILD_MEDIA) {
            setProperty("available_tags", mapper.createArrayNode());
        }
        return this;
    }

    /**
     * Sets the default reaction emoji for the guild forum or media channel.
     *
     * @param emojiIdOrName the ID or name of the emoji to set as default reaction
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setDefaultReaction(String emojiIdOrName) {
        if (channel.getType() == ChannelType.GUILD_FORUM || channel.getType() == ChannelType.GUILD_MEDIA) {
            ObjectNode defaultEmoji = mapper.createObjectNode();
            defaultEmoji.put("emoji_id", MessageUtils.isEmojiLong(emojiIdOrName) ? emojiIdOrName : null);
            defaultEmoji.put("emoji_name", !MessageUtils.isEmojiLong(emojiIdOrName) ? emojiIdOrName : null);
            setProperty("default_reaction_emoji", defaultEmoji);
        }
        return this;
    }

    /**
     * Clears the default reaction emoji for the guild forum or media channel.
     *
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction clearDefaultReaction() {
        if (channel.getType() == ChannelType.GUILD_FORUM || channel.getType() == ChannelType.GUILD_MEDIA) {
            setProperty("default_reaction_emoji", mapper.createObjectNode());
        }
        return this;
    }

    /**
     * Sets the posts order for the guild channel if it is a forum or media channel.
     *
     * @param type the order type to set
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setPostsOrder(OrderType type) {
        if (channel.getType() == ChannelType.GUILD_FORUM || channel.getType() == ChannelType.GUILD_MEDIA) {
            setProperty("default_sort_order", type.getValue());
        }
        return this;
    }

    /**
     * Sets the forum layout for the guild channel if it is a forum channel.
     *
     * @param type the forum layout type to set
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setForumLayout(ForumLayoutType type) {
        if (channel.getType() == ChannelType.GUILD_FORUM) {
            setProperty("default_forum_layout", type.getValue());
        }
        return this;
    }

    private int setFlagValue(int value, ChannelFlag f) {
        if (f == ChannelFlag.HIDE_MEDIA_DOWNLOAD_OPTIONS) {
            if (channel.getType() == ChannelType.GUILD_MEDIA) {
                value = f.getValue();
            }
        } else if (f == ChannelFlag.REQUIRE_TAG) {
            if (channel.getType() == ChannelType.GUILD_FORUM || channel.getType() == ChannelType.GUILD_MEDIA) {
                value = f.getValue();
            }
        } else {
            value = f.getValue();
        }
        return value;
    }

    private void setForumTags(List<ForumTag> tags, ArrayNode array) {
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

    private boolean checkChannelType(ChannelPermissionType perm) {
        String types = perm.getTypes();
        return switch (channel.getType()) {
            case GUILD_TEXT, PUBLIC_THREAD, PRIVATE_THREAD, ANNOUNCEMENT_THREAD, GUILD_ANNOUNCEMENT ->
                    types.contains("T");
            case GUILD_VOICE -> types.contains("T") || types.contains("V");
            case GUILD_STAGE_VOICE -> types.contains("S");
            default -> false;
        };
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.Channel.Get(channel.getId()));
            hasChanges = false;
        }

        if (SlashCommandEvent.getData() != null) {
            SlashCommandEvent.UpdateData();
        }
        if (ButtonEvent.getData() != null) {
            ButtonEvent.UpdateData();
        }
        if (SelectMenuEvent.getData() != null) {
            SelectMenuEvent.UpdateData();
        }
    }
}
