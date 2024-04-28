package org.dimas4ek.dawncord.event;

import org.dimas4ek.dawncord.entities.CustomEmoji;
import org.dimas4ek.dawncord.entities.guild.Guild;

import java.util.List;

public class GuildEmojiEvent implements Event {
    private final Guild guild;
    private final List<CustomEmoji> emojis;

    public GuildEmojiEvent(Guild guild, List<CustomEmoji> emojis) {
        this.guild = guild;
        this.emojis = emojis;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    public List<CustomEmoji> getEmojis() {
        return emojis;
    }
}
