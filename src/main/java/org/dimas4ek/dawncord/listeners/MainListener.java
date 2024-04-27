package org.dimas4ek.dawncord.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainListener extends WebSocketAdapter {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        JsonNode json = mapper.readTree(text);
        int op = json.get("op").asInt();
        if (op == 10) {
            JsonNode d = json.get("d");
            int heartbeatInterval = d.get("heartbeat_interval").asInt();

            JsonNode payload = mapper.createObjectNode()
                    .put("op", 1)
                    .putNull("d");

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
