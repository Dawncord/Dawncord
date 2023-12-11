package org.dimas4ek.wrapper.entities.application.team;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.entities.image.TeamIcon;
import org.dimas4ek.wrapper.utils.JsonUtils;

import java.util.List;

public class TeamImpl implements Team {
    private final JsonNode team;
    private String id;
    private String name;
    private TeamIcon icon;
    private User owner;
    private List<TeamMember> members;

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
            owner = new UserImpl(JsonUtils.fetchEntity("/users/" + team.get("owner_user_id").asText()));
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
