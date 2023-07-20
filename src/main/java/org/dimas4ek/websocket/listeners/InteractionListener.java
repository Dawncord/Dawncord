package org.dimas4ek.websocket.listeners;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import org.dimas4ek.event.Event;
import org.dimas4ek.event.listeners.EventListener;
import org.dimas4ek.event.slashcommand.interaction.SlashCommandInteractionEventImpl;
import org.dimas4ek.interaction.Interaction;
import org.dimas4ek.interaction.InteractionImpl;
import org.json.JSONException;
import org.json.JSONObject;

public class InteractionListener extends WebSocketAdapter {
    @Override
    public void onTextMessage(WebSocket websocket, String message) throws Exception {
        
        // Parse the message as a JSON object
        JSONObject json;
        try {
            json = new JSONObject(message);
        } catch (JSONException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            return;
        }
        
        int op = json.getInt("op");
        
        if (op == 0) {
            JSONObject d = json.getJSONObject("d");
            String type = json.getString("t");
            
            String interactionId = d.getString("id");
            String interactionToken = d.getString("token");
            
            String guildId = d.getString("guild_id");
            String channelId = d.getString("channel_id");
            String commandName = d.getJSONObject("data").getString("name");
            
            if (type.equals("INTERACTION_CREATE")) {
                
                Interaction guildInteraction = new InteractionImpl(commandName, guildId, channelId);
                
                SlashCommandInteractionEventImpl slashCommandInteractionEvent =
                    new SlashCommandInteractionEventImpl(interactionId, interactionToken, guildInteraction);
                
                for (Event listener : EventListener.getEventListeners()) {
                    listener.onEvent((slashCommandInteractionEvent));
                }
            }
        }
    }
    
    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) {
        System.out.println("Closed: " + serverCloseFrame.getCloseReason() + " " + serverCloseFrame.getCloseReason());
    }
    
    @Override
    public void onError(WebSocket websocket, WebSocketException cause) {
        System.err.println("Error: " + cause.getMessage());
    }
}
