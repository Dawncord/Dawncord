package io.github.dawncord.api.entities.guild;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.CustomEmoji;
import io.github.dawncord.api.entities.CustomEmojiImpl;
import io.github.dawncord.api.entities.Emoji;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.image.DiscoverySplash;
import io.github.dawncord.api.entities.image.Splash;
import io.github.dawncord.api.entities.message.sticker.Sticker;
import io.github.dawncord.api.entities.message.sticker.StickerImpl;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a preview of a guild.
 */
public class GuildPreview implements ISnowflake {
    private final JsonNode preview;
    private final Guild guild;
    private String id;
    private String name;
    private String description;
    private Splash splash;
    private DiscoverySplash discoverySplash;
    private List<CustomEmoji> emojis;
    private List<String> features;
    private List<Sticker> stickers;
    private Integer memberCount;
    private Integer onlineMemberCount;

    /**
     * Constructs a GuildPreview object.
     *
     * @param preview The JSON node containing guild preview information.
     * @param guild   The associated guild.
     */
    public GuildPreview(JsonNode preview, Guild guild) {
        this.preview = preview;
        this.guild = guild;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = preview.get("id").asText();
        }
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    /**
     * Retrieves the name of the guild.
     *
     * @return The name of the guild.
     */
    public String getName() {
        if (name == null) {
            name = preview.get("name").asText();
        }
        return name;
    }

    /**
     * Retrieves the description of the guild.
     *
     * @return The description of the guild.
     */
    public String getDescription() {
        if (description == null) {
            description = preview.get("description").asText();
        }
        return description;
    }

    /**
     * Retrieves the splash image of the guild.
     *
     * @return The splash image of the guild.
     */
    public Splash getSplash() {
        if (splash == null) {
            splash = preview.has("splash") ? new Splash(getId(), preview.get("splash").asText()) : null;
        }
        return splash;
    }

    /**
     * Retrieves the discovery splash image of the guild.
     *
     * @return The discovery splash image of the guild.
     */
    public DiscoverySplash getDiscoverySplash() {
        if (discoverySplash == null) {
            discoverySplash = preview.has("discovery_splash") ?
                    new DiscoverySplash(getId(), preview.get("discovery_splash").asText()) : null;
        }
        return discoverySplash;
    }

    /**
     * Retrieves the emojis of the guild.
     *
     * @return The emojis of the guild.
     */
    public List<CustomEmoji> getEmojis() {
        if (emojis == null) {
            emojis = JsonUtils.getEntityList(preview.get("emojis"), emoji -> new CustomEmojiImpl(emoji, guild));
        }
        return emojis;
    }

    /**
     * Retrieves the emoji with the specified ID.
     *
     * @param emojiId The ID of the emoji.
     * @return The emoji with the specified ID, or null if not found.
     */
    public Emoji getEmojiById(String emojiId) {
        return getEmojis().stream().filter(emoji -> emoji.getId().equals(emojiId)).findFirst().orElse(null);
    }

    /**
     * Retrieves the emoji with the specified ID.
     *
     * @param emojiId The ID of the emoji.
     * @return The emoji with the specified ID, or null if not found.
     */
    public Emoji getEmojiById(long emojiId) {
        return getEmojiById(String.valueOf(emojiId));
    }

    /**
     * Retrieves the emojis with the specified name.
     *
     * @param emojiName The name of the emojis.
     * @return The list of emojis with the specified name.
     */
    public List<CustomEmoji> getEmojisByName(String emojiName) {
        return getEmojis().stream().filter(emoji -> emoji.getName().equals(emojiName)).toList();
    }

    /**
     * Retrieves the emojis created by the user with the specified ID.
     *
     * @param userId The ID of the user.
     * @return The list of emojis created by the user with the specified ID.
     */
    public List<CustomEmoji> getEmojisByCreatorId(String userId) {
        return getEmojis().stream().filter(emoji -> emoji.getCreator().getId().equals(userId)).toList();
    }

    /**
     * Retrieves the emojis created by the user with the specified ID.
     *
     * @param userId The ID of the user.
     * @return The list of emojis created by the user with the specified ID.
     */
    public List<CustomEmoji> getEmojisByCreatorId(long userId) {
        return getEmojisByCreatorId(String.valueOf(userId));
    }

    /**
     * Retrieves the features of the guild.
     *
     * @return The features of the guild.
     */
    public List<String> getFeatures() {
        if (features == null) {
            features = new ArrayList<>();
            for (JsonNode feature : preview.get("features")) {
                features.add(feature.asText());
            }
        }
        return features;
    }

    /**
     * Retrieves the stickers of the guild.
     *
     * @return The stickers of the guild.
     */
    public List<Sticker> getStickers() {
        if (stickers == null) {
            stickers = JsonUtils.getEntityList(preview.get("stickers"), sticker -> new StickerImpl(sticker, guild));
        }
        return stickers;
    }

    /**
     * Retrieves the sticker with the specified ID.
     *
     * @param stickerId The ID of the sticker.
     * @return The sticker with the specified ID, or null if not found.
     */
    public Sticker getStickerById(String stickerId) {
        return getStickers().stream().filter(sticker -> sticker.getId().equals(stickerId)).findFirst().orElse(null);
    }

    /**
     * Retrieves the sticker with the specified ID.
     *
     * @param stickerId The ID of the sticker.
     * @return The sticker with the specified ID, or null if not found.
     */
    public Sticker getStickerById(long stickerId) {
        return getStickerById(String.valueOf(stickerId));
    }

    /**
     * Retrieves the stickers with the specified name.
     *
     * @param stickerName The name of the stickers.
     * @return The list of stickers with the specified name.
     */
    public List<Sticker> getStickersByName(String stickerName) {
        return getStickers().stream().filter(sticker -> sticker.getName().equals(stickerName)).toList();
    }

    /**
     * Retrieves the stickers created by the user with the specified ID.
     *
     * @param userId The ID of the user.
     * @return The list of stickers created by the user with the specified ID.
     */
    public List<Sticker> getStickersByAuthorId(String userId) {
        return getStickers().stream().filter(sticker -> sticker.getAuthor().getId().equals(userId)).toList();
    }

    /**
     * Retrieves the stickers created by the user with the specified ID.
     *
     * @param userId The ID of the user.
     * @return The list of stickers created by the user with the specified ID.
     */
    public List<Sticker> getStickersByAuthorId(long userId) {
        return getStickersByAuthorId(String.valueOf(userId));
    }

    /**
     * Retrieves the approximate member count of the guild.
     *
     * @return The approximate member count of the guild.
     */
    public int getMemberCount() {
        if (memberCount == null) {
            memberCount = preview.get("approximate_member_count").asInt();
        }
        return memberCount;
    }

    /**
     * Retrieves the approximate online member count of the guild.
     *
     * @return The approximate online member count of the guild.
     */
    public int getOnlineMemberCount() {
        if (onlineMemberCount == null) {
            onlineMemberCount = preview.get("approximate_presence_count").asInt();
        }
        return onlineMemberCount;
    }
}
