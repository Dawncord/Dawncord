package org.dimas4ek.wrapper.events;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.interaction.Interaction;
import org.dimas4ek.wrapper.entities.Guild;
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
}
