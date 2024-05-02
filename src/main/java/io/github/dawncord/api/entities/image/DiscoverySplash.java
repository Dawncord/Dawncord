package io.github.dawncord.api.entities.image;

import io.github.dawncord.api.Routes;
import io.github.dawncord.api.types.ImageFormat;

/**
 * Represents a discovery splash associated with a guild.
 */
public class DiscoverySplash implements Icon {
    private final String guildId;
    private final String hash;

    /**
     * Constructs a DiscoverySplash object associated with a guild.
     *
     * @param guildId The ID of the guild.
     * @param hash    The hash value of the discovery splash.
     */
    public DiscoverySplash(String guildId, String hash) {
        this.guildId = guildId;
        this.hash = hash;
    }

    @Override
    public String getUrl(ImageFormat format) {
        return Routes.Icon.DiscoverySplash(guildId, hash, format.getExtension());
    }
}
