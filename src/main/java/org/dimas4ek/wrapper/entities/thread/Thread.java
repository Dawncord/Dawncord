package org.dimas4ek.wrapper.entities.thread;

import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.MessageChannel;

import java.util.List;

public interface Thread extends MessageChannel {
    GuildChannel getChannel();

    User getCreator();

    ThreadMetaData getMetaData();

    List<ThreadMember> getThreadMembers();

    ThreadMember getThreadMemberById(String userId);

    ThreadMember getThreadMemberById(long userId);
}
