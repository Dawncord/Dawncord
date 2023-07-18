package org.dimas4ek.enities.guild;

import org.dimas4ek.enities.user.User;

import java.util.List;

public interface GuildMember {
    User getUser();
    String getNickname();
    List<GuildRole> getRoles();
}
