package org.dimas4ek.wrapper.entities.image;

import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.types.ImageFormat;

public class GuildEventImage implements Icon {
    private final String eventId;
    private final String hash;

    public GuildEventImage(String eventId, String hash) {
        this.eventId = eventId;
        this.hash = hash;
    }

    @Override
    public String getUrl(ImageFormat format) {
        return Routes.Icon.GuildEventImage(eventId, hash, format.getExtension());
    }
}
