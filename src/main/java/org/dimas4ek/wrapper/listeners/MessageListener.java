package org.dimas4ek.wrapper.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import org.dimas4ek.wrapper.Dawncord;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildImpl;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.entities.message.MessageImpl;
import org.dimas4ek.wrapper.event.MessageEventImpl;
import org.dimas4ek.wrapper.utils.JsonUtils;

public class MessageListener extends WebSocketAdapter {
    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        JsonNode json = mapper.readTree(text);

        int op = json.get("op").asInt();

        if (op == 0) {
            JsonNode d = json.get("d");
            String type = json.get("t").asText();

            String guildId = d.get("guild_id").asText();
            String channelId = d.get("channel_id").asText();

            if (type.equals("MESSAGE_CREATE")) {
                String messageId = d.get("id").asText();

                Guild guild = new GuildImpl(JsonUtils.fetchEntity("/guilds/" + guildId));
                GuildChannel channel = guild.getChannelById(channelId);
                Message message = new MessageImpl(JsonUtils.fetchEntity("/channels/" + channelId + "/messages/" + messageId), guild);

                MessageEventImpl messageEvent = new MessageEventImpl(message, channel, guild);

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
