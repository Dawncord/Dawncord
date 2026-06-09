package io.github.dawncord.api.entities.message;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.guildsticker.GuildStickerModifyAction;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.UserImpl;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.types.StickerFormatType;
import io.github.dawncord.api.types.StickerType;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.EnumUtils;

import java.util.function.Consumer;

/**
 * Represents a sticker implementation.
 */
public class Sticker implements ISnowflake {
    private final JsonNode sticker;
    private final Guild guild;
    private String id;
    private String name;
    private String description;
    private String emoji;
    private StickerType type;
    private StickerFormatType formatType;
    private Boolean isAvailable;
    private User author;

    /**
     * Constructs a new StickerImpl instance.
     *
     * @param sticker The JSON node representing the sticker.
     * @param guild   The guild to which the sticker belongs.
     */
    public Sticker(JsonNode sticker, Guild guild) {
        this.sticker = sticker;
        this.guild = guild;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = sticker.get("id").asText();
        }
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    public String getName() {
        if (name == null) {
            name = sticker.get("name").asText();
        }
        return name;
    }

    public String getDescription() {
        if (description == null) {
            description = sticker.get("description").asText();
        }
        return description;
    }

    public String getEmoji() {
        if (emoji == null) {
            emoji = sticker.get("tags").asText();
        }
        return emoji;
    }

    public StickerType getType() {
        if (type == null) {
            type = EnumUtils.getEnumObject(sticker, "type", StickerType.class);
        }
        return type;
    }

    public StickerFormatType getFormatType() {
        if (formatType == null) {
            formatType = EnumUtils.getEnumObject(sticker, "format_type", StickerFormatType.class);
        }
        return formatType;
    }

    public boolean isAvailable() {
        if (isAvailable == null) {
            isAvailable = sticker.get("available").asBoolean();
        }
        return isAvailable;
    }

    public Guild getGuild() {
        return guild;
    }

    public User getAuthor() {
        if (author == null) {
            author = sticker.has("user")
                    ? new UserImpl(sticker.get("user"))
                    : null;
        }
        return author;
    }

    public ModifyEvent<Sticker> modify(Consumer<GuildStickerModifyAction> handler) {
        ActionExecutor.modifyGuildSticker(handler, getGuild().getId(), getId());
        return new ModifyEvent<>(guild.getStickerById(getId()));
    }

    public void delete() {
        ApiClient.delete(Routes.Guild.Sticker.Get(getGuild().getId(), getId()));
    }
}
