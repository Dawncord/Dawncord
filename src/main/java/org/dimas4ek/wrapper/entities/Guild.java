package org.dimas4ek.wrapper.entities;

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
    List<GuildRole> getRolesByName(String roleName);
}
