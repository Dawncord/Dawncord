package org.dimas4ek.wrapper.entities.application.team;

import org.dimas4ek.wrapper.entities.ISnowflake;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.image.TeamIcon;

import java.util.List;

public interface Team extends ISnowflake {
    String getName();

    List<TeamMember> getMembers();

    TeamIcon getIcon();

    User getOwner();
}
