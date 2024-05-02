package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.message.sticker.Sticker;

import java.util.List;

/**
 * Represents an event related to stickers in a guild.
 */
public class GuildStickerEvent implements Event {
    private final Guild guild;
    private final List<Sticker> stickers;

    /**
     * Constructs a GuildStickerEvent with the specified guild and list of stickers.
     *
     * @param guild    The guild associated with the event.
     * @param stickers The list of stickers involved in the event.
     */
    public GuildStickerEvent(Guild guild, List<Sticker> stickers) {
        this.guild = guild;
        this.stickers = stickers;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    /**
     * Retrieves the list of stickers involved in the event.
     *
     * @return The list of stickers involved in the event.
     */
    public List<Sticker> getStickers() {
        return stickers;
    }
}
