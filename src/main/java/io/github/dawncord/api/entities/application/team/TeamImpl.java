package io.github.dawncord.api.entities.application.team;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.UserImpl;
import io.github.dawncord.api.entities.image.TeamIcon;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.List;

/**
 * Represents an implementation of the Team interface.
 * TeamImpl is a class implementing the Team interface and providing methods to access team properties.
 */
public class TeamImpl implements Team {
    private final JsonNode team;
    private String id;
    private String name;
    private TeamIcon icon;
    private User owner;
    private List<TeamMember> members;

    /**
     * Constructs a TeamImpl object with the provided JSON node containing team information.
     *
     * @param team The JSON node containing team information.
     */
    public TeamImpl(JsonNode team) {
        this.team = team;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = team.get("id").asText();
        }
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        if (name == null) {
            name = team.get("name").asText();
        }
        return name;
    }

    @Override
    public TeamIcon getIcon() {
        if (icon == null) {
            icon = team.has("icon") && team.hasNonNull("icon") ? new TeamIcon(id, team.get("icon").asText()) : null;
        }
        return icon;
    }

    @Override
    public User getOwner() {
        if (owner == null) {
            owner = new UserImpl(JsonUtils.fetch(Routes.User(team.get("owner_user_id").asText())));
        }
        return owner;
    }

    @Override
    public List<TeamMember> getMembers() {
        if (members == null) {
            members = JsonUtils.getEntityList(team.get("members"), member -> new TeamMemberImpl(member, this));
        }
        return members;
    }
}
