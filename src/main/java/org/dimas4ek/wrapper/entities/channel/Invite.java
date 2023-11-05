package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.application.Application;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.event.GuildEvent;
import org.dimas4ek.wrapper.types.TargetType;

import java.time.ZonedDateTime;

public interface Invite {
    String getCode();

    Guild getGuild();

    GuildChannel getChannel();

    User getInviter();

    TargetType getTargetType();

    User getTargetUser();

    Application getTargetApplication();

    int getOnlineMembersCount();

    int getTotalMembersCount();

    ZonedDateTime getCreationTimestamp();

    ZonedDateTime getExpirationTimestamp();

    GuildEvent getGuildScheduledEvent();

    int getMaxAge();

    int getUses();

    boolean isTemporary();
}
