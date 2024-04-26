package org.dimas4ek.wrapper.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import org.dimas4ek.wrapper.Dawncord;
import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildImpl;
import org.dimas4ek.wrapper.event.ChannelEvent;
import org.dimas4ek.wrapper.types.GatewayEvent;
import org.dimas4ek.wrapper.utils.JsonUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ChannelListener extends WebSocketAdapter {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        JsonNode json = mapper.readTree(text);

        int op = json.get("op").asInt();

        if (op == 0) {
            JsonNode d = json.get("d");
            GatewayEvent type = GatewayEvent.valueOf(json.get("t").asText());

            String guildId = d.has("guild_id") ? d.get("guild_id").asText() : null;
            Guild guild = guildId != null ? new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(guildId))) : null;

            if (guild != null) {
                GuildChannel channel = guild.getChannelById(d.get("id").asText());

                if (channel != null) {
                    ChannelEvent channelEvent = new ChannelEvent(guild, channel);

                    try {
                        Method method = Dawncord.class.getDeclaredMethod("processChannelEvent", GatewayEvent.class, ChannelEvent.class);
                        method.setAccessible(true);
                        method.invoke(null, type, channelEvent);
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException |
                             IllegalArgumentException |
                             InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
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
