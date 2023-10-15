package org.dimas4ek.wrapper.entities.channel;

import org.json.JSONObject;

public class TextChannelImpl extends IChannel implements TextChannel{
    public TextChannelImpl(JSONObject channel) {
        super(channel);
    }

    @Override
    public void sendMessage(String message) {

    }
}
