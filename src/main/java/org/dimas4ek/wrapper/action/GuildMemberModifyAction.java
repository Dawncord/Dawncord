package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.types.GuildMemberFlag;
import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GuildMemberModifyAction {
    private final String guildId;
    private final String userId;
    private final JSONObject jsonObject;
    private boolean hasChanges = false;

    public GuildMemberModifyAction(String guildId, String userId) {
        this.guildId = guildId;
        this.userId = userId;
        this.jsonObject = new JSONObject();
    }

    private void setProperty(String name, Object value) {
        jsonObject.put(name, value);
        hasChanges = true;
    }

    public GuildMemberModifyAction setNick(String nickName) {
        setProperty("nick", nickName);
        return this;
    }

    public GuildMemberModifyAction setRoles(List<String> roleIds) {
        setProperty("roles", roleIds);
        return this;
    }

    public GuildMemberModifyAction mute(boolean isMuted) {
        setProperty("mute", isMuted);
        return this;
    }

    public GuildMemberModifyAction deafen(boolean isDeafened) {
        setProperty("deaf", isDeafened);
        return this;
    }

    public GuildMemberModifyAction moveToChannel(String channelId) {
        setProperty("channel_id", channelId);
        return this;
    }

    public GuildMemberModifyAction moveToChannel(long channelId) {
        moveToChannel(String.valueOf(channelId));
        return this;
    }

    public GuildMemberModifyAction setTimeoutUntil(ZonedDateTime timeout) {
        setProperty("communication_disabled_until", timeout.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return this;
    }

    public GuildMemberModifyAction removeTimeout() {
        setProperty("communication_disabled_until", JSONObject.NULL);
        return this;
    }

    public GuildMemberModifyAction setFlags(GuildMemberFlag... flags) {
        long value = 0;
        for (GuildMemberFlag flag : flags) {
            value |= flag.getValue();
        }
        setProperty("flags", value);
        return this;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, "/guilds/" + guildId + "/members/" + userId);
            hasChanges = false;
        }
        jsonObject.clear();
    }
}
