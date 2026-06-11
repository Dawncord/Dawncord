package io.github.dawncord.api.entities.application.team;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.types.MembershipState;
import io.github.dawncord.api.types.TeamMemberRole;
import io.github.dawncord.api.utils.EnumUtils;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.LazyLoader;

/**
 * Represents a member of an application team and provides access to member properties.
 */
public class TeamMember {
    private final LazyLoader loader;
    private final JsonNode member;
    private final String ownerId;
    private User user;
    private TeamMemberRole role;
    private MembershipState membershipState;

    /**
     * Constructs a TeamMember object with the provided JSON node containing member information and the associated team.
     *
     * @param member  The JSON node containing member information.
     * @param ownerId The id of the associated team owner.
     */
    public TeamMember(JsonNode member, String ownerId) {
        this.member = member;
        this.ownerId = ownerId;
        loader = new LazyLoader(member);
    }

    public User getUser() {
        user = loader.load(user, () -> new User(JsonUtils.fetch(Routes.User(getUserId()))));
        return user;
    }

    public TeamMemberRole getRole() {
        role = loader.load(role, () -> {
            if (getUser().getId().equals(ownerId)) {
                role = TeamMemberRole.OWNER;
            } else {
                role = EnumUtils.getEnumObject(member, "role", TeamMemberRole.class);
            }
            return role;
        });
        return role;
    }

    public MembershipState getMembershipState() {
        membershipState = loader.loadEnumObject(membershipState, "membership_state", MembershipState.class);
        return membershipState;
    }

    private String getUserId() {
        return member.get("user").get("id").asText();
    }
}
