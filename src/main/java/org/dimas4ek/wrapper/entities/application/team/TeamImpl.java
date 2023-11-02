package org.dimas4ek.wrapper.entities.application.team;

import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONObject;

import java.util.List;

public class TeamImpl implements Team {
    private final JSONObject team;

    public TeamImpl(JSONObject team) {
        this.team = team;
    }

    @Override
    public String getId() {
        return team.getString("id");
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        return team.getString("name");
    }

    @Override
    public List<TeamMember> getMembers() {
        return JsonUtils.getEntityList(team.getJSONArray("members"), (JSONObject t) -> new TeamMemberImpl(this, t));
    }

    @Override
    public TeamIcon getIcon() {
        return team.has("icon")
                ? new TeamIcon(getId(), team.getString("icon"))
                : null;
    }

    @Override
    public User getOwner() {
        return new UserImpl(JsonUtils.fetchEntity("/users/" + team.getString("owner_user_id")));
    }
}
