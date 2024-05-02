package io.github.dawncord.api.entities.message.sticker;

import io.github.dawncord.api.action.GuildStickerModifyAction;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.types.StickerFormatType;
import io.github.dawncord.api.types.StickerType;

import java.util.function.Consumer;

/**
 * Represents a sticker.
 */
public interface Sticker extends ISnowflake {
    /**
     * Gets the name of the sticker.
     *
     * @return The name of the sticker.
     */
    String getName();

    /**
     * Gets the description of the sticker.
     *
     * @return The description of the sticker.
     */
    String getDescription();

    /**
     * Gets the emoji associated with the sticker.
     *
     * @return The emoji associated with the sticker.
     */
    String getEmoji();

    /**
     * Gets the type of the sticker.
     *
     * @return The type of the sticker.
     */
    StickerType getType();

    /**
     * Gets the format type of the sticker.
     *
     * @return The format type of the sticker.
     */
    StickerFormatType getFormatType();

    /**
     * Checks if the sticker is available.
     *
     * @return True if the sticker is available, false otherwise.
     */
    boolean isAvailable();

    /**
     * Gets the guild that the sticker belongs to.
     *
     * @return The guild that the sticker belongs to.
     */
    Guild getGuild();

    /**
     * Gets the author of the sticker.
     *
     * @return The author of the sticker.
     */
    User getAuthor();

    /**
     * Modifies the sticker with the provided handler.
     *
     * @param handler The handler to modify the sticker.
     * @return The modify event for the sticker.
     */
    ModifyEvent<Sticker> modify(Consumer<GuildStickerModifyAction> handler);

    /**
     * Deletes the sticker.
     */
    void delete();
}
