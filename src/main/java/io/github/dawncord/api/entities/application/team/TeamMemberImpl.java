package io.github.dawncord.api.entities.application.team;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.UserImpl;
import io.github.dawncord.api.types.MembershipState;
import io.github.dawncord.api.types.TeamMemberRole;
import io.github.dawncord.api.utils.EnumUtils;
import io.github.dawncord.api.utils.JsonUtils;

/**
 * Represents an implementation of the TeamMember interface.
 * TeamMemberImpl is a class implementing the TeamMember interface and providing methods to access team member properties.
 */
public class TeamMemberImpl implements TeamMember {
    private final JsonNode member;
    private final Team team;
    private User user;
    private TeamMemberRole role;
    private MembershipState membershipState;

    /**
     * Constructs a TeamMemberImpl object with the provided JSON node containing member information and the associated team.
     *
     * @param member The JSON node containing member information.
     * @param team   The associated team.
     */
    public TeamMemberImpl(JsonNode member, Team team) {
        this.member = member;
        this.team = team;
    }

    @Override
    public User getUser() {
        if (user == null) {
            user = new UserImpl(JsonUtils.fetch(Routes.User(member.get("user").get("id").asText())));
        }
        return user;
    }

    @Override
    public TeamMemberRole getRole() {
        if (role == null) {
            if (getUser().getId().equals(team.getOwner().getId())) {
                role = TeamMemberRole.OWNER;
            } else {
                role = EnumUtils.getEnumObject(member, "role", TeamMemberRole.class);
            }
        }
        return role;
    }

    @Override
    public MembershipState getMembershipState() {
        if (membershipState == null) {
            membershipState = EnumUtils.getEnumObject(member, "membership_state", MembershipState.class);
        }
        return membershipState;
    }
}
