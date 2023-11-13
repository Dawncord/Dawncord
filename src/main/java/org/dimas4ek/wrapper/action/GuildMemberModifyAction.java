package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.types.GuildMemberFlag;
import org.json.JSONObject;
import org.slf4j.helpers.CheckReturnValue;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GuildMemberModifyAction {
    //todo refactor all actions - use lambda

    private final String guildId;
    private final String userId;
    private final JSONObject jsonObject;

    public GuildMemberModifyAction(String guildId, String userId) {
        this.guildId = guildId;
        this.userId = userId;
        this.jsonObject = new JSONObject();
    }

    @CheckReturnValue
    private void setProperty(String name, Object value) {
        jsonObject.put(name, value);
    }

    @CheckReturnValue
    public GuildMemberModifyAction setNick(String nickName) {
        setProperty("nick", nickName);
        return this;
    }

    @CheckReturnValue
    public GuildMemberModifyAction setRoles(List<String> roleIds) {
        setProperty("roles", roleIds);
        return this;
    }

    @CheckReturnValue
    public GuildMemberModifyAction mute(boolean isMuted) {
        setProperty("mute", isMuted);
        return this;
    }

    @CheckReturnValue
    public GuildMemberModifyAction deafen(boolean isDeafened) {
        setProperty("deaf", isDeafened);
        return this;
    }

    @CheckReturnValue
    public GuildMemberModifyAction moveToChannel(String channelId) {
        setProperty("channel_id", channelId);
        return this;
    }

    @CheckReturnValue
    public GuildMemberModifyAction moveToChannel(long channelId) {
        moveToChannel(String.valueOf(channelId));
        return this;
    }

    @CheckReturnValue
    public GuildMemberModifyAction setTimeoutUntil(ZonedDateTime timeout) {
        setProperty("communication_disabled_until", timeout.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return this;
    }

    @CheckReturnValue
    public GuildMemberModifyAction removeTimeout() {
        setProperty("communication_disabled_until", JSONObject.NULL);
        return this;
    }

    @CheckReturnValue
    public GuildMemberModifyAction setFlags(GuildMemberFlag... flags) {
        long value = 0;
        for (GuildMemberFlag flag : flags) {
            value |= flag.getValue();
        }
        setProperty("flags", value);
        return this;
    }

    public void submit() {
        ApiClient.patch(jsonObject, "/guilds/" + guildId + "/members/" + userId);
        jsonObject.clear();
    }
}
