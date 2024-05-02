package io.github.dawncord.api.entities.image;

import io.github.dawncord.api.Routes;
import io.github.dawncord.api.types.ImageFormat;

/**
 * Represents an avatar associated with a user or a guild.
 */
public class Avatar implements Icon {
    private final String guildId;
    private final String userId;
    private final String hash;

    /**
     * Constructs an Avatar object associated with a user.
     *
     * @param userId The ID of the user.
     * @param hash   The hash value of the avatar.
     */
    public Avatar(String userId, String hash) {
        this.guildId = null;
        this.userId = userId;
        this.hash = hash;
    }

    /**
     * Constructs an Avatar object associated with a user in a guild.
     *
     * @param guildId The ID of the guild.
     * @param userId  The ID of the user.
     * @param hash    The hash value of the avatar.
     */
    public Avatar(String guildId, String userId, String hash) {
        this.guildId = guildId;
        this.userId = userId;
        this.hash = hash;
    }

    @Override
    public String getUrl(ImageFormat format) {
        return guildId != null
                ? Routes.Icon.GuildUserAvatar(guildId, userId, hash, format.getExtension())
                : Routes.Icon.UserAvatar(userId, hash, format.getExtension());
    }
}
