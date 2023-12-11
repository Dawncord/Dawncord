package org.dimas4ek.wrapper.entities.guild.widget;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.entities.ISnowflake;
import org.dimas4ek.wrapper.entities.image.Avatar;
import org.dimas4ek.wrapper.types.OnlineStatus;
import org.dimas4ek.wrapper.utils.EnumUtils;

public class GuildWidgetMember implements ISnowflake {
    private final JsonNode member;
    private String id;
    private String username;
    private Avatar avatar;
    private OnlineStatus onlineStatus;

    public GuildWidgetMember(JsonNode member) {
        this.member = member;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = member.get("id").asText();
        }
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    public String getUsername() {
        if (username == null) {
            username = member.get("username").asText();
        }
        return username;
    }

    public Avatar getAvatar() {
        if (avatar == null) {
            avatar = member.has("avatar") && member.hasNonNull("avatar")
                    ? new Avatar(getId(), member.get("avatar").asText())
                    : null;
        }
        return avatar;
    }

    public OnlineStatus getOnlineStatus() {
        if (onlineStatus == null) {
            onlineStatus = EnumUtils.getEnumObject(member, "status", OnlineStatus.class);
        }
        return onlineStatus;
    }
}
