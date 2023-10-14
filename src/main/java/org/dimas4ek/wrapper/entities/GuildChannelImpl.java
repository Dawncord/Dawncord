package org.dimas4ek.wrapper.entities;

import org.dimas4ek.wrapper.ApiClient;
import org.json.JSONObject;

public class GuildChannelImpl implements GuildChannel {
    private final String channelId;

    public GuildChannelImpl(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public void sendMessage(String message) {
        JSONObject jsonObject = new JSONObject()
                .put("content", message);
        ApiClient.sendResponse(channelId, jsonObject);
    }
}
