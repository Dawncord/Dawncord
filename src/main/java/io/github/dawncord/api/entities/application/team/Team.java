package io.github.dawncord.api.entities.application.team;

import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.image.TeamIcon;

import java.util.List;

/**
 * Represents a team entity.
 * Team is an interface providing methods to access properties of a team.
 */
public interface Team extends ISnowflake {

    /**
     * Retrieves the name of the team.
     *
     * @return The name of the team.
     */
    String getName();

    /**
     * Retrieves the icon of the team.
     *
     * @return The icon of the team.
     */
    TeamIcon getIcon();

    /**
     * Retrieves the owner of the team.
     *
     * @return The owner of the team.
     */
    User getOwner();

    /**
     * Retrieves the members of the team.
     *
     * @return The list of members in the team.
     */
    List<TeamMember> getMembers();
}
