package org.dimas4ek.dawncord.entities.application.team;

import org.dimas4ek.dawncord.entities.ISnowflake;
import org.dimas4ek.dawncord.entities.User;
import org.dimas4ek.dawncord.entities.image.TeamIcon;

import java.util.List;

public interface Team extends ISnowflake {
    String getName();

    TeamIcon getIcon();

    User getOwner();

    List<TeamMember> getMembers();
}
