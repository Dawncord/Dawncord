package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.entities.Mentionable;
import org.dimas4ek.wrapper.entities.message.Message;

public interface Channel extends Mentionable {
    String getId();
    long getIdLong();
    Message getLastMessage();
    Message getMessageById(String messageId);
    Message getMessageById(long messageId);
    boolean isNsfw();
    GuildCategory getCategory();
}
