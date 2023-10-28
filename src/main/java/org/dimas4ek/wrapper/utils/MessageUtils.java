package org.dimas4ek.wrapper.utils;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.message.sticker.Sticker;
import org.dimas4ek.wrapper.entities.message.sticker.StickerImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MessageUtils {
    public static boolean isEmojiLong(String input) {
        try {
            Long.parseLong(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static List<Sticker> retrieveStickersFromMessage(JSONObject message, Guild guild) {
        List<Sticker> stickers = new ArrayList<>();
        JSONArray stickerItems = message.getJSONArray("sticker_items");
        JSONArray guildStickers = ApiClient.getJsonArray("/guilds/" + guild.getId() + "/stickers");

        if (stickerItems != null && guildStickers != null) {
            for (int i = 0; i < stickerItems.length(); i++) {
                JSONObject stickerJson = stickerItems.getJSONObject(i);
                for (int j = 0; j < guildStickers.length(); j++) {
                    JSONObject guildStickerJson = guildStickers.getJSONObject(j);
                    if (stickerJson.getString("id").equals(guildStickerJson.getString("id"))) {
                        stickers.add(new StickerImpl(guildStickerJson));
                        break;
                    }
                }
            }
        }
        return stickers;
    }

    public static ZonedDateTime getZonedDateTime(JSONObject object, String timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return ZonedDateTime.parse(object.getString(timestamp), formatter);
    }
}
