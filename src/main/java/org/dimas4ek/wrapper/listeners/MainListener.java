package org.dimas4ek.wrapper.listeners;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainListener extends WebSocketAdapter {
    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        JSONObject json;
        try {
            json = new JSONObject(text);
            System.out.println(json.toString(4));
        } catch (JSONException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            return;
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
}
