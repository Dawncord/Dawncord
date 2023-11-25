package org.dimas4ek.wrapper.entities.message.sticker;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.action.GuildStickerModifyAction;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildImpl;
import org.dimas4ek.wrapper.utils.ActionExecutor;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONObject;

import java.util.function.Consumer;

public class StickerImpl implements Sticker {
    private final JSONObject sticker;

    public StickerImpl(JSONObject sticker) {
        this.sticker = sticker;
    }

    @Override
    public String getId() {
        return sticker.getString("id");
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        return sticker.getString("name");
    }

    @Override
    public String getDescription() {
        return sticker.getString("description");
    }

    @Override
    public String getEmoji() {
        return sticker.getString("tags");
    }

    @Override
    public StickerType getType() {
        for (StickerType type : StickerType.values()) {
            if (type.getValue() == sticker.getInt("type")) {
                return type;
            }
        }
        return null;
    }

    @Override
    public StickerFormatType getFormatType() {
        for (StickerFormatType type : StickerFormatType.values()) {
            if (type.getValue() == sticker.getInt("format_type")) {
                return type;
            }
        }
        return null;
    }

    @Override
    public boolean isAvailable() {
        return sticker.getBoolean("available");
    }

    @Override
    public Guild getGuild() {
        return new GuildImpl(JsonUtils.fetchEntity("/guilds/" + sticker.getString("guild_id")));
    }

    @Override
    public User getAuthor() {
        return new UserImpl(sticker.getJSONObject("user"));
    }

    @Override
    public void modify(Consumer<GuildStickerModifyAction> handler) {
        ActionExecutor.modifyGuildSticker(handler, getGuild().getId(), getId());
    }

    @Override
    public void delete() {
        ApiClient.delete("/guilds/" + getGuild().getId() + "/stickers/" + getId());
    }
}
