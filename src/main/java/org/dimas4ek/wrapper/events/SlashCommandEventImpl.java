package org.dimas4ek.wrapper.events;

import org.dimas4ek.wrapper.ApiClient;
import org.json.JSONObject;

public class SlashCommandEventImpl implements SlashCommandEvent {
    private final String commandName;
    private final String interactionId;
    private final String interactionToken;

    public SlashCommandEventImpl(String commandName, String interactionId, String interactionToken) {
        this.commandName = commandName;
        this.interactionId = interactionId;
        this.interactionToken = interactionToken;
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

        String url = "/interactions/" + interactionId + "/" + interactionToken + "/callback";

        ApiClient.post(jsonObject, url);
    }
}
