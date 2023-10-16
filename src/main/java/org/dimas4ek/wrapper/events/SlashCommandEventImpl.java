package org.dimas4ek.wrapper.events;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.GuildChannelImpl;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.interaction.Interaction;
import org.json.JSONObject;

public class SlashCommandEventImpl implements SlashCommandEvent {
    private final String commandName;
    private final Interaction response;

    public SlashCommandEventImpl(String commandName, Interaction response) {
        this.commandName = commandName;
        this.response = response;
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public void reply(String message) {
        if (message == null || message.isEmpty()) {
            System.out.println("Empty response text");
        }

        JSONObject jsonObject = new JSONObject()
                .put("type", 4)
                .put("data", new JSONObject()
                        .put("content", message));

        String url = "/interactions/" + response.getInteractionId() + "/" + response.getInteractionToken() + "/callback";

        ApiClient.post(jsonObject, url);
    }

    @Override
    public Guild getGuild() {
        return response.getGuild();
    }

    @Override
    public GuildChannel getChannel() {
        return response.getGuildChannel();
    }

    @Override
    public GuildChannel getChannelById(String id) {
        return new GuildChannelImpl(ApiClient.getJsonObject("/channels/" + id));
    }

    @Override
    public GuildChannel getChannelById(long id) {
        return getChannelById(String.valueOf(id));
    }
}
