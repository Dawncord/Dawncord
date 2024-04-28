package org.dimas4ek.dawncord.entities;

import org.dimas4ek.dawncord.action.EmojiModifyAction;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.entities.guild.role.GuildRole;
import org.dimas4ek.dawncord.event.ModifyEvent;

import java.util.List;
import java.util.function.Consumer;

public interface CustomEmoji extends Emoji {
    Guild getGuild();

    List<GuildRole> getRoles();

    User getCreator();

    boolean isRequiredColons();

    boolean isManaged();

    boolean isAnimated();

    boolean isAvailable();

    ModifyEvent<CustomEmoji> modify(Consumer<EmojiModifyAction> handler);

    void delete();
}
