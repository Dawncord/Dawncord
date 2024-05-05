package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.event.ButtonEvent;
import io.github.dawncord.api.event.SelectMenuEvent;
import io.github.dawncord.api.event.SlashCommandEvent;
import io.github.dawncord.api.types.GuildMemberFlag;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Represents an action for updating a guild member.
 *
 * @see GuildMember
 */
public class GuildMemberModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private final String userId;
    private boolean hasChanges = false;

    /**
     * Create a new {@link GuildMemberModifyAction}
     *
     * @param guildId The ID of the guild to which the member belongs.
     * @param userId  The ID of the member to be modified.
     */
    public GuildMemberModifyAction(String guildId, String userId) {
        this.guildId = guildId;
        this.userId = userId;
        this.jsonObject = mapper.createObjectNode();
    }

    private GuildMemberModifyAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    /**
     * Sets the nickname for the guild member.
     *
     * @param nickName the nickname to set
     * @return the modified GuildMemberModifyAction object
     */
    public GuildMemberModifyAction setNick(String nickName) {
        return setProperty("nick", nickName);
    }

    /**
     * Sets the roles for the guild member.
     *
     * @param roleIds the list of role IDs to set
     * @return the modified GuildMemberModifyAction object
     */
    public GuildMemberModifyAction setRoles(List<String> roleIds) {
        return setProperty("roles", roleIds);
    }

    /**
     * Sets the mute property for the guild member
     *
     * @param isMuted the boolean value indicating whether the user should be muted or not
     * @return the modified GuildMemberModifyAction object
     */
    public GuildMemberModifyAction mute(boolean isMuted) {
        return setProperty("mute", isMuted);
    }

    /**
     * Sets the deafen property for the guild member
     *
     * @param isDeafened the boolean value indicating whether the user should be deafened or not
     * @return the modified GuildMemberModifyAction object
     */
    public GuildMemberModifyAction deafen(boolean isDeafened) {
        return setProperty("deaf", isDeafened);
    }

    /**
     * Sets the channel ID for the guild member if they are currently in a voice channel.
     *
     * @param channelId the ID of the channel to move the member to
     * @return the modified GuildMemberModifyAction object
     */
    public GuildMemberModifyAction moveToChannel(String channelId) {
        return setProperty("channel_id", channelId);
    }

    /**
     * Sets the channel ID for the guild member if they are currently in a voice channel.
     *
     * @param channelId the ID of the channel to move the member to
     * @return the modified GuildMemberModifyAction object
     */
    public GuildMemberModifyAction moveToChannel(long channelId) {
        return moveToChannel(String.valueOf(channelId));
    }

    /**
     * Sets the timeout until the guild member's communication is disabled.
     *
     * @param timeout the zoned date and time until communication is disabled
     * @return the modified GuildMemberModifyAction object
     */
    public GuildMemberModifyAction setTimeoutUntil(ZonedDateTime timeout) {
        return setProperty("communication_disabled_until", timeout.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    /**
     * Removes the timeout for the guild member's communication.
     *
     * @return the modified GuildMemberModifyAction object
     */
    public GuildMemberModifyAction removeTimeout() {
        return setProperty("communication_disabled_until", NullNode.instance);
    }

    /**
     * Sets the flags for the guild member
     *
     * @param flags the flags to set
     * @return the modified GuildMemberModifyAction object
     */
    public GuildMemberModifyAction setFlags(GuildMemberFlag... flags) {
        long value = 0;
        for (GuildMemberFlag flag : flags) {
            value |= flag.getValue();
        }
        return setProperty("flags", value);
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.Guild.Member.Get(guildId, userId));
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
