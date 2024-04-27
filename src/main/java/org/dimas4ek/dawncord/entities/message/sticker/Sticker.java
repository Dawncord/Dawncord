package org.dimas4ek.dawncord.entities.message.sticker;

import org.dimas4ek.dawncord.action.GuildStickerModifyAction;
import org.dimas4ek.dawncord.entities.ISnowflake;
import org.dimas4ek.dawncord.entities.User;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.event.ModifyEvent;
import org.dimas4ek.dawncord.types.StickerFormatType;
import org.dimas4ek.dawncord.types.StickerType;

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

    ModifyEvent<Sticker> modify(Consumer<GuildStickerModifyAction> handler);

    void delete();
}
