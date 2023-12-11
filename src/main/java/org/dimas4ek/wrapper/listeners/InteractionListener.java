package org.dimas4ek.wrapper.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import org.dimas4ek.wrapper.Constants;
import org.dimas4ek.wrapper.Dawncord;
import org.dimas4ek.wrapper.command.SlashCommand;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildImpl;
import org.dimas4ek.wrapper.entities.guild.GuildMember;
import org.dimas4ek.wrapper.event.SlashCommandEvent;
import org.dimas4ek.wrapper.event.SlashCommandEventImpl;
import org.dimas4ek.wrapper.interaction.Interaction;
import org.dimas4ek.wrapper.interaction.InteractionData;
import org.dimas4ek.wrapper.utils.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InteractionListener extends WebSocketAdapter {
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

            if (type.equals("INTERACTION_CREATE")) {
                JsonNode data = d.get("data");

                JsonNode slashCommandJson = JsonUtils.fetchEntity("/applications/" + Constants.APPLICATION_ID + "/commands/" + data.get("id").asText());
                SlashCommand slashCommand = new SlashCommand(slashCommandJson);

                String interactionId = d.get("id").asText();
                String interactionToken = d.get("token").asText();
                Guild guild = new GuildImpl(JsonUtils.fetchEntity("/guilds/" + guildId));
                GuildMember guildMember = guild.getMemberById(d.get("member").get("user").get("id").asText());
                GuildChannel guildChannel = guild.getChannelById(channelId);

                List<Map<String, Object>> options = new ArrayList<>();
                if (data.has("options")) {
                    ArrayNode optionsArray = (ArrayNode) data.get("options");

                    for (JsonNode optionNode : optionsArray) {
                        ObjectNode option = (ObjectNode) optionNode;
                        Map<String, Object> map = new HashMap<>();

                        map.put("name", option.get("name").asText());
                        map.put("value", option.get("value"));

                        if (data.has("resolved")) {
                            map.put("resolved", data.get("resolved"));
                        }

                        options.add(map);
                    }
                }

                Interaction interaction = new Interaction(interactionId, interactionToken);
                InteractionData interactionData = new InteractionData(options, slashCommand, interaction, guild, guildMember, guildChannel);

                SlashCommandEvent slashCommandEvent = new SlashCommandEventImpl(interactionData);

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
