package io.github.dawncord.api.entities.image;

import io.github.dawncord.api.Routes;
import io.github.dawncord.api.types.ImageFormat;

/**
 * Represents a splash image associated with a guild.
 */
public class Splash implements Icon {
    private final String guildId;
    private final String hash;

    /**
     * Constructs a Splash object associated with a guild.
     *
     * @param guildId The ID of the guild.
     * @param hash    The hash value of the splash image.
     */
    public Splash(String guildId, String hash) {
        this.guildId = guildId;
        this.hash = hash;
    }

    @Override
    public String getUrl(ImageFormat format) {
        return Routes.Icon.Splash(guildId, hash, format.getExtension());
    }
}
