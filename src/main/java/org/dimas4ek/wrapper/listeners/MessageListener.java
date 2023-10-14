package org.dimas4ek.wrapper.listeners;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import org.dimas4ek.wrapper.Dawncord;
import org.dimas4ek.wrapper.entities.*;
import org.dimas4ek.wrapper.events.MessageEventImpl;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageListener extends WebSocketAdapter {
    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        JSONObject json;
        try {
            json = new JSONObject(text);
        } catch (JSONException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            return;
        }

        int op = json.getInt("op");

        if (op == 0) {
            JSONObject d = json.getJSONObject("d");
            String type = json.getString("t");

            String id = d.getString("id");

            String guildId = d.getString("guild_id");
            String channelId = d.getString("channel_id");

            if (type.equals("MESSAGE_CREATE")) {
                String content = d.getString("content");
                JSONObject author = d.getJSONObject("author");
                String userId = author.getString("id");

                User user = new UserImpl(userId);

                GuildChannel guildChannel = new GuildChannelImpl(channelId);

                Message message = new MessageImpl(content, user);
                MessageEventImpl messageEvent = new MessageEventImpl(message, guildChannel);

                Dawncord.processMessage(messageEvent);
            }
        }
    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) {
        System.out.println("Closed: " + serverCloseFrame.getCloseReason());
    }

    @Override
    public void onError(WebSocket websocket, WebSocketException cause) {
        System.err.println("Error: " + cause.getMessage());
    }
}
