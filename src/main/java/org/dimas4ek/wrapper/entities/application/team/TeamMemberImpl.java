package org.dimas4ek.wrapper.entities.application.team;

import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.types.MembershipState;
import org.dimas4ek.wrapper.types.TeamMemberRole;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONObject;

public class TeamMemberImpl implements TeamMember {
    private final Team team;
    private final JSONObject member;

    public TeamMemberImpl(Team team, JSONObject member) {
        this.team = team;
        this.member = member;
    }

    @Override
    public User getUser() {
        return new UserImpl(JsonUtils.fetchEntity("/users/" + member.getJSONObject("user").getString("id")));
    }

    @Override
    public TeamMemberRole getRole() {
        for (TeamMemberRole role : TeamMemberRole.values()) {
            if (getUser().getId().equals(team.getOwner().getId())) {
                return TeamMemberRole.OWNER;
            } else if (role.getValue().equals(member.getString("role"))) {
                return role;
            }
        }
        return null;
    }

    @Override
    public MembershipState getMembershipState() {
        return EnumUtils.getEnumObject(member, "membership_state", MembershipState.class);
        /*for (MembershipState state : MembershipState.values()) {
            if (state.getValue() == member.getInt("membership_state")) {
                return state;
            }
        }
        return null;*/
    }
}
