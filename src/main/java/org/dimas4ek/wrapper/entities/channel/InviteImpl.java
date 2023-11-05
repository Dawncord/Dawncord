package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.entities.GuildEventImpl;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.entities.application.Application;
import org.dimas4ek.wrapper.entities.application.ApplicationImpl;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildImpl;
import org.dimas4ek.wrapper.entities.guild.event.GuildEvent;
import org.dimas4ek.wrapper.types.TargetType;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.dimas4ek.wrapper.utils.MessageUtils;
import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.util.Map;

public class InviteImpl implements Invite {
    private final JSONObject invite;

    public InviteImpl(JSONObject invite) {
        this.invite = invite;
    }

    @Override
    public String getCode() {
        return invite.getString("code");
    }

    @Override
    public Guild getGuild() {
        return new GuildImpl(JsonUtils.fetchEntity("/guilds/" + invite.getString("guild_id")));
    }

    @Override
    public GuildChannel getChannel() {
        return getGuild().getChannelById(invite.getJSONObject("channel").getString("id"));
    }

    @Override
    public User getInviter() {
        return new UserImpl(invite.getJSONObject("inviter"));
    }

    @Override
    public TargetType getTargetType() {
        return EnumUtils.getEnumObject(invite, "type", TargetType.class);
        /*for (TargetType type : TargetType.values()) {
            if (type.getValue() == invite.getInt("type")) {
                return type;
            }
        }
        return null;*/
    }

    @Override
    public User getTargetUser() {
        return new UserImpl(invite.getJSONObject("target_user"));
    }

    @Override
    public Application getTargetApplication() {
        return invite.has("target_application")
                ? new ApplicationImpl(invite.getJSONObject("target_application"))
                : null;
    }

    @Override
    public int getOnlineMembersCount() {
        return JsonUtils.fetchEntityParams("/invites/" + getCode(), Map.of("with_counts", "true")).getInt("approximate_presence_count");
    }

    @Override
    public int getTotalMembersCount() {
        return JsonUtils.fetchEntityParams("/invites/" + getCode(), Map.of("with_counts", "true")).getInt("approximate_member_count");
    }

    @Override
    public ZonedDateTime getCreationTimestamp() {
        return MessageUtils.getZonedDateTime(invite, "created_at");
    }

    @Override
    public ZonedDateTime getExpirationTimestamp() {
        return MessageUtils.getZonedDateTime(invite, "expires_at");
    }

    @Override
    public GuildEvent getGuildScheduledEvent() {
        return new GuildEventImpl(invite.getJSONObject("guild_scheduled_event"));
    }

    @Override
    public int getMaxAge() {
        return invite.getInt("nax_age");
    }

    @Override
    public int getUses() {
        return invite.getInt("uses");
    }

    @Override
    public boolean isTemporary() {
        return invite.getBoolean("temporary");
    }
}
