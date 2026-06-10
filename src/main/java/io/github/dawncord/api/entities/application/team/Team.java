package io.github.dawncord.api.entities.application.team;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.image.TeamIcon;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.LazyLoader;

import java.util.List;

/**
 * Represents an implementation of the Team interface.
 * TeamImpl is a class implementing the Team interface and providing methods to access team properties.
 */
public class Team implements ISnowflake {
    private final LazyLoader loader;
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
    public Team(JsonNode team) {
        this.team = team;
        loader = new LazyLoader(team);
    }

    @Override
    public String getId() {
        id = loader.loadString(id, "id");
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    public String getName() {
        name = loader.loadString(name, "name");
        return name;
    }

    public TeamIcon getIcon() {
        icon = loader.loadIfExists(icon, "icon", () -> new TeamIcon(id, team.get("icon").asText()));
        return icon;
    }

    public User getOwner() {
        owner = loader.loadIfExists(owner, "owner_user_id",
                () -> new User(JsonUtils.fetch(Routes.User(team.get("owner_user_id").asText()))));
        return owner;
    }

    public List<TeamMember> getMembers() {
        members = loader.loadIfExists(members, "members",
                () -> JsonUtils.getEntityList(team.get("members"), member -> new TeamMember(member, getOwner().getId())));
        return members;
    }
}
