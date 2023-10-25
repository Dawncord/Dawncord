package org.dimas4ek.wrapper.utils;

import org.dimas4ek.wrapper.entities.message.embed.Embed;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EmbedUtils {
    public static JSONArray createEmbedsArray(Embed... embeds) {
        JSONArray embedsArray = new JSONArray();

        for (Embed embed : embeds) {
            JSONObject embedJson = new JSONObject()
                    .put("title", embed.getTitle())
                    .put("description", embed.getDescription())
                    .put("url", embed.getUrl())
                    .put("timestamp", embed.getTimestamp().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                    .put("color", embed.getColor() == 536870911 ? 0 : embed.getColor());
            if (embed.getFooter() != null) {
                embedJson
                        .put("footer", new JSONObject()
                                .put("text", embed.getFooter().getText())
                                .put("icon_url", embed.getFooter().getIconUrl())
                        );
            }
            if (embed.getImage() != null) {
                embedJson
                        .put("image", new JSONObject()
                                .put("url", embed.getImage().getUrl())
                                .put("proxy_url", embed.getImage().getProxyUrl())
                                .put("width", embed.getImage().getWidth())
                                .put("height", embed.getImage().getHeight())
                        );
            }
            if (embed.getThumbnail() != null) {
                embedJson
                        .put("thumbnail", new JSONObject()
                                .put("url", embed.getThumbnail().getUrl())
                                .put("proxy_url", embed.getThumbnail().getProxyUrl())
                                .put("width", embed.getThumbnail().getWidth())
                                .put("height", embed.getThumbnail().getHeight())
                        );
            }
            if (embed.getAuthor() != null) {
                embedJson
                        .put("author", new JSONObject()
                                .put("name", embed.getAuthor().getName())
                                .put("url", embed.getAuthor().getUrl())
                                .put("icon_url", embed.getAuthor().getIconUrl())
                        );
            }
            if (embed.getFields() != null && !embed.getFields().isEmpty()) {
                JSONArray fieldsArray = new JSONArray();
                embed.getFields().forEach(field -> {
                    JSONObject fieldJson = new JSONObject()
                            .put("name", field.getName())
                            .put("value", field.getValue())
                            .put("inline", field.isInline());
                    fieldsArray.put(fieldJson);
                });
                embedJson.put("fields", fieldsArray);
            }

            embedsArray.put(embedJson);
        }

        return embedsArray;
    }

    public static Embed getEmbedFromJson(JSONObject embed) {
        String title = embed.getString("title");
        String description = embed.has("description") ? embed.getString("description") : null;
        String url = embed.has("url") ? embed.getString("url") : null;
        ZonedDateTime timestamp = embed.has("timestamp") ? getTimestamp(embed.getString("timestamp")) : null;
        int color = embed.has("color") ? embed.getInt("color") : 0;
        Embed.Footer footer = embed.has("footer") ? getEmbedFooterFromJson(embed.getJSONObject("footer")) : null;
        Embed.EmbedImage image = embed.has("image") ? getEmbedImageFromJson(embed.getJSONObject("image")) : null;
        Embed.EmbedImage thumbnail = embed.has("thumbnail") ? getEmbedImageFromJson(embed.getJSONObject("thumbnail")) : null;
        Embed.Author author = embed.has("author") ? getEmbedAuthorFromJson(embed.getJSONObject("author")) : null;
        List<Embed.Field> fields = embed.has("fields") ? getEmbedFieldsFromJson(embed.getJSONArray("fields")) : null;

        return new Embed(title, description, url, timestamp, color, footer, image, thumbnail, author, fields);
    }

    private static ZonedDateTime getTimestamp(String timestampJson) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return ZonedDateTime.parse(timestampJson, formatter);
    }

    private static Embed.Footer getEmbedFooterFromJson(JSONObject footerJson) {
        String text = footerJson.getString("text");
        String iconUrl = footerJson.has("icon_url") && !footerJson.isNull("icon_url") ? footerJson.getString("icon_url") : null;
        return new Embed.Footer(text, iconUrl);
    }

    private static Embed.EmbedImage getEmbedImageFromJson(JSONObject imageJson) {
        String url = imageJson.getString("url");
        String proxyUrl = imageJson.has("proxy_url") && !imageJson.isNull("proxy_url") ? imageJson.getString("proxy_url") : null;
        int width = imageJson.has("width") && !imageJson.isNull("width") ? imageJson.getInt("width") : 0;
        int height = imageJson.has("height") && !imageJson.isNull("height") ? imageJson.getInt("height") : 0;
        return new Embed.EmbedImage(url, proxyUrl, width, height);
    }

    private static Embed.Author getEmbedAuthorFromJson(JSONObject authorJson) {
        String name = authorJson.getString("name");
        String authorUrl = authorJson.has("url") && !authorJson.isNull("url") ? authorJson.getString("url") : null;
        String iconUrl = authorJson.has("icon_url") && !authorJson.isNull("icon_url") ? authorJson.getString("icon_url") : null;
        return new Embed.Author(name, authorUrl, iconUrl);
    }

    private static List<Embed.Field> getEmbedFieldsFromJson(JSONArray fieldsArray) {
        List<Embed.Field> fields = new ArrayList<>();
        for (int i = 0; i < fieldsArray.length(); i++) {
            JSONObject fieldsJson = fieldsArray.getJSONObject(i);
            Embed.Field field = new Embed.Field(fieldsJson.getString("name"), fieldsJson.getString("value"), fieldsJson.getBoolean("inline"));
            fields.add(field);
        }

        return fields;
    }
}
