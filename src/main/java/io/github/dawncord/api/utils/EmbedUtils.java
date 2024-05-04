package io.github.dawncord.api.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.entities.message.embed.Embed;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for handling embeds.
 */
public class EmbedUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Creates a JSON array node containing embeds.
     *
     * @param embeds The list of embeds.
     * @return The JSON array node containing embeds.
     */
    public static JsonNode createEmbedsArray(List<Embed> embeds) {
        ArrayNode embedsArray = mapper.createArrayNode();

        for (Embed embed : embeds) {
            embedsArray.add(createEmbedJson(embed));
        }

        return embedsArray;
    }

    /**
     * Creates an Embed object from a JSON node.
     *
     * @param embed The JSON node representing the embed.
     * @return The Embed object.
     */
    public static Embed getEmbedFromJson(JsonNode embed) {
        String title = embed.get("title").asText();
        String description = embed.has("description") ? embed.get("description").asText() : null;
        String url = embed.has("url") ? embed.get("url").asText() : null;
        ZonedDateTime timestamp = embed.has("timestamp") ? TimeUtils.getZonedDateTime(embed, "timestamp") : null;
        int color = embed.has("color") ? embed.get("color").asInt() : 0;
        Embed.Footer footer = embed.has("footer") ? getEmbedFooterFromJson(embed.get("footer")) : null;
        Embed.EmbedImage image = embed.has("image") ? getEmbedImageFromJson(embed.get("image")) : null;
        Embed.EmbedImage thumbnail = embed.has("thumbnail") ? getEmbedImageFromJson(embed.get("thumbnail")) : null;
        Embed.Author author = embed.has("author") ? getEmbedAuthorFromJson(embed.get("author")) : null;
        List<Embed.Field> fields = embed.has("fields") ? getEmbedFieldsFromJson(embed.get("fields")) : null;

        return new Embed(title, description, url, timestamp, color, footer, image, thumbnail, author, fields);
    }

    private static ObjectNode createEmbedJson(Embed embed) {
        ObjectNode embedJson = mapper.createObjectNode()
                .put("title", embed.getTitle())
                .put("description", embed.getDescription())
                .put("url", embed.getUrl())
                .put("timestamp", embed.getTimestamp() != null ? embed.getTimestamp().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) : null)
                .put("color", embed.getColor() == 536870911 ? 0 : embed.getColor());
        if (embed.getFooter() != null) {
            setEmbedFooterToJson(embed, embedJson);
        }
        if (embed.getImage() != null) {
            setEmbedImageToJson(embed, embedJson);
        }
        if (embed.getThumbnail() != null) {
            setEmbedThumbnailToJson(embed, embedJson);
        }
        if (embed.getAuthor() != null) {
            setEmbedAuthorToJson(embed, embedJson);
        }
        if (embed.getFields() != null && !embed.getFields().isEmpty()) {
            setFields(embed, embedJson);
        }
        return embedJson;
    }

    private static void setFields(Embed embed, ObjectNode embedJson) {
        ArrayNode fieldsArray = mapper.createArrayNode();
        embed.getFields().forEach(field -> {
            JsonNode fieldJson = mapper.createObjectNode()
                    .put("name", field.getName())
                    .put("value", field.getValue())
                    .put("inline", field.isInline());
            fieldsArray.add(fieldJson);
        });
        embedJson.set("fields", fieldsArray);
    }

    private static void setEmbedAuthorToJson(Embed embed, ObjectNode embedJson) {
        embedJson
                .set("author", mapper.createObjectNode()
                        .put("name", embed.getAuthor().getName())
                        .put("url", embed.getAuthor().getUrl())
                        .put("icon_url", embed.getAuthor().getIconUrl())
                );
    }

    private static void setEmbedThumbnailToJson(Embed embed, ObjectNode embedJson) {
        embedJson
                .set("thumbnail", mapper.createObjectNode()
                        .put("url", embed.getThumbnail().getUrl())
                        .put("proxy_url", embed.getThumbnail().getProxyUrl())
                        .put("width", embed.getThumbnail().getWidth())
                        .put("height", embed.getThumbnail().getHeight())
                );
    }

    private static void setEmbedImageToJson(Embed embed, ObjectNode embedJson) {
        embedJson
                .set("image", mapper.createObjectNode()
                        .put("url", embed.getImage().getUrl())
                        .put("proxy_url", embed.getImage().getProxyUrl())
                        .put("width", embed.getImage().getWidth())
                        .put("height", embed.getImage().getHeight())
                );
    }

    private static void setEmbedFooterToJson(Embed embed, ObjectNode embedJson) {
        embedJson
                .set("footer", mapper.createObjectNode()
                        .put("text", embed.getFooter().getText())
                        .put("icon_url", embed.getFooter().getIconUrl())
                );
    }

    private static Embed.Footer getEmbedFooterFromJson(JsonNode footerJson) {
        String text = footerJson.get("text").asText();
        String iconUrl = footerJson.has("icon_url") && footerJson.hasNonNull("icon_url") ? footerJson.get("icon_url").asText() : null;
        return new Embed.Footer(text, iconUrl);
    }

    private static Embed.EmbedImage getEmbedImageFromJson(JsonNode imageJson) {
        String url = imageJson.get("url").asText();
        String proxyUrl = imageJson.has("proxy_url") && imageJson.hasNonNull("proxy_url") ? imageJson.get("proxy_url").asText() : null;
        int width = imageJson.has("width") && imageJson.hasNonNull("width") ? imageJson.get("width").asInt() : 0;
        int height = imageJson.has("height") && imageJson.hasNonNull("height") ? imageJson.get("height").asInt() : 0;
        return new Embed.EmbedImage(url, proxyUrl, width, height);
    }

    private static Embed.Author getEmbedAuthorFromJson(JsonNode authorJson) {
        String name = authorJson.get("name").asText();
        String authorUrl = authorJson.has("url") && authorJson.hasNonNull("url") ? authorJson.get("url").asText() : null;
        String iconUrl = authorJson.has("icon_url") && authorJson.hasNonNull("icon_url") ? authorJson.get("icon_url").asText() : null;
        return new Embed.Author(name, authorUrl, iconUrl);
    }

    private static List<Embed.Field> getEmbedFieldsFromJson(JsonNode fieldsArray) {
        List<Embed.Field> fields = new ArrayList<>();
        for (int i = 0; i < fieldsArray.size(); i++) {
            JsonNode fieldsJson = fieldsArray.get(i);
            Embed.Field field = new Embed.Field(fieldsJson.get("name").asText(), fieldsJson.get("value").asText(), fieldsJson.get("inline").asBoolean());
            fields.add(field);
        }
        return fields;
    }
}
