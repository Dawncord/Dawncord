package org.dimas4ek.wrapper.entities.channel;

import org.json.JSONObject;

public class GuildCategoryImpl extends ChannelImpl implements GuildCategory {
    public GuildCategoryImpl(JSONObject channel) {
        super(channel);
    }
}
