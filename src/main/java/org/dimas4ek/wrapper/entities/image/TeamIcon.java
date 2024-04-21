package org.dimas4ek.wrapper.entities.image;

import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.types.ImageFormat;

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
