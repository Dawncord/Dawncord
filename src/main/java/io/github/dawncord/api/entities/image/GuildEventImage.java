package io.github.dawncord.api.entities.image;

import io.github.dawncord.api.Routes;
import io.github.dawncord.api.types.ImageFormat;

/**
 * Represents an image associated with a guild event.
 */
public class GuildEventImage implements Icon {
    private final String eventId;
    private final String hash;

    /**
     * Constructs a GuildEventImage object associated with a guild event.
     *
     * @param eventId The ID of the guild event.
     * @param hash    The hash value of the event image.
     */
    public GuildEventImage(String eventId, String hash) {
        this.eventId = eventId;
        this.hash = hash;
    }

    @Override
    public String getUrl(ImageFormat format) {
        return Routes.Icon.GuildEventImage(eventId, hash, format.getExtension());
    }
}
