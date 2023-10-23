package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.action.ChannelModifyAction;
import org.json.JSONObject;

public class GuildChannelImpl extends ChannelImpl implements GuildChannel {
    private final JSONObject channel;

    public GuildChannelImpl(JSONObject channel) {
        super(channel);
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

    @Override
    public ChannelModifyAction modify() {
        return new ChannelModifyAction(this);
    }
}
