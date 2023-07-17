package org.dimas4ek.websocket.listeners;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.dimas4ek.commands.SlashCommand;
import org.dimas4ek.event.listeners.EventListener;
import org.dimas4ek.utils.Constants;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainListener extends WebSocketAdapter {
    @Override
    public void onTextMessage(WebSocket websocket, String message) throws Exception {
        JSONObject jsonObject = new JSONObject(message);
        System.out.println(jsonObject.toString(4));
        
        // Parse the message as a JSON object
        JSONObject json;
        try {
            json = new JSONObject(message);
        } catch (JSONException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            return;
        }
        
        for (SlashCommand command : EventListener.getEventListeners()) {
            RegisterSlashCommands.register(command.name(), command.description());
        }
        
        int op = json.getInt("op");
        if (op == 10) {
            JSONObject d = json.getJSONObject("d");
            int heartbeatInterval = d.getInt("heartbeat_interval");
            System.out.println(heartbeatInterval);
            
            JSONObject payload = new JSONObject()
                .put("op", 1)
                .put("d", JSONObject.NULL);
            
            websocket.sendText(payload.toString());
            System.out.println("Sending heartbeat every " + heartbeatInterval + " ms...");
            
            // Send a heartbeat message at the specified interval
            Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
                websocket.sendText(payload.toString());
                System.out.println("Sending heartbeat...");
            }, heartbeatInterval, heartbeatInterval, TimeUnit.MILLISECONDS);
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
    
    private static class RegisterSlashCommands {
        private static void register(String name, String description) {
            OkHttpClient client = new OkHttpClient();
            JSONObject requestBody = new JSONObject();
            requestBody.put("name", name);
            requestBody.put("description", description);
            RequestBody body = RequestBody.create(requestBody.toString(), okhttp3.MediaType.parse("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                .post(body)
                .url("https://discord.com/api/v10/applications/" + Constants.APPLICATION_ID + "/commands")
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
                .addHeader("Content-Type", "application/json")
                .build();
            
            try {
                client.newCall(request).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}