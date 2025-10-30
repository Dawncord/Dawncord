package io.github.dawncord.api.entities.application.team;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.UserImpl;
import io.github.dawncord.api.types.MembershipState;
import io.github.dawncord.api.types.TeamMemberRole;
import io.github.dawncord.api.utils.EnumUtils;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.LazyLoader;

/**
 * Represents an implementation of the TeamMember interface.
 * TeamMemberImpl is a class implementing the TeamMember interface and providing methods to access team member properties.
 */
public class TeamMember {
    private final LazyLoader loader;
    private final JsonNode member;
    private final String ownerId;
    private User user;
    private TeamMemberRole role;
    private MembershipState membershipState;

    /**
     * Constructs a TeamMemberImpl object with the provided JSON node containing member information and the associated team.
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
        user = loader.load(user, () -> new UserImpl(JsonUtils.fetch(Routes.User(getMemberId()))));
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

    private String getMemberId() {
        return member.get("user").get("id").asText();
    }
}
