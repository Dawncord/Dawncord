package org.dimas4ek.wrapper.entities;

import org.dimas4ek.wrapper.Constants;
import org.dimas4ek.wrapper.types.ImageFormat;

public class Avatar {
    private final String guildId;
    private final String userId;
    private final String avatarHash;

    public Avatar(String userId, String avatarHash) {
        this.guildId = null;
        this.userId = userId;
        this.avatarHash = avatarHash;
    }

    public Avatar(String guildId, String userId, String avatarHash) {
        this.guildId = guildId;
        this.userId = userId;
        this.avatarHash = avatarHash;
    }

    public String getUrl(ImageFormat format) {
        return guildId != null
                ? Constants.CDN_URL + "/guilds/" + guildId + "/users/" + userId + "/avatars/" + avatarHash + format.getFormat()
                : Constants.CDN_URL + "/avatars/" + userId + "/" + avatarHash + format.getFormat();
    }

    public void download() {
        //TODO download
    }
}
