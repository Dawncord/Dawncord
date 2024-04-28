package org.dimas4ek.dawncord.entities.guild;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.entities.CustomEmoji;
import org.dimas4ek.dawncord.entities.CustomEmojiImpl;
import org.dimas4ek.dawncord.entities.Emoji;
import org.dimas4ek.dawncord.entities.ISnowflake;
import org.dimas4ek.dawncord.entities.image.DiscoverySplash;
import org.dimas4ek.dawncord.entities.image.Splash;
import org.dimas4ek.dawncord.entities.message.sticker.Sticker;
import org.dimas4ek.dawncord.entities.message.sticker.StickerImpl;
import org.dimas4ek.dawncord.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

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

    public String getName() {
        if (name == null) {
            name = preview.get("name").asText();
        }
        return name;
    }

    public String getDescription() {
        if (description == null) {
            description = preview.get("description").asText();
        }
        return description;
    }

    public Splash getSplash() {
        if (splash == null) {
            splash = preview.has("splash")
                    ? new Splash(getId(), preview.get("splash").asText())
                    : null;
        }
        return splash;
    }

    public DiscoverySplash getDiscoverySplash() {
        if (discoverySplash == null) {
            discoverySplash = preview.has("discovery_splash")
                    ? new DiscoverySplash(getId(), preview.get("discovery_splash").asText())
                    : null;
        }
        return discoverySplash;
    }

    public List<CustomEmoji> getEmojis() {
        if (emojis == null) {
            emojis = JsonUtils.getEntityList(preview.get("emojis"), emoji -> new CustomEmojiImpl(emoji, guild));
        }
        return emojis;
    }

    public Emoji getEmojiById(String emojiId) {
        return getEmojis().stream().filter(emoji -> emoji.getId().equals(emojiId)).findFirst().orElse(null);
    }

    public Emoji getEmojiById(long emojiId) {
        return getEmojiById(String.valueOf(emojiId));
    }

    public List<CustomEmoji> getEmojisByName(String emojiName) {
        return getEmojis().stream().filter(emoji -> emoji.getName().equals(emojiName)).toList();
    }

    public List<CustomEmoji> getEmojisByCreatorId(String userId) {
        return getEmojis().stream().filter(emoji -> emoji.getCreator().getId().equals(userId)).toList();
    }

    public List<CustomEmoji> getEmojisByCreatorId(long userId) {
        return getEmojisByCreatorId(String.valueOf(userId));
    }

    public List<String> getFeatures() {
        if (features == null) {
            features = new ArrayList<>();
            for (JsonNode feature : preview.get("features")) {
                features.add(feature.asText());
            }
        }
        return features;
    }

    public List<Sticker> getStickers() {
        if (stickers == null) {
            stickers = JsonUtils.getEntityList(preview.get("stickers"), sticker -> new StickerImpl(sticker, guild));
        }
        return stickers;
    }

    public Sticker getStickerById(String stickerId) {
        return getStickers().stream().filter(sticker -> sticker.getId().equals(stickerId)).findFirst().orElse(null);
    }

    public Sticker getStickerById(long stickerId) {
        return getStickerById(String.valueOf(stickerId));
    }

    public List<Sticker> getStickersByName(String stickerName) {
        return getStickers().stream().filter(sticker -> sticker.getName().equals(stickerName)).toList();
    }

    public List<Sticker> getStickersByAuthorId(String userId) {
        return getStickers().stream().filter(sticker -> sticker.getAuthor().getId().equals(userId)).toList();
    }

    public List<Sticker> getStickersByAuthorId(long userId) {
        return getStickersByAuthorId(String.valueOf(userId));
    }

    public int getMemberCount() {
        if (memberCount == null) {
            memberCount = preview.get("approximate_member_count").asInt();
        }
        return memberCount;
    }

    public int getOnlineMemberCount() {
        if (onlineMemberCount == null) {
            onlineMemberCount = preview.get("approximate_presence_count").asInt();
        }
        return onlineMemberCount;
    }
}
