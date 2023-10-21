package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.entities.IMentionable;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.message.Message;

public interface Channel extends IMentionable {
    String getId();

    long getIdLong();

    Guild getGuild();

    Message getLastMessage();

    Message getMessageById(String messageId);

    Message getMessageById(long messageId);

    boolean isNsfw();

    GuildCategory getCategory();
}
