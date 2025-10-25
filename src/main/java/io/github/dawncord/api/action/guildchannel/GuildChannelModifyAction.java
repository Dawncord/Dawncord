package io.github.dawncord.api.action.guildchannel;

import com.fasterxml.jackson.databind.node.ArrayNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.ForumTag;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.event.ButtonEvent;
import io.github.dawncord.api.event.SelectMenuEvent;
import io.github.dawncord.api.event.SlashCommandEvent;
import io.github.dawncord.api.types.*;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.List;

/**
 * Represents an action for updating a guild channel.
 *
 * @see GuildChannel
 */
public class GuildChannelModifyAction extends GuildChannelAction {
    private final String channelId;

    /**
     * Create a new {@link GuildChannelModifyAction}
     *
     * @param channelType The type of the guild channel
     * @param channelId The ID of the guild channel
     */
    public GuildChannelModifyAction(ChannelType channelType, String channelId) {
        super(null, channelType);
        this.channelId = channelId;
    }

    /**
     * Sets the archived status for the guild channel.
     *
     * @param enabled true to set the channel as archived, false otherwise
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction setArchived(boolean enabled) {
        if (isThreadChannel()) {
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
        if (isThreadChannel()) {
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
        if (isThreadChannel()) {
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
        if (channelType == ChannelType.PRIVATE_THREAD) {
            setProperty("invitable", enabled);
        }
        return this;
    }

    /**
     * Clears the permission overrides for the guild channel if the channel type is not a public thread, private thread, or announcement thread.
     *
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction clearPermissionOverrides() {
        if (isThreadChannel()) {
            setProperty("permission_overwrites", mapper.createArrayNode());
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
        setProperty("channel_flags", value);
        return this;
    }

    /**
     * Updates the tags for the guild channel if it is a forum or media channel.
     *
     * @param tags the list of forum tags to update
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction updateTags(List<ForumTag> tags) {
        if (isForumMediaChannelType()) {
            setForumTags(tags, (ArrayNode) JsonUtils.fetch(Routes.Channel.Get(channelId)).get("available_tags"));
        }
        return this;
    }

    /**
     * Clears the tags for the guild channel if it is a forum or media channel.
     *
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction clearTags() {
        if (isForumMediaChannelType()) {
            setProperty("available_tags", mapper.createArrayNode());
        }
        return this;
    }

    /**
     * Clears the default reaction emoji for the guild forum or media channel.
     *
     * @return the modified GuildChannelModifyAction object
     */
    public GuildChannelModifyAction clearDefaultReaction() {
        if (isForumMediaChannelType()) {
            setProperty("default_reaction_emoji", mapper.createObjectNode());
        }
        return this;
    }

    private int setFlagValue(int value, ChannelFlag f) {
        if (f == ChannelFlag.HIDE_MEDIA_DOWNLOAD_OPTIONS) {
            if (channelType == ChannelType.GUILD_MEDIA) {
                value = f.getValue();
            }
        } else if (f == ChannelFlag.REQUIRE_TAG) {
            if (isForumMediaChannelType()) {
                value = f.getValue();
            }
        } else {
            value = f.getValue();
        }
        return value;
    }

    private boolean isThreadChannel() {
        return channelType == ChannelType.PUBLIC_THREAD ||
                channelType == ChannelType.PRIVATE_THREAD ||
                channelType == ChannelType.ANNOUNCEMENT_THREAD;
    }

    @Override
    protected void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.Channel.Get(channelId));
            hasChanges = false;
        }

        //todo look about update data
        // use reflections if needed
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
