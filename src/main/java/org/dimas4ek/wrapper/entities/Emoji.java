package org.dimas4ek.wrapper.entities;

import org.dimas4ek.wrapper.action.EmojiModifyAction;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.role.GuildRole;

import java.util.List;
import java.util.function.Consumer;

public interface Emoji extends ISnowflake {
    String getName();

    Guild getGuild();

    List<GuildRole> getRoles();

    User getCreator();

    boolean isRequiredColons();

    boolean isManaged();

    boolean isAnimated();

    boolean isAvailable();

    void modify(Consumer<EmojiModifyAction> handler);

    void delete();
}
