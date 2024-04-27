package org.dimas4ek.dawncord.entities.channel;

import org.dimas4ek.dawncord.entities.User;
import org.dimas4ek.dawncord.entities.application.Application;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.entities.guild.event.GuildScheduledEvent;
import org.dimas4ek.dawncord.types.TargetType;

import java.time.ZonedDateTime;

public interface Invite {
    Guild getGuild();

    String getCode();

    GuildChannel getChannel();

    User getInviter();

    TargetType getTargetType();

    User getTargetUser();

    Application getTargetApplication();

    int getOnlineMembersCount();

    int getTotalMembersCount();

    ZonedDateTime getCreationTimestamp();

    ZonedDateTime getExpirationTimestamp();

    GuildScheduledEvent getGuildEvent();

    int getMaxAge();

    int getUses();

    boolean isTemporary();

    void delete();
}
