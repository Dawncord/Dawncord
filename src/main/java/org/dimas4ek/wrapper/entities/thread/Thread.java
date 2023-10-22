package org.dimas4ek.wrapper.entities.thread;

import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.message.Message;

import java.util.List;

public interface Thread {
    String getId();

    long getIdLong();

    String getName();

    String getType();

    Message getLastMessage();

    List<String> getFlags();

    Guild getGuild();

    GuildChannel getChannel();

    int getRateLimit();

    int getBitrate();

    int getUserLimit();

    User getCreator();

    ThreadMetaData getMetaData();

    List<Message> getMessages();

    Message getMessageById(String messageId);

    Message getMessageById(long messageId);

    List<ThreadMember> getMembers();

    ThreadMember getMemberById(String userId);

    ThreadMember getMemberById(long userId);
}
