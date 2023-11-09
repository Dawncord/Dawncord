package org.dimas4ek.wrapper.entities.guild;

import org.dimas4ek.wrapper.action.GuildModifyAction;
import org.dimas4ek.wrapper.entities.GuildBan;
import org.dimas4ek.wrapper.entities.GuildMember;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.channel.GuildCategory;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.audit.AuditLog;
import org.dimas4ek.wrapper.entities.guild.event.GuildEvent;
import org.dimas4ek.wrapper.entities.role.GuildRole;
import org.dimas4ek.wrapper.entities.thread.Thread;

import java.util.List;

public interface Guild {
    String getId();

    long getIdLong();

    String getName();

    String getDescription();

    User getOwner();

    List<GuildChannel> getChannels();

    GuildChannel getChannelById(String channelId);

    GuildChannel getChannelById(long channelId);

    List<GuildMember> getMembers();

    GuildMember getMemberById(String memberId);

    GuildMember getMemberById(long memberId);

    List<GuildMember> searchMembers(String username, int limit);

    List<GuildMember> searchMembers(String username);

    List<GuildBan> getBans();

    GuildBan getBanByUserId(String userId);

    GuildBan getBanByUserId(long userId);

    List<GuildRole> getRoles();

    GuildRole getRoleById(String roleId);

    GuildRole getRoleById(long roleId);

    GuildRole getPublicRole();

    List<GuildRole> getRolesByName(String roleName);

    List<GuildCategory> getCategories();

    GuildCategory getCategoryById(String categoryId);

    GuildCategory getCategoryById(long categoryId);

    GuildModifyAction modify();

    void delete();

    boolean hasActiveThreads();

    int getActiveThreadsCount();

    List<Thread> getActiveThreads();

    List<Thread> getPublicArchiveThreads(String channelId);

    List<Thread> getPublicArchiveThreads(long channelId);

    List<Thread> getPrivateArchiveThreads(String channelId);

    List<Thread> getPrivateArchiveThreads(long channelId);

    List<Thread> getJoinedPrivateArchiveThreads(String channelId);

    List<Thread> getJoinedPrivateArchiveThreads(long channelId);

    Thread getThreadById(String threadId);

    Thread getThreadById(long threadId);

    List<GuildEvent> getGuildEvents();

    GuildEvent getGuildEventById(String eventId);

    GuildEvent getGuildEventById(long eventId);

    AuditLog getAuditLog();
}
