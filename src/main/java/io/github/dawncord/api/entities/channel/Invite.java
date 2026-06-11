package io.github.dawncord.api.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.application.Application;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.event.GuildScheduledEvent;
import io.github.dawncord.api.types.TargetType;
import io.github.dawncord.api.utils.InviteType;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.LazyLoader;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 * Represents an invite to a guild or channel.
 */
public class Invite {
    private final LazyLoader loader;
    private final JsonNode invite;
    private final Guild guild;
    private InviteType type;
    private String code;
    private GuildChannel channel;
    private User inviter;
    private TargetType targetType;
    private User targetUser;
    private Application targetApplication;
    private Integer onlineMembersCount;
    private Integer totalMembersCount;
    private GuildScheduledEvent guildEvent;
    private ZonedDateTime expirationTimestamp;

    /**
     * Constructs a new Invite with the provided JSON invite and guild.
     *
     * @param invite The JSON node representing the invite.
     * @param guild  The guild associated with this invite.
     */
    public Invite(JsonNode invite, Guild guild) {
        this.invite = invite;
        this.guild = guild;
        loader = new LazyLoader(invite);
    }

    public Guild getGuild() {
        return guild;
    }

    public InviteType getType() {
        type = loader.loadEnumObject(type, "type", InviteType.class);
        return type;
    }

    public String getCode() {
        code = loader.loadString(code, "code");
        return code;
    }

    public GuildChannel getChannel() {
        channel = loader.loadIfExists(channel, "channel", () -> {
            channel = guild.getChannelById(invite.get("channel").get("id").asText());
            return channel;
        });
        return channel;
    }

    public User getInviter() {
        inviter = loader.loadIfExists(inviter, "inviter", () -> new User(invite.get("inviter")));
        return inviter;
    }

    public TargetType getTargetType() {
        targetType = loader.loadEnumObject(targetType, "type", TargetType.class);
        return targetType;
    }

    public User getTargetUser() {
        targetUser = loader.loadIfExists(targetUser, "target_user",
                () -> new User(invite.get("target_user")));
        return targetUser;
    }

    public Application getTargetApplication() {
        targetApplication = loader.loadIfExists(targetApplication, "target_application",
                () -> new Application(invite.get("target_application")));
        return targetApplication;
    }

    public int getOnlineMembersCount() {
        onlineMembersCount = loader.load(onlineMembersCount, () -> {
            onlineMembersCount =
                    JsonUtils.fetchParams(
                            Routes.Channel.Invite.Get(code),
                            Map.of("with_counts", "true")
                    ).get("approximate_presence_count").asInt();
            return onlineMembersCount;
        });
        return onlineMembersCount;
    }

    public int getTotalMembersCount() {
        totalMembersCount = loader.load(totalMembersCount, () -> {
            totalMembersCount =
                    JsonUtils.fetchParams(
                            Routes.Channel.Invite.Get(code),
                            Map.of("with_counts", "true")
                    ).get("approximate_member_count").asInt();
            return totalMembersCount;
        });
        return totalMembersCount;
    }

    public GuildScheduledEvent getGuildEvent() {
        guildEvent = loader.loadIfExists(guildEvent, "guild_scheduled_event", GuildScheduledEvent::new);
        return guildEvent;
    }

    public ZonedDateTime getExpirationTimestamp() {
        expirationTimestamp = loader.loadZonedDateTime(expirationTimestamp, "expires_at");
        return expirationTimestamp;
    }

    public void delete() {
        ApiClient.delete(Routes.Channel.Invite.Get(getCode()));
    }
}
