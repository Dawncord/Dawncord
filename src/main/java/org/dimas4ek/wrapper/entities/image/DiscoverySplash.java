package org.dimas4ek.wrapper.entities.image;

import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.types.ImageFormat;

public class DiscoverySplash implements Icon {
    private final String guildId;
    private final String hash;

    public DiscoverySplash(String guildId, String hash) {
        this.guildId = guildId;
        this.hash = hash;
    }

    @Override
    public String getUrl(ImageFormat format) {
        return Routes.Icon.DiscoverySplash(guildId, hash, format.getExtension());
    }
}
