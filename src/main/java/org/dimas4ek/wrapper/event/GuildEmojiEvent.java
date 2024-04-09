package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.entities.Emoji;
import org.dimas4ek.wrapper.entities.guild.Guild;

import java.util.List;

public class GuildEmojiEvent implements Event {
    private final Guild guild;
    private final List<Emoji> emojis;

    public GuildEmojiEvent(Guild guild, List<Emoji> emojis) {
        this.guild = guild;
        this.emojis = emojis;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    public List<Emoji> getEmojis() {
        return emojis;
    }
}
