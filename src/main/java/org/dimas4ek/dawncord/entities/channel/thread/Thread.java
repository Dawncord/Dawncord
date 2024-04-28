package org.dimas4ek.dawncord.entities.channel.thread;

import org.dimas4ek.dawncord.entities.User;
import org.dimas4ek.dawncord.entities.channel.GuildChannel;
import org.dimas4ek.dawncord.entities.channel.MessageChannel;

import java.util.List;

public interface Thread extends MessageChannel {
    GuildChannel getChannel();

    User getCreator();

    ThreadMetadata getMetaData();

    List<ThreadMember> getThreadMembers();

    ThreadMember getThreadMemberById(String userId);

    ThreadMember getThreadMemberById(long userId);

    void join();

    void join(String userId);

    void join(long userId);

    void leave();

    void leave(String userId);

    void leave(long userId);
}
