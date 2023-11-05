package org.dimas4ek.wrapper.utils;

import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.message.sticker.Sticker;
import org.dimas4ek.wrapper.entities.message.sticker.StickerImpl;
import org.dimas4ek.wrapper.entities.thread.ThreadMessage;
import org.dimas4ek.wrapper.types.AllowedMention;
import org.dimas4ek.wrapper.types.MessageFlag;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessageUtils {
    public static boolean isEmojiLong(String input) {
        return input.chars().allMatch(Character::isDigit);
    }

    public static void setAllowedMentions(JSONObject message, AllowedMention[] allowedMentions) {
        if (allowedMentions == null || allowedMentions.length == 0) {
            return;
        }

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

        if (!message.has("allowed_mentions")) {
            message.put("allowed_mentions", allowedMentionsJson);
            return;
        }

        JSONObject allowedMentionsObj = message.getJSONObject("allowed_mentions");
        boolean noUsers = !allowedMentionsObj.has("users");
        boolean noRoles = !allowedMentionsObj.has("roles");

        for (AllowedMention allowedMention : allowedMentions) {
            if ((allowedMention != AllowedMention.USER && noUsers) || (allowedMention != AllowedMention.ROLE && noRoles)) {
                allowedMentionsObj.put("parse", allowedMentionsJson.getJSONArray("parse"));
            } else {
                return;
            }
        }
    }

    public static void updateMentions(JSONObject message, String[] ids, String entities) {
        if (ids != null && ids.length > 0) {
            JSONObject allowedMentions = message.optJSONObject("allowed_mentions");
            if (allowedMentions == null) {
                message.put("allowed_mentions", new JSONObject().put(entities, ids));
            } else {
                JSONArray parse = allowedMentions.optJSONArray("parse");
                boolean noEntities = true;
                if (parse != null) {
                    for (int i = 0; i < parse.length(); i++) {
                        if (parse.getString(i).equals(entities)) {
                            noEntities = false;
                            break;
                        }
                    }
                }
                if (noEntities) {
                    allowedMentions.put(entities, new JSONArray().put(ids));
                }
            }
            System.out.println(message.toString(4));
        }
    }

    public static List<Sticker> retrieveStickersFromMessage(JSONObject message, Guild guild) {
        List<Sticker> stickers = new ArrayList<>();
        JSONArray stickerItems = message.getJSONArray("sticker_items");
        JSONArray guildStickers = JsonUtils.fetchArray("/guilds/" + guild.getId() + "/stickers");

        if (stickerItems != null && guildStickers != null) {
            Set<String> guildStickerIds = new HashSet<>();
            for (int j = 0; j < guildStickers.length(); j++) {
                JSONObject guildStickerJson = guildStickers.getJSONObject(j);
                guildStickerIds.add(guildStickerJson.getString("id"));
            }

            for (int i = 0; i < stickerItems.length(); i++) {
                JSONObject stickerJson = stickerItems.getJSONObject(i);
                if (guildStickerIds.contains(stickerJson.getString("id"))) {
                    stickers.add(new StickerImpl(stickerJson));
                }
            }
        }
        return stickers;
    }

    public static JSONObject createThreadMessage(ThreadMessage message) {
        JSONObject messageJson = new JSONObject();
        messageJson.put("content", message.getContent());
        messageJson.put("embeds", message.getEmbeds() != null
                ? EmbedUtils.createEmbedsArray(message.getEmbeds())
                : null);
        MessageUtils.setAllowedMentions(messageJson, message.getAllowedMentions() != null
                ? message.getAllowedMentions()
                : null);
        messageJson.put("components", message.getComponents() != null
                ? ComponentUtils.createComponents(message.getComponents())
                : null);
        messageJson.put("sticker_ids", message.getStickers() != null
                ? message.getStickers().stream().map(Sticker::getId).toList()
                : null);
        messageJson.put("attachments", message.getAttachments() != null
                ? new JSONArray().put(message.getAttachments())
                : null);
        messageJson.put("flags", message.isSuppressEmbeds() ? MessageFlag.SUPPRESS_EMBEDS.getValue() : 0);

        return messageJson;
    }

    public static ZonedDateTime getZonedDateTime(JSONObject object, String timestamp) {
        return ZonedDateTime.parse(object.getString(timestamp), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
