package org.dimas4ek.wrapper.utils;

import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.message.sticker.Sticker;
import org.dimas4ek.wrapper.entities.message.sticker.StickerImpl;
import org.dimas4ek.wrapper.types.AllowedMention;
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

    //todo check
    public static void setAllowedMentions(JSONObject message, AllowedMention[] allowedMentions) {
        if (allowedMentions != null && allowedMentions.length > 0) {
            JSONObject allowedMentionsJson = new JSONObject();
            if (allowedMentions.length == 1 && allowedMentions[0] == AllowedMention.EMPTY) {
                allowedMentionsJson.put("parse", new JSONArray());
            } else {
                JSONArray allowedMentionsArray = new JSONArray();
                for (AllowedMention allowedMention : allowedMentions) {
                    allowedMentionsArray.put(allowedMention.getValue());
                }
                allowedMentionsJson.put("parse", allowedMentionsArray);
            }

            if (message.has("allowed_mentions")) {
                boolean noUsers = false;
                boolean noRoles = false;
                if (!message.getJSONObject("allowed_mentions").has("users")) {
                    noUsers = true;
                }
                if (!message.getJSONObject("allowed_mentions").has("roles")) {
                    noRoles = true;
                }
                for (AllowedMention allowedMention : allowedMentions) {
                    if (allowedMention != AllowedMention.USER && noUsers
                            || allowedMention != AllowedMention.ROLE && noRoles) {
                        message.getJSONObject("allowed_mentions")
                                .put("parse", allowedMentionsJson.getJSONArray("parse"));
                    } else {
                        return;
                    }
                }
            } else {
                message.put("allowed_mentions", allowedMentionsJson);
            }
        }
    }

    //todo check
    public static void updateMentions(JSONObject message, String[] ids, String entities) {
        if (ids != null && ids.length > 0) {
            if (message.has("allowed_mentions")) {
                boolean noEntities = true;
                JSONObject allowedMentions = message.getJSONObject("allowed_mentions");
                if (allowedMentions.has("parse")) {
                    JSONArray parse = allowedMentions.getJSONArray("parse");
                    for (int i = 0; i < parse.length(); i++) {
                        if (parse.getString(i).equals(entities)) {
                            noEntities = false;
                        }
                    }
                }
                if (noEntities) {
                    allowedMentions.put(entities, new JSONArray().put(ids));
                }
            } else {
                message.put("allowed_mentions", new JSONObject()
                        .put(entities, new JSONArray().put(ids))
                );
            }
        }
    }

    public static List<Sticker> retrieveStickersFromMessage(JSONObject message, Guild guild) {
        List<Sticker> stickers = new ArrayList<>();
        JSONArray stickerItems = message.getJSONArray("sticker_items");
        JSONArray guildStickers = JsonUtils.fetchArray("/guilds/" + guild.getId() + "/stickers");

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
