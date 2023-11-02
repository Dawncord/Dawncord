package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.types.GuildScheduledEventEntityType;
import org.dimas4ek.wrapper.types.GuildScheduledEventPrivacyLevel;
import org.dimas4ek.wrapper.types.GuildScheduledEventStatus;

import java.time.ZonedDateTime;

public interface GuildScheduledEvent {
    String getId();

    long getIdLong();

    String getName();

    String getDescription();

    Guild getGuild();

    GuildChannel getChannel();

    User getCreator();

    ZonedDateTime getStartTimestamp();

    ZonedDateTime getEndTimestamp();

    GuildScheduledEventPrivacyLevel getPrivacyLevel();

    GuildScheduledEventStatus getStatus();

    GuildScheduledEventEntityType getEntityType();

    //int getEntityId(); todo add entity

    //String getLocation(); todo check location

    int getMemberCount();

    //List<GuildScheduledEventMember> getMembers(); todo add members

    //GuildScheduledEventImage getImage(); todo add image
}
