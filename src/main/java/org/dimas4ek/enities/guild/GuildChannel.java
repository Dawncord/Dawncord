package org.dimas4ek.enities.guild;

import org.dimas4ek.enities.guild.action.MessageCreateAction;

import java.util.List;

public interface GuildChannel {
    String getId();
    String getName();
    String getType();
    MessageCreateAction sendMessage(String message);
    GuildChannelMessage getMessageById(String id);
    GuildChannelMessage getLastMessage();
    List<GuildChannelMessage> getMessages();
    List<GuildChannelMessage> getMessagesBefore(String beforeId);
    List<GuildChannelMessage> getMessagesAfter(String afterId);
}
