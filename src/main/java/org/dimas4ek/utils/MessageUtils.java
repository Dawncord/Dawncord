package org.dimas4ek.utils;

import org.dimas4ek.api.exceptions.EmptyEmbedTitleException;
import org.dimas4ek.enities.component.ActionRow;
import org.dimas4ek.enities.component.Button;
import org.dimas4ek.enities.component.MessageComponent;
import org.dimas4ek.enities.embed.Embed;
import org.dimas4ek.enities.embed.EmbedImage;
import org.dimas4ek.enities.embed.Field;
import org.dimas4ek.enities.types.InteractionType;
import org.json.JSONArray;
import org.json.JSONObject;

public class MessageUtils {
    public static void createEmbed(Embed embed, JSONObject jsonObject) {
        JSONObject embedJsonObject = new JSONObject();
        
        try {
            embedJsonObject
                .put("title", embed.getTitle())
                .put("description", embed.getDescription());
            
            if (embed.getTitle() == null || embed.getTitle().isEmpty()) {
                throw new EmptyEmbedTitleException("Embed title should not be empty");
            }
        } catch (EmptyEmbedTitleException e) {
            e.printStackTrace();
        }
        
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
        
        embedJsonObject.put("color", embed.getColorRaw() & 16777215);
        
        embedJsonObject.put("timestamp", embed.getTimeStamp());
        
        EmbedImage image = embed.getImage();
        if (image != null) {
            embedJsonObject.put("image", new JSONObject()
                .put("url", embed.getImage().getUrl())
                .put("width", embed.getImage().getWidth())
                .put("height", embed.getImage().getHeight()));
        }
        
        EmbedImage thumbnail = embed.getThumbnail();
        if (thumbnail != null) {
            embedJsonObject.put("thumbnail", new JSONObject()
                .put("url", embed.getThumbnail().getUrl())
                .put("width", embed.getThumbnail().getWidth())
                .put("height", embed.getThumbnail().getHeight()));
        }
        
        jsonObject.put("type", InteractionType.CHANNEL_MESSAGE_WITH_SOURCE.getValue());
        
        if (jsonObject.isNull("data")) {
            jsonObject.put("data", new JSONObject());
        }
        
        jsonObject.getJSONObject("data")
            .put("embeds", new JSONArray()
                .put(embedJsonObject));
    }
    
    public static void createComponent(MessageComponent component, JSONObject jsonObject) {
        JSONArray components = new JSONArray();
        for (ActionRow actionRow : component.getActionRows()) {
            JSONObject actionRowJsonObject = new JSONObject();
            
            actionRowJsonObject.put("type", 1);
            actionRowJsonObject.put("components", new JSONArray());
            
            for (Button button : actionRow.getButtons()) {
                actionRowJsonObject.getJSONArray("components")
                    .put(new JSONObject()
                             .put("style", button.getStyle().getValue())
                             .put("label", button.getLabel())
                             .put("url", button.getUrl())
                             .put("custom_id", button.getId())
                             .put("disabled", button.isDisabled())
                             .put("type", 2)
                    );
            }
            
            components.put(actionRowJsonObject);
        }
        
        if (jsonObject.isNull("data")) {
            jsonObject.put("data", new JSONObject());
        }
        
        jsonObject.getJSONObject("data")
            .put("components", components);
    }
}
