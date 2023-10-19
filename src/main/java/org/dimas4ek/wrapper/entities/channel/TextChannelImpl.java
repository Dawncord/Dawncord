package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.ApiClient;
import org.json.JSONObject;

public class TextChannelImpl extends ChannelImpl implements TextChannel {
    public TextChannelImpl(JSONObject channel) {
        super(channel);
    }

    @Override
    public void sendMessage(String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", message);
        ApiClient.sendResponse(getId(), jsonObject);
    }
}
