package org.dimas4ek.wrapper.entities.image;

import org.dimas4ek.wrapper.Constants;
import org.dimas4ek.wrapper.types.ImageFormat;

public class Splash implements Icon {
    private final String guildId;
    private final String hash;

    public Splash(String guildId, String hash) {
        this.guildId = guildId;
        this.hash = hash;
    }

    @Override
    public String getUrl(ImageFormat format) {
        return Constants.CDN_URL + "/splashes/" + guildId + "/" + hash + format.getFormat();
    }

    @Override
    public void download() {

    }
}
