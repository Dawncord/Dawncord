package org.dimas4ek.wrapper.entities.channel;

import org.json.JSONObject;

public class GuildChannelImpl implements GuildChannel {
    private final JSONObject channel;

    public GuildChannelImpl(JSONObject channel) {
        this.channel = channel;
    }

    @Override
    public TextChannel asText() {
        return new TextChannelImpl(channel);
    }

    @Override
    public VoiceChannel asVoice() {
        return new VoiceChannelImpl(channel);
    }
}
