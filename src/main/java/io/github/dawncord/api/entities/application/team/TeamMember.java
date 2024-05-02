package io.github.dawncord.api.entities.application.team;

import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.types.MembershipState;
import io.github.dawncord.api.types.TeamMemberRole;

/**
 * Represents a member of a team.
 * TeamMember is an interface providing methods to access properties of a team member.
 */
public interface TeamMember {

    /**
     * Retrieves the user associated with this team member.
     *
     * @return The user associated with this team member.
     */
    User getUser();

    /**
     * Retrieves the role of this team member.
     *
     * @return The role of this team member.
     */
    TeamMemberRole getRole();

    /**
     * Retrieves the membership state of this team member.
     *
     * @return The membership state of this team member.
     */
    MembershipState getMembershipState();
}
