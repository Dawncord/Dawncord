package org.dimas4ek.enities.guild;

import java.util.List;

public interface Guild {
    String getId();
    String getName();
    GuildMember getOwner();
    List<GuildChannel> getChannels();
    GuildChannel getChannelById(String id);
    List<GuildChannel> getChannelsByName(String name);
    List<GuildMember> getMembers();
    List<GuildRole> getRoles();
    List<GuildRole> getRolesByName(String roleName);
    GuildRole getRoleById(String id);
    void createTextChannel(String name);
    void createTextChannel(String name, GuildCategory category);
    void createVoiceChannel(String name);
    void createVoiceChannel(String name, GuildCategory category);
    void createForumChannel(String name);
    void createForumChannel(String name, GuildCategory category);
    void createStageChannel(String name);
    void createStageChannel(String name, GuildCategory category);
    void createNewsChannel(String name);
    void createNewsChannel(String name, GuildCategory category);
    void createCategory(String name);
    GuildCategory getCategoryByName(String name);
    GuildCategory getCategoryById(String id);
}
