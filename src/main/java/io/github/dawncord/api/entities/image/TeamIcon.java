package io.github.dawncord.api.entities.image;

import io.github.dawncord.api.Routes;
import io.github.dawncord.api.types.ImageFormat;

/**
 * Represents an icon associated with a team.
 */
public class TeamIcon implements Icon {
    private final String teamId;
    private final String hash;

    /**
     * Constructs a TeamIcon object associated with a team.
     *
     * @param teamId The ID of the team.
     * @param hash   The hash value of the team icon.
     */
    public TeamIcon(String teamId, String hash) {
        this.teamId = teamId;
        this.hash = hash;
    }

    @Override
    public String getUrl(ImageFormat format) {
        return Routes.Icon.TeamIcon(teamId, hash, format.getExtension());
    }
}
