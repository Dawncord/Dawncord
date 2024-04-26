package org.dimas4ek.wrapper.entities.application.team;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.types.MembershipState;
import org.dimas4ek.wrapper.types.TeamMemberRole;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;

public class TeamMemberImpl implements TeamMember {
    private final JsonNode member;
    private final Team team;
    private User user;
    private TeamMemberRole role;
    private MembershipState membershipState;

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
