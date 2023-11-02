package org.dimas4ek.wrapper.entities.application.team;

import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.types.MembershipState;
import org.dimas4ek.wrapper.types.TeamMemberRole;

public interface TeamMember {
    User getUser();

    TeamMemberRole getRole();

    MembershipState getMembershipState();
}
