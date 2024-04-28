package org.dimas4ek.dawncord.event;

import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.entities.message.sticker.Sticker;

import java.util.List;

public class GuildStickerEvent implements Event {
    private final Guild guild;
    private final List<Sticker> stickers;

    public GuildStickerEvent(Guild guild, List<Sticker> stickers) {
        this.guild = guild;
        this.stickers = stickers;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    public List<Sticker> getStickers() {
        return stickers;
    }
}
