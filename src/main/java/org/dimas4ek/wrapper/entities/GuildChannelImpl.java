package org.dimas4ek.wrapper.entities;

import org.dimas4ek.wrapper.ApiClient;
import org.json.JSONObject;

public class GuildChannelImpl implements GuildChannel {
    private final JSONObject channel;

    public GuildChannelImpl(JSONObject channel) {
        this.channel = channel;
    }

    @Override
    public String getId() {
        return channel.getString("id");
    }

    @Override
    public void sendMessage(String message) {
        JSONObject jsonObject = new JSONObject()
                .put("content", message);
        ApiClient.sendResponse(getId(), jsonObject);
    }
}
