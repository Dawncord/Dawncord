package io.github.dawncord.api.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import io.github.dawncord.api.Constants;
import io.github.dawncord.api.types.GatewayCloseEventCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainListener extends WebSocketAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MainListener.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private ScheduledExecutorService executor;
    private int heartbeatInterval;

    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        JsonNode json = mapper.readTree(text);

        if (Constants.DEV_LOGGING) {
            System.out.println("MAIN LISTENER");
            System.out.println(json.toPrettyString());
        }
        
        int op = json.get("op").asInt();
        if (op == 10) {
            sendInterval(websocket, json);
        }
        if (json.get("t").asText().equals("READY")) {
            Constants.SESSION_ID = json.get("d").get("session_id").asText();
            Constants.RESUME_URL = json.get("d").get("resume_gateway_url").asText();
            sendIntervals(websocket);
        }
        Constants.LAST_SEQUENCE = json.get("s").asInt();
    }

    private void sendIntervals(WebSocket websocket) {
        logger.info("Successfully logged in");

        JsonNode payload = mapper.createObjectNode()
                .put("op", 1)
                .putNull("d");

        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            websocket.sendText(payload.toString());
            logger.debug("Sending heartbeat...");
        }, heartbeatInterval, heartbeatInterval, TimeUnit.MILLISECONDS);
    }

    private void sendInterval(WebSocket websocket, JsonNode json) {
        JsonNode d = json.get("d");
        heartbeatInterval = d.get("heartbeat_interval").asInt();

        logger.info("Sending heartbeat every {} ms...", heartbeatInterval);

        JsonNode payload = mapper.createObjectNode()
                .put("op", 1)
                .putNull("d");

        websocket.sendText(payload.toString());
    }

    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) {
        logger.info("Connected to gateway");
    }

    @Override
    public void onConnectError(WebSocket websocket, WebSocketException exception) {
        logger.error("Error: " + exception.getMessage());
    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) {
        if (closedByServer) {
            logger.info("Closed by server: {}", serverCloseFrame.getCloseReason());
            for (GatewayCloseEventCode code : GatewayCloseEventCode.values()) {
                if (code.getCode() == serverCloseFrame.getCloseCode()) {
                    logger.info(code.getExplanation());
                    if (code.isReconnect()) {
                        reconnect();
                    }
                    break;
                }
            }
        } else {
            logger.info("Disconnected: {}", clientCloseFrame.getCloseReason());
        }
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }

    private void reconnect() {
        //todo reconnect logic
    }

    @Override
    public void onError(WebSocket websocket, WebSocketException cause) {
        logger.error("Error: " + cause.getMessage());
    }
}
