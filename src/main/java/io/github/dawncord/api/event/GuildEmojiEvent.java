package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.CustomEmoji;
import io.github.dawncord.api.entities.guild.Guild;

import java.util.List;

/**
 * Represents an event related to emojis in a guild.
 */
public class GuildEmojiEvent implements Event {
    private final Guild guild;
    private final List<CustomEmoji> emojis;

    /**
     * Constructs a GuildEmojiEvent with the specified guild and list of emojis.
     *
     * @param guild  The guild associated with the event.
     * @param emojis The list of emojis involved in the event.
     */
    public GuildEmojiEvent(Guild guild, List<CustomEmoji> emojis) {
        this.guild = guild;
        this.emojis = emojis;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    /**
     * Retrieves the list of emojis involved in the event.
     *
     * @return The list of emojis involved in the event.
     */
    public List<CustomEmoji> getEmojis() {
        return emojis;
    }
}
