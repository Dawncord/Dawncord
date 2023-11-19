package org.dimas4ek.wrapper.entities.guild.widget;

import org.dimas4ek.wrapper.entities.ISnowflake;
import org.dimas4ek.wrapper.entities.image.Avatar;
import org.dimas4ek.wrapper.types.OnlineStatus;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.json.JSONObject;

public class GuildWidgetMember implements ISnowflake {
    private final JSONObject member;

    public GuildWidgetMember(JSONObject member) {
        this.member = member;
    }

    @Override
    public String getId() {
        return member.getString("id");
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    public String getName() {
        return member.getString("name");
    }

    public Avatar getAvatar() {
        String avatar = member.optString("avatar", null);
        return avatar != null ? new Avatar(getId(), avatar) : null;
    }

    public OnlineStatus getOnlineStatus() {
        return EnumUtils.getEnumObject(member, "status", OnlineStatus.class);
    }

    public String getAvatarUrl() {
        return member.optString("avatar_url", null);
    }
}
