package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.ForumTag;
import org.dimas4ek.wrapper.entities.PermissionOverride;
import org.dimas4ek.wrapper.entities.channel.GuildCategory;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.types.*;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.dimas4ek.wrapper.utils.MessageUtils;

import java.util.List;

public class GuildChannelModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final GuildChannel channel;
    private boolean hasChanges = false;

    public GuildChannelModifyAction(GuildChannel channel) {
        this.channel = channel;
        jsonObject = mapper.createObjectNode();
    }

    private GuildChannelModifyAction setProperty(String key, Object value) {
        jsonObject.set(key, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    //region Threads
    public GuildChannelModifyAction setArchived(boolean enabled) {
        if (channel.getType() == ChannelType.PUBLIC_THREAD || channel.getType() == ChannelType.PRIVATE_THREAD
                || channel.getType() == ChannelType.ANNOUNCEMENT_THREAD) {
            setProperty("archived", enabled);
        }
        return this;
    }

    public GuildChannelModifyAction setArchiveDuration(int minutes) {
        if (channel.getType() == ChannelType.PUBLIC_THREAD || channel.getType() == ChannelType.PRIVATE_THREAD
                || channel.getType() == ChannelType.ANNOUNCEMENT_THREAD) {
            setProperty("auto_archive_duration", minutes);
        }
        return this;
    }

    public GuildChannelModifyAction setLocked(boolean enabled) {
        if (channel.getType() == ChannelType.PUBLIC_THREAD || channel.getType() == ChannelType.PRIVATE_THREAD
                || channel.getType() == ChannelType.ANNOUNCEMENT_THREAD) {
            setProperty("locked", enabled);
        }
        return this;
    }

    public GuildChannelModifyAction setInvitable(boolean enabled) {
        if (channel.getType() == ChannelType.PRIVATE_THREAD) {
            setProperty("invitable", enabled);
        }
        return this;
    }
    //endregion

    public GuildChannelModifyAction setName(String name) {
        return setProperty("name", name);
    }

    public GuildChannelModifyAction setType(ChannelType type) {
        if ((channel.getType() == ChannelType.GUILD_TEXT || channel.getType() == ChannelType.GUILD_ANNOUNCEMENT)
                && (type == ChannelType.GUILD_TEXT || type == ChannelType.GUILD_ANNOUNCEMENT)) {
            setProperty("type", type.getValue());
        }
        return this;
    }

    public GuildChannelModifyAction setPosition(int position) {
        return setProperty("position", position);
    }

    public GuildChannelModifyAction setTopic(String topic) {
        if (channel.getType() == ChannelType.GUILD_FORUM || channel.getType() == ChannelType.GUILD_MEDIA) {
            setProperty("topic", topic);
        }
        return this;
    }

    public GuildChannelModifyAction setNsfw(boolean enabled) {
        if (channel.getType() == ChannelType.GUILD_TEXT || channel.getType() == ChannelType.GUILD_ANNOUNCEMENT
                || channel.getType() == ChannelType.GUILD_FORUM || channel.getType() == ChannelType.GUILD_MEDIA) {
            setProperty("nsfw", enabled);
        }
        return this;
    }

    public GuildChannelModifyAction setRateLimit(int seconds) {
        if (channel.getType() == ChannelType.GUILD_TEXT || channel.getType() == ChannelType.GUILD_VOICE
                || channel.getType() == ChannelType.GUILD_STAGE_VOICE || channel.getType() == ChannelType.GUILD_FORUM
                || channel.getType() == ChannelType.GUILD_MEDIA) {
            setProperty("rate_limit_per_user", seconds);
        }
        return this;
    }

    public GuildChannelModifyAction setBitrate(int bitrate) {
        if (channel.getType() == ChannelType.GUILD_VOICE || channel.getType() == ChannelType.GUILD_STAGE_VOICE) {
            setProperty("bitrate", bitrate);
        }
        return this;
    }

    public GuildChannelModifyAction setUserLimit(int limit) {
        if (channel.getType() == ChannelType.GUILD_VOICE || channel.getType() == ChannelType.GUILD_STAGE_VOICE) {
            setProperty("user_limit", limit);
        }
        return this;
    }

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
                        .mapToLong(PermissionType::getValue)
                        .reduce(0L, (x, y) -> x | y))
                        : "0");
                overrideJson.put("allow", override.getAllowed() != null && !override.getAllowed().isEmpty()
                        ? String.valueOf(override.getAllowed().stream()
                        .mapToLong(PermissionType::getValue)
                        .reduce(0L, (x, y) -> x | y))
                        : "0");
                overridesArray.add(overrideJson);
            }
            setProperty("permission_overwrites", overridesArray);
        }
        return this;
    }

    public GuildChannelModifyAction clearPermissionOverrides() {
        if (channel.getType() != ChannelType.PUBLIC_THREAD || channel.getType() != ChannelType.PRIVATE_THREAD
                || channel.getType() != ChannelType.ANNOUNCEMENT_THREAD) {
            setProperty("permission_overwrites", mapper.createArrayNode());
        }
        return this;
    }

    public GuildChannelModifyAction setCategory(GuildCategory category) {
        if (channel.getType() != ChannelType.PUBLIC_THREAD || channel.getType() != ChannelType.PRIVATE_THREAD
                || channel.getType() != ChannelType.ANNOUNCEMENT_THREAD) {
            setProperty("parent_id", category.getId());
        }
        return this;
    }

    public GuildChannelModifyAction setVoiceRegion(VoiceRegion voiceRegion) {
        if (channel.getType() == ChannelType.GUILD_VOICE || channel.getType() == ChannelType.GUILD_STAGE_VOICE) {
            setProperty("rtc_region", voiceRegion.getValue());
        }
        return this;
    }

    public GuildChannelModifyAction setOptimalVoiceRegion() {
        if (channel.getType() == ChannelType.GUILD_VOICE || channel.getType() == ChannelType.GUILD_STAGE_VOICE) {
            String optimalVoiceRegion = null;
            JsonNode voiceRegions = JsonUtils.fetchArray("/voice/regions");
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

    public GuildChannelModifyAction setVideoQuality(VideoQualityMode mode) {
        if (channel.getType() == ChannelType.GUILD_VOICE || channel.getType() == ChannelType.GUILD_STAGE_VOICE) {
            setProperty("video_quality_mode", mode.getValue());
        }
        return this;
    }

    public GuildChannelModifyAction setFlags(ChannelFlag flag, ChannelFlag... flags) {
        int value = 0;
        value = setFlagValue(value, flag);
        for (ChannelFlag f : flags) {
            value = setFlagValue(value, f);
        }
        return setProperty("channel_flags", value);
    }

    public GuildChannelModifyAction setTags(List<ForumTag> tags) {
        if (channel.getType() == ChannelType.GUILD_FORUM || channel.getType() == ChannelType.GUILD_MEDIA) {
            setForumTags(tags, mapper.createArrayNode());
        }
        return this;
    }

    public GuildChannelModifyAction updateTags(List<ForumTag> tags) {
        if (channel.getType() == ChannelType.GUILD_FORUM || channel.getType() == ChannelType.GUILD_MEDIA) {
            setForumTags(tags, (ArrayNode) JsonUtils.fetchEntity("/channels/" + channel.getId()).get("available_tags"));
        }
        return this;
    }

    public GuildChannelModifyAction clearTags() {
        if (channel.getType() == ChannelType.GUILD_FORUM || channel.getType() == ChannelType.GUILD_MEDIA) {
            setProperty("available_tags", mapper.createArrayNode());
        }
        return this;
    }

    public GuildChannelModifyAction setDefaultReaction(String emojiIdOrName) {
        if (channel.getType() == ChannelType.GUILD_FORUM || channel.getType() == ChannelType.GUILD_MEDIA) {
            ObjectNode defaultEmoji = mapper.createObjectNode();
            defaultEmoji.put("emoji_id", MessageUtils.isEmojiLong(emojiIdOrName) ? emojiIdOrName : null);
            defaultEmoji.put("emoji_name", !MessageUtils.isEmojiLong(emojiIdOrName) ? emojiIdOrName : null);
            setProperty("default_reaction_emoji", defaultEmoji);
        }
        return this;
    }

    public GuildChannelModifyAction clearDefaultReaction() {
        if (channel.getType() == ChannelType.GUILD_FORUM || channel.getType() == ChannelType.GUILD_MEDIA) {
            setProperty("default_reaction_emoji", mapper.createObjectNode());
        }
        return this;
    }

    public GuildChannelModifyAction setPostsOrder(OrderType type) {
        if (channel.getType() == ChannelType.GUILD_FORUM || channel.getType() == ChannelType.GUILD_MEDIA) {
            setProperty("default_sort_order", type.getValue());
        }
        return this;
    }

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

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, "/channels/" + channel.getId());
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
