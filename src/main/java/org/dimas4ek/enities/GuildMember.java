package org.dimas4ek.enities;

import java.util.List;

public interface GuildMember {
    User getUser();
    String getNickname();
    List<GuildRole> getRoles();
}
