package org.dimas4ek.wrapper.entities.guild.automod;

import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.GuildChannelImpl;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONObject;

public class ActionMetadata {
    private final JSONObject metadata;

    public ActionMetadata(JSONObject metadata) {
        this.metadata = metadata;
    }

    public GuildChannel getChannel() {
        return new GuildChannelImpl(JsonUtils.fetchEntity("/channels/" + metadata.getString("channel_id")));
    }

    public int getDuration() {
        return metadata.getInt("duration_seconds");
    }

    public String getCustomMessage() {
        return metadata.optString("custom_message", null);
    }
}
