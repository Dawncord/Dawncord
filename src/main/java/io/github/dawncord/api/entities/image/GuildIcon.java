package io.github.dawncord.api.entities.image;

import io.github.dawncord.api.Routes;
import io.github.dawncord.api.types.ImageFormat;

/**
 * Represents an icon associated with a guild.
 */
public class GuildIcon implements Icon {
    private final String guildId;
    private final String hash;

    /**
     * Constructs a GuildIcon object associated with a guild.
     *
     * @param guildId The ID of the guild.
     * @param hash    The hash value of the guild icon.
     */
    public GuildIcon(String guildId, String hash) {
        this.guildId = guildId;
        this.hash = hash;
    }

    @Override
    public String getUrl(ImageFormat format) {
        return Routes.Icon.GuildIcon(guildId, hash, format.getExtension());
    }
}
