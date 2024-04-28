package org.dimas4ek.dawncord.entities.application.team;

import org.dimas4ek.dawncord.entities.User;
import org.dimas4ek.dawncord.types.MembershipState;
import org.dimas4ek.dawncord.types.TeamMemberRole;

public interface TeamMember {
    User getUser();

    TeamMemberRole getRole();

    MembershipState getMembershipState();
}
