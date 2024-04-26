package org.dimas4ek.wrapper.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import org.dimas4ek.wrapper.Dawncord;
import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildImpl;
import org.dimas4ek.wrapper.event.GuildDefaultEvent;
import org.dimas4ek.wrapper.types.GatewayEvent;
import org.dimas4ek.wrapper.utils.JsonUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GuildListener extends WebSocketAdapter {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        JsonNode json = mapper.readTree(text);

        int op = json.get("op").asInt();

        if (op == 0) {
            JsonNode d = json.get("d");
            GatewayEvent type = GatewayEvent.valueOf(json.get("t").asText());

            String guildId = d.has("guild_id") ? d.get("guild_id").asText() : (d.has("id") ? d.get("id").asText() : null);
            Guild guild = guildId != null ? new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(guildId))) : null;

            if (guild != null) {
                GuildDefaultEvent guildEvent = new GuildDefaultEvent(guild);
                try {
                    Method method = Dawncord.class.getDeclaredMethod("processGuildEvent", GatewayEvent.class, GuildDefaultEvent.class);
                    method.setAccessible(true);
                    method.invoke(null, type, guildEvent);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException |
                         InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
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
