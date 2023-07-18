package org.dimas4ek.enities;

import java.util.List;

public interface Guild {
    String getId();
    
    String getName();
    GuildMember getOwner();
    
    List<GuildChannel> getChannels();
    
    List<GuildMember> getMembers();
    
    List<GuildRole> getRoles();
    
    List<GuildRole> getRolesByName(String roleName);
    
    GuildRole getRoleById(String id);
}
