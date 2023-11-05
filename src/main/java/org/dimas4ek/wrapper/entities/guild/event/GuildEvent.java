package org.dimas4ek.wrapper.entities.guild.event;

import org.dimas4ek.wrapper.action.GuildEventModifyAction;
import org.dimas4ek.wrapper.entities.GuildMember;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.types.GuildEventEntityType;
import org.dimas4ek.wrapper.types.GuildEventPrivacyLevel;
import org.dimas4ek.wrapper.types.GuildEventStatus;

import java.time.ZonedDateTime;
import java.util.List;

public interface GuildEvent {
    String getId();

    long getIdLong();

    String getName();

    String getDescription();

    Guild getGuild();

    GuildChannel getChannel();

    User getCreator();

    ZonedDateTime getStartTimestamp();

    ZonedDateTime getEndTimestamp();

    GuildEventPrivacyLevel getPrivacyLevel();

    GuildEventStatus getStatus();

    GuildEventEntityType getEntityType();

    boolean inChannel();

    int getEntityId();

    String getLocation();

    int getMemberCount();

    List<GuildMember> getGuildEventMembers();

    List<GuildMember> getGuildEventMembers(int limit);

    GuildEventImage getImage();

    GuildEventModifyAction modify();

    void delete();
}
