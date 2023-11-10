package org.dimas4ek.wrapper.entities;

import org.dimas4ek.wrapper.action.EmojiModifyAction;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.role.GuildRole;

import java.util.List;

public interface Emoji {
    String getId();

    long getIdLong();

    String getName();

    Guild getGuild();

    List<GuildRole> getRoles();

    User getCreator();

    boolean isRequiredColons();

    boolean isManaged();

    boolean isAnimated();

    boolean isAvailable();

    EmojiModifyAction modify();

    void delete();
}
