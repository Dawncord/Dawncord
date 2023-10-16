package org.dimas4ek.wrapper.listeners;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.Dawncord;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.GuildChannelImpl;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.entities.message.MessageImpl;
import org.dimas4ek.wrapper.events.MessageEventImpl;
import org.dimas4ek.wrapper.utils.JsonUtils;
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

            String channelId = d.getString("channel_id");

            if (type.equals("MESSAGE_CREATE")) {
                String messageId = d.getString("id");

                JSONObject channel = JsonUtils.fetchEntity("/channels/" + channelId);
                GuildChannel guildChannel = new GuildChannelImpl(channel);

                Message message = new MessageImpl(ApiClient.getJsonObject("/channels/" + channelId + "/messages/" + messageId));

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
