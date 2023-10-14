package org.dimas4ek.wrapper.listeners;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import org.dimas4ek.wrapper.Dawncord;
import org.dimas4ek.wrapper.events.SlashCommandEvent;
import org.dimas4ek.wrapper.events.SlashCommandEventImpl;
import org.json.JSONException;
import org.json.JSONObject;

public class SlashCommandListener extends WebSocketAdapter {
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

            String guildId = d.getString("guild_id");
            String channelId = d.getString("channel_id");

            if (type.equals("INTERACTION_CREATE")) {
                JSONObject data = d.getJSONObject("data");
                String commandName = data.getString("name");

                String interactionId = d.getString("id");
                String interactionToken = d.getString("token");

                SlashCommandEvent slashCommandEvent = new SlashCommandEventImpl(commandName, interactionId, interactionToken);

                Dawncord.processSlashCommand(slashCommandEvent);
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
