package org.dimas4ek.dawncord.entities.image;

import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.types.ImageFormat;

public class TeamIcon implements Icon {
    private final String teamId;
    private final String hash;

    public TeamIcon(String teamId, String hash) {
        this.teamId = teamId;
        this.hash = hash;
    }

    @Override
    public String getUrl(ImageFormat format) {
        return Routes.Icon.TeamIcon(teamId, hash, format.getExtension());
    }
}
