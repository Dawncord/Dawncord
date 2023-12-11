package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.application.Application;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.event.GuildEvent;
import org.dimas4ek.wrapper.types.TargetType;

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

    GuildEvent getGuildEvent();

    int getMaxAge();

    int getUses();

    boolean isTemporary();

    void delete();
}
