package org.dimas4ek.wrapper.entities.guild;

import org.dimas4ek.wrapper.entities.Emoji;
import org.dimas4ek.wrapper.entities.EmojiImpl;
import org.dimas4ek.wrapper.entities.image.DiscoverySplash;
import org.dimas4ek.wrapper.entities.image.Splash;
import org.dimas4ek.wrapper.entities.message.sticker.Sticker;
import org.dimas4ek.wrapper.entities.message.sticker.StickerImpl;
import org.dimas4ek.wrapper.types.GuildFeature;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONObject;

import java.util.List;

public class GuildPreview {
    private final Guild guild;
    private final JSONObject preview;

    public GuildPreview(Guild guild, JSONObject preview) {
        this.guild = guild;
        this.preview = preview;
    }

    public String getId() {
        return preview.getString("id");
    }

    public long getIdLong() {
        return Long.parseLong(getId());
    }

    public String getName() {
        return preview.getString("name");
    }

    public String getDescription() {
        return preview.getString("description");
    }

    public Splash getSplash() {
        String splash = preview.optString("splash", null);
        return splash != null ? new Splash(getId(), splash) : null;
    }

    public DiscoverySplash getDiscoverySplash() {
        String splash = preview.optString("discovery_splash", null);
        return splash != null ? new DiscoverySplash(getId(), splash) : null;
    }

    public List<Emoji> getEmojis() {
        return JsonUtils.getEntityList(preview.getJSONArray("emojis"), (JSONObject t) -> new EmojiImpl(guild, t));
    }

    public Emoji getEmojiById(String emojiId) {
        return getEmojis().stream().filter(emoji -> emoji.getId().equals(emojiId)).findFirst().orElse(null);
    }

    public Emoji getEmojiById(long emojiId) {
        return getEmojiById(String.valueOf(emojiId));
    }

    public List<Emoji> getEmojisByName(String emojiName) {
        return getEmojis().stream().filter(emoji -> emoji.getName().equals(emojiName)).toList();
    }

    public List<Emoji> getEmojisByCreatorId(String userId) {
        return getEmojis().stream().filter(emoji -> emoji.getCreator().getId().equals(userId)).toList();
    }

    public List<Emoji> getEmojisByCreatorId(long userId) {
        return getEmojisByCreatorId(String.valueOf(userId));
    }

    public List<GuildFeature> getFeatures() {
        return EnumUtils.getEnumList(preview.getJSONArray("features"), GuildFeature.class);
    }

    public List<Sticker> getStickers() {
        return JsonUtils.getEntityList(preview.getJSONArray("stickers"), StickerImpl::new);
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
        return preview.getInt("approximate_member_count");
    }

    public int getOnlineMemberCount() {
        return preview.getInt("approximate_presence_count");
    }
}
