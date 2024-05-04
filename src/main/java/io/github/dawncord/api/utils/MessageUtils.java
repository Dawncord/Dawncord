package io.github.dawncord.api.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.channel.thread.ThreadMessage;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.message.sticker.Sticker;
import io.github.dawncord.api.entities.message.sticker.StickerImpl;
import io.github.dawncord.api.types.AllowedMention;
import io.github.dawncord.api.types.MessageFlag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility class for message-related operations.
 */
public class MessageUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Checks if a string represents a long emoji.
     *
     * @param input The input string to check.
     * @return True if the input represents a long emoji, false otherwise.
     */
    public static boolean isEmojiLong(String input) {
        return input.chars().allMatch(Character::isDigit);
    }

    /**
     * Sets allowed mentions in a JSON message node.
     *
     * @param message         The JSON message node.
     * @param allowedMentions The allowed mention types.
     */
    public static void setAllowedMentions(JsonNode message, AllowedMention[] allowedMentions) {
        if (allowedMentions == null || allowedMentions.length == 0) {
            return;
        }

        ObjectNode allowedMentionsJson = mapper.createObjectNode();
        if (allowedMentions.length == 1 && allowedMentions[0] == AllowedMention.EMPTY) {
            allowedMentionsJson.set("parse", mapper.createArrayNode());
        } else {
            ArrayNode allowedMentionsArray = mapper.createArrayNode();
            for (AllowedMention allowedMention : allowedMentions) {
                allowedMentionsArray.add(allowedMention.getValue());
            }
            allowedMentionsJson.set("parse", allowedMentionsArray);
        }

        if (!message.has("allowed_mentions")) {
            ((ObjectNode) message).set("allowed_mentions", allowedMentionsJson);
            return;
        }

        ObjectNode allowedMentionsObj = (ObjectNode) message.get("allowed_mentions");
        boolean noUsers = !allowedMentionsObj.has("users");
        boolean noRoles = !allowedMentionsObj.has("roles");

        for (AllowedMention allowedMention : allowedMentions) {
            if ((allowedMention != AllowedMention.USER && noUsers) || (allowedMention != AllowedMention.ROLE && noRoles)) {
                allowedMentionsObj.set("parse", allowedMentionsJson.get("parse"));
            } else {
                return;
            }
        }
    }

    /**
     * Updates mentions in a JSON message node.
     *
     * @param message  The JSON message node.
     * @param ids      The mention IDs.
     * @param entities The mention entities.
     */
    public static void updateMentions(JsonNode message, String[] ids, String entities) {
        if (ids != null && ids.length > 0) {
            ObjectNode allowedMentions = (ObjectNode) message.get("allowed_mentions");
            if (allowedMentions == null) {
                ((ObjectNode) message).set("allowed_mentions", mapper.createObjectNode().set(entities, mapper.valueToTree(ids)));
            } else {
                ArrayNode parse = allowedMentions.withArray("parse");
                boolean noEntities = true;
                if (parse != null) {
                    for (int i = 0; i < parse.size(); i++) {
                        if (parse.get(i).asText().equals(entities)) {
                            noEntities = false;
                            break;
                        }
                    }
                }
                if (noEntities) {
                    allowedMentions.set(entities, mapper.valueToTree(ids));
                }
            }
        }
    }

    /**
     * Retrieves stickers from a JSON message node associated with a guild.
     *
     * @param message The JSON message node.
     * @param guild   The guild associated with the stickers.
     * @return A list of stickers retrieved from the message.
     */
    public static List<Sticker> retrieveStickersFromMessage(JsonNode message, Guild guild) {
        List<Sticker> stickers = new ArrayList<>();
        JsonNode stickerItems = message.has("sticker_items") ? message.get("sticker_items") : null;
        JsonNode guildStickers = JsonUtils.fetch(Routes.Guild.Sticker.All(guild.getId()));

        if (stickerItems != null && guildStickers != null) {
            Set<String> guildStickerIds = new HashSet<>();
            for (int j = 0; j < guildStickers.size(); j++) {
                JsonNode guildStickerJson = guildStickers.get(j);
                guildStickerIds.add(guildStickerJson.get("id").asText());
            }

            for (int i = 0; i < stickerItems.size(); i++) {
                JsonNode stickerJson = stickerItems.get(i);
                if (guildStickerIds.contains(stickerJson.get("id").asText())) {
                    stickers.add(new StickerImpl(stickerJson, guild));
                }
            }
        }
        return stickers;
    }

    /**
     * Creates a JSON message node for a thread message.
     *
     * @param message The thread message.
     * @return The JSON message node.
     */
    public static ObjectNode createThreadMessage(ThreadMessage message) {
        ObjectNode messageJson = mapper.createObjectNode();
        messageJson.put("content", message.getContent());
        messageJson.set("embeds", message.getEmbeds() != null
                ? EmbedUtils.createEmbedsArray(message.getEmbeds())
                : null);
        setAllowedMentions(messageJson, message.getAllowedMentions() != null
                ? message.getAllowedMentions()
                : null);
        messageJson.set("components", message.getComponents() != null
                ? ComponentUtils.createComponents(message.getComponents())
                : null);
        messageJson.set("sticker_ids", message.getStickers() != null
                ? mapper.valueToTree(message.getStickers().stream().map(Sticker::getId).toList())
                : null);
        messageJson.set("attachments", message.getAttachments() != null
                ? mapper.valueToTree(message.getAttachments())
                : null);
        messageJson.put("flags", message.isSuppressEmbeds() ? MessageFlag.SUPPRESS_EMBEDS.getValue() : 0);

        return messageJson;
    }

}
