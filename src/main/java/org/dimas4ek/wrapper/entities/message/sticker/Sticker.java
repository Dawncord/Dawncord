package org.dimas4ek.wrapper.entities.message.sticker;

import org.dimas4ek.wrapper.action.GuildStickerModifyAction;
import org.dimas4ek.wrapper.entities.ISnowflake;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.guild.Guild;

import java.util.function.Consumer;

public interface Sticker extends ISnowflake {
    String getName();

    String getDescription();

    String getEmoji();

    StickerType getType();

    StickerFormatType getFormatType();

    boolean isAvailable();

    Guild getGuild();

    User getAuthor();

    void modify(Consumer<GuildStickerModifyAction> handler);

    void delete();
}
