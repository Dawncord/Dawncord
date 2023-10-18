package org.dimas4ek.wrapper.listeners;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.Constants;
import org.dimas4ek.wrapper.Dawncord;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.GuildChannelImpl;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildImpl;
import org.dimas4ek.wrapper.event.SlashCommandEvent;
import org.dimas4ek.wrapper.event.SlashCommandEventImpl;
import org.dimas4ek.wrapper.interaction.Interaction;
import org.dimas4ek.wrapper.interaction.InteractionData;
import org.dimas4ek.wrapper.slashcommand.SlashCommand;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                JSONObject slashCommandData = ApiClient.getJsonObject("/applications/" + Constants.APPLICATION_ID + "/commands/" + data.getString("id"));
                SlashCommand slashCommand = new SlashCommand(slashCommandData);

                String interactionId = d.getString("id");
                String interactionToken = d.getString("token");
                Guild guild = new GuildImpl(JsonUtils.fetchEntity("/guilds/" + guildId));
                GuildChannel guildChannel = new GuildChannelImpl(JsonUtils.fetchEntity("/channels/" + channelId));

                Interaction response = new Interaction(interactionId, interactionToken, guild, guildChannel);

                List<Map<String, Object>> options = new ArrayList<>();
                if (data.has("options")) {
                    for (int i = 0; i < data.getJSONArray("options").length(); i++) {
                        JSONObject option = data.getJSONArray("options").getJSONObject(i);
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", option.getString("name"));
                        map.put("value", option.get("value"));
                        map.put("guildId", guild.getId());

                        if (data.has("resolved")) {
                            map.put("resolved", data.getJSONObject("resolved"));
                        }

                        options.add(map);
                    }
                }

                InteractionData interactionData = new InteractionData(options, slashCommand, response);

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
