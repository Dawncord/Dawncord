package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.ForumTag;
import org.dimas4ek.wrapper.entities.PermissionOverride;
import org.dimas4ek.wrapper.types.*;
import org.dimas4ek.wrapper.utils.MessageUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class GuildChannelCreateAction {
    private final String guildId;
    private final ChannelType channelType;
    private final JSONObject jsonObject;
    private boolean hasChanges = false;

    public GuildChannelCreateAction(String guildId, ChannelType channelType) {
        this.guildId = guildId;
        this.channelType = channelType;
        this.jsonObject = new JSONObject();
    }

    private void setProperty(String name, Object value) {
        jsonObject.put(name, value);
        hasChanges = true;
    }

    public GuildChannelCreateAction setName(String name) {
        setProperty("name", name);
        return this;
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
        setProperty("position", position);
        return this;
    }

    public GuildChannelCreateAction setPermissionOverrides(PermissionOverride... permissionOverrides) {
        if (permissionOverrides != null && permissionOverrides.length > 0) {
            JSONArray jsonArray = new JSONArray();
            for (PermissionOverride permissionOverride : permissionOverrides) {
                JSONObject override = new JSONObject();
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
                jsonArray.put(override);
            }
            jsonObject.put("permission_overwrites", jsonArray);
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
        setParentId(String.valueOf(parentId));
        return this;
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
            JSONObject reaction = new JSONObject();
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
        JSONArray array = new JSONArray();
        for (ForumTag tag : tags) {
            JSONObject tagJson = new JSONObject();
            tagJson.put("name", tag.getName());
            tagJson.put("moderated", tag.isModerated());
            tagJson.put("emoji_id", MessageUtils.isEmojiLong(tag.getEmojiIdOrName()) ? tag.getEmojiIdOrName() : null);
            tagJson.put("emoji_name", !MessageUtils.isEmojiLong(tag.getEmojiIdOrName()) ? tag.getEmojiIdOrName() : null);
            array.put(tagJson);
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

    private JSONObject getJsonObject() {
        return jsonObject;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.post(jsonObject, "/guilds/" + guildId + "/channels");
            hasChanges = false;
        }
        jsonObject.clear();
    }
}
