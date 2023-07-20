package org.dimas4ek.event;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.dimas4ek.enities.InteractionType;
import org.dimas4ek.enities.embed.Embed;
import org.dimas4ek.enities.embed.Field;
import org.dimas4ek.enities.guild.Guild;
import org.dimas4ek.enities.guild.GuildChannel;
import org.dimas4ek.event.interaction.Interaction;
import org.dimas4ek.event.interaction.response.InteractionCallback;
import org.dimas4ek.event.interaction.response.InteractionCallbackImpl;
import org.dimas4ek.utils.Constants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class SlashCommandInteractionEventImpl implements SlashCommandInteractionEvent {
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private final String interactionId;
    private final String interactionToken;
    private final Interaction interaction;
    
    public SlashCommandInteractionEventImpl(String interactionId, String interactionToken, Interaction interaction) {
        this.interactionId = interactionId;
        this.interactionToken = interactionToken;
        this.interaction = interaction;
    }
    
    @Override
    public String getCommandName() {
        return interaction.getCommandName();
    }
    
    /**
     * Sends a reply to an interaction with the provided response text.
     *
     * @param message the text to be sent as the reply
     */
    @Override
    public InteractionCallback reply(String message) {
        if (message == null || message.isEmpty()) {
            System.out.println("Empty response text");
            return null;
        }
        
        JSONObject jsonObject = new JSONObject()
            .put("type", InteractionType.CHANNEL_MESSAGE_WITH_SOURCE.getValue())
            .put("data", new JSONObject()
                .put("content", message));
        
        return new InteractionCallbackImpl(jsonObject, interactionId, interactionToken);
    }
    
    
    /**
     * Sends a reply to an interaction with the provided response embed.
     *
     * @param embed the Embed object to be sent as the reply
     */
    @Override
    public void replyWithEmbed(Embed embed) {
        if (embed == null) {
            System.out.println("Empty response embed");
            return;
        }
        
        JSONObject jsonObject = new JSONObject();
        JSONObject embedJsonObject = new JSONObject()
            .put("title", embed.getTitle())
            .put("description", embed.getDescription());
        
        JSONArray fieldsJsonArray = new JSONArray();
        for (Field field : embed.getFields()) {
            fieldsJsonArray.put(new JSONObject()
                                    .put("name", field.getName())
                                    .put("value", field.getValue())
                                    .put("inline", field.isInline()));
        }
        embedJsonObject.put("fields", fieldsJsonArray);
        
        embedJsonObject.put("author", new JSONObject()
            .put("name", embed.getAuthor()));
        embedJsonObject.put("footer", new JSONObject()
            .put("text", embed.getFooter()));
        
        RequestBody requestBody = RequestBody.create(
            jsonObject
                .put("type", InteractionType.CHANNEL_MESSAGE_WITH_SOURCE.getValue())
                .put("data", new JSONObject()
                    .put("embeds", new JSONArray().put(embedJsonObject)))
                .toString(),
            Constants.MEDIA_TYPE_JSON
        );
        
        String url = String.format("%s/interactions/%s/%s/callback", Constants.API_URL, interactionId, interactionToken);
        Request request = new Request.Builder()
            .url(url)
            .post(requestBody)
            .build();
        
        try (Response response = CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Response executed successfully");
            } else {
                System.out.println("API request failed with status code: " + response.code() + " body: " + response.body().string());
            }
        } catch (IOException e) {
            System.out.println("Encountered IOException: " + e.getMessage());
        }
    }
    
    @Override
    public void deferReply() {
        JSONObject jsonObject = new JSONObject();
        
        RequestBody requestBody = RequestBody.create(
            jsonObject
                .put("type", InteractionType.DEFERRED_CHANNEL_MESSAGE_WITH_SOURCE.getValue())
                .toString(),
            Constants.MEDIA_TYPE_JSON
        );
        
        Request request = new Request.Builder()
            .url(Constants.API_URL + "/interactions/" + interactionId + "/" + interactionToken + "/callback")
            .post(requestBody)
            .build();
        
        try (Response response = CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Response executed successfully");
            } else {
                System.out.println("API request failed with status code: " + response.code());
            }
        } catch (IOException e) {
            System.out.println("Encountered IOException: " + e.getMessage());
        }
    }
    
    @Override
    public Guild getGuild() {
        return interaction.getGuild();
    }
    
    @Override
    public GuildChannel getChannel() {
        return interaction.getChannel();
    }
}
