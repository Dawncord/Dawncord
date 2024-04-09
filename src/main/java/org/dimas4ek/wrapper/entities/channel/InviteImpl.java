package org.dimas4ek.wrapper.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.entities.application.Application;
import org.dimas4ek.wrapper.entities.application.ApplicationImpl;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.event.GuildScheduledEvent;
import org.dimas4ek.wrapper.entities.guild.event.GuildScheduledEventImpl;
import org.dimas4ek.wrapper.types.TargetType;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.dimas4ek.wrapper.utils.MessageUtils;

import java.time.ZonedDateTime;
import java.util.Map;

public class InviteImpl implements Invite {
    private final JsonNode invite;
    private final Guild guild;
    private String code;
    private GuildChannel channel;
    private User inviter;
    private TargetType targetType;
    private User targetUser;
    private Application targetApplication;
    private Integer onlineMembersCount;
    private Integer totalMembersCount;
    private ZonedDateTime creationTimestamp;
    private ZonedDateTime expirationTimestamp;
    private GuildScheduledEvent guildEvent;
    private Integer maxAge;
    private Integer uses;
    private Boolean isTemporary;

    public InviteImpl(JsonNode invite, Guild guild) {
        this.invite = invite;
        this.guild = guild;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    @Override
    public String getCode() {
        if (code == null) {
            code = invite.get("code").asText();
        }
        return code;
    }

    @Override
    public GuildChannel getChannel() {
        if (channel == null) {
            channel = guild.getChannelById(invite.get("channel").get("id").asText());
        }
        return channel;
    }

    @Override
    public User getInviter() {
        if (inviter == null) {
            inviter = invite.has("inviter") ? new UserImpl(invite.get("inviter")) : null;
        }
        return inviter;
    }

    @Override
    public TargetType getTargetType() {
        if (targetType == null) {
            targetType = invite.has("type") ? EnumUtils.getEnumObject(invite, "type", TargetType.class) : null;
        }
        return targetType;
    }

    @Override
    public User getTargetUser() {
        if (targetUser == null) {
            targetUser = invite.has("target_user") ? new UserImpl(invite.get("target_user")) : null;
        }
        return targetUser;
    }

    @Override
    public Application getTargetApplication() {
        if (targetApplication == null) {
            targetApplication = invite.has("target_application") ? new ApplicationImpl(invite.get("target_application"), guild) : null;
        }
        return targetApplication;
    }

    @Override
    public int getOnlineMembersCount() {
        if (onlineMembersCount == null) {
            onlineMembersCount =
                    JsonUtils.fetchEntityParams(
                            "/invites/" + getCode(),
                            Map.of("with_counts", "true")
                    ).get("approximate_presence_count").asInt();
        }
        return onlineMembersCount;
    }

    @Override
    public int getTotalMembersCount() {
        if (totalMembersCount == null) {
            totalMembersCount =
                    JsonUtils.fetchEntityParams(
                            "/invites/" + getCode(),
                            Map.of("with_counts", "true")
                    ).get("approximate_member_count").asInt();
        }
        return totalMembersCount;
    }

    @Override
    public ZonedDateTime getCreationTimestamp() {
        if (creationTimestamp == null) {
            creationTimestamp = MessageUtils.getZonedDateTime(invite, "created_at");
        }
        return creationTimestamp;
    }

    @Override
    public ZonedDateTime getExpirationTimestamp() {
        if (expirationTimestamp == null) {
            expirationTimestamp = invite.has("expires_at") ? MessageUtils.getZonedDateTime(invite, "expires_at") : null;
        }
        return expirationTimestamp;
    }

    @Override
    public GuildScheduledEvent getGuildEvent() {
        if (guildEvent == null) {
            guildEvent = invite.has("guild_scheduled_event") ? new GuildScheduledEventImpl(invite.get("guild_scheduled_event"), guild) : null;
        }
        return guildEvent;
    }

    @Override
    public int getMaxAge() {
        if (maxAge == null) {
            maxAge = invite.get("max_age").asInt();
        }
        return maxAge;
    }

    @Override
    public int getUses() {
        if (uses == null) {
            uses = invite.get("uses").asInt();
        }
        return uses;
    }

    @Override
    public boolean isTemporary() {
        if (isTemporary == null) {
            isTemporary = invite.get("temporary").asBoolean();
        }
        return isTemporary;
    }

    @Override
    public void delete() {
        ApiClient.delete("/invites/" + getCode());
    }
}
