package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.entities.message.Message;

public interface Channel {
    String getId();
    long getIdLong();
    String getName();
    String getType();
    Message getLastMessage();
    Message getMessageById(String messageId);
    Message getMessageById(long messageId);
    boolean isNsfw();
    GuildCategory getCategory();
    String getAsMention();
}
