package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.types.GuildMemberFlag;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GuildMemberModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private final String userId;
    private boolean hasChanges = false;

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

    public GuildMemberModifyAction setNick(String nickName) {
        return setProperty("nick", nickName);
    }

    public GuildMemberModifyAction setRoles(List<String> roleIds) {
        return setProperty("roles", roleIds);
    }

    public GuildMemberModifyAction mute(boolean isMuted) {
        return setProperty("mute", isMuted);
    }

    public GuildMemberModifyAction deafen(boolean isDeafened) {
        return setProperty("deaf", isDeafened);
    }

    public GuildMemberModifyAction moveToChannel(String channelId) {
        return setProperty("channel_id", channelId);
    }

    public GuildMemberModifyAction moveToChannel(long channelId) {
        return moveToChannel(String.valueOf(channelId));
    }

    public GuildMemberModifyAction setTimeoutUntil(ZonedDateTime timeout) {
        return setProperty("communication_disabled_until", timeout.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    public GuildMemberModifyAction removeTimeout() {
        return setProperty("communication_disabled_until", NullNode.instance);
    }

    public GuildMemberModifyAction setFlags(GuildMemberFlag... flags) {
        long value = 0;
        for (GuildMemberFlag flag : flags) {
            value |= flag.getValue();
        }
        return setProperty("flags", value);
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, "/guilds/" + guildId + "/members/" + userId);
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
