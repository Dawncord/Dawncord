package org.dimas4ek.wrapper.entities.application.team;

import org.dimas4ek.wrapper.entities.User;

import java.util.List;

public interface Team {
    String getId();

    long getIdLong();

    String getName();

    List<TeamMember> getMembers();

    TeamIcon getIcon();

    User getOwner();
}
