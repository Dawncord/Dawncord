package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONObject;

public class VoiceChannelImpl extends MessageChannelImpl implements VoiceChannel {
    private final JSONObject channel;

    public VoiceChannelImpl(JSONObject channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public int getUserLimit() {
        return channel.getInt("user_limit");
    }

    @Override
    public int getBitrate() {
        return channel.getInt("bitrate");
    }

    @Override
    public GuildCategory getCategory() {
        return (GuildCategory) new ChannelImpl(JsonUtils.fetchEntity("/channels/" + channel.getString("parent_id")));
    }
}
