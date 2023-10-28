package org.dimas4ek.wrapper.entities.image;

import org.dimas4ek.wrapper.Constants;
import org.dimas4ek.wrapper.types.ImageFormat;

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
                ? Constants.CDN_URL + "/guilds/" + guildId + "/users/" + userId + "/avatars/" + hash + format.getFormat()
                : Constants.CDN_URL + "/avatars/" + userId + "/" + hash + format.getFormat();
    }

    @Override
    public void download() {
        //TODO download
    }
}
