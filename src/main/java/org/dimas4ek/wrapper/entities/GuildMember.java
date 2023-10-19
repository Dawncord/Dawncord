package org.dimas4ek.wrapper.entities;

import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.role.GuildRole;

import java.time.ZonedDateTime;
import java.util.List;

public interface GuildMember {
    String getNickname();

    Avatar getAvatar();

    List<String> getFlags();

    ZonedDateTime getTimeJoined();

    List<String> getPermissions();

    List<GuildRole> getRoles();

    boolean isPending();

    boolean isOwner();

    boolean isBoosting();

    boolean isMuted();

    boolean isDeafened();

    Guild getGuild();

    User getUser();
}
