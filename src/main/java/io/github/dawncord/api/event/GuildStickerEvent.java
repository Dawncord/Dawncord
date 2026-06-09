package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.message.sticker.Sticker;

import java.util.List;

/**
 * Represents an event related to stickers in a guild.
 */
public record GuildStickerEvent(Guild guild, List<Sticker> stickers) implements Event {
    /**
     * Constructs a GuildStickerEvent with the specified guild and list of stickers.
     *
     * @param guild    The guild associated with the event.
     * @param stickers The list of stickers involved in the event.
     */
    public GuildStickerEvent {
    }

    /**
     * Retrieves the list of stickers involved in the event.
     *
     * @return The list of stickers involved in the event.
     */
    @Override
    public List<Sticker> stickers() {
        return stickers;
    }
}
