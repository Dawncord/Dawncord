package org.dimas4ek.dawncord.entities.image;

import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.types.ImageFormat;

public class Avatar implements Icon {
    private final String guildId;
    private final String userId;
    private final String hash;

    public Avatar(String userId, String hash) {
        this.guildId = null;
        this.userId = userId;
        this.hash = hash;
    }

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
