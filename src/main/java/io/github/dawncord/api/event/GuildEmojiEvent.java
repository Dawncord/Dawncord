package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.CustomEmoji;
import io.github.dawncord.api.entities.guild.Guild;

import java.util.List;

/**
 * Represents an event related to emojis in a guild.
 */
public record GuildEmojiEvent(Guild guild, List<CustomEmoji> emojis) implements Event {
    /**
     * Constructs a GuildEmojiEvent with the specified guild and list of emojis.
     *
     * @param guild  The guild associated with the event.
     * @param emojis The list of emojis involved in the event.
     */
    public GuildEmojiEvent {
    }

    /**
     * Retrieves the list of emojis involved in the event.
     *
     * @return The list of emojis involved in the event.
     */
    @Override
    public List<CustomEmoji> emojis() {
        return emojis;
    }
}
