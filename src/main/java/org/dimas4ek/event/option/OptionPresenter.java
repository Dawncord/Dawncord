package org.dimas4ek.event.option;

import org.dimas4ek.enities.guild.GuildChannel;
import org.dimas4ek.enities.guild.GuildRole;
import org.dimas4ek.enities.user.User;

public interface OptionPresenter {
    String asText();
    int asInt();
    double asDouble();
    long asLong();
    boolean asBoolean();
    User asUser();
    GuildChannel asChannel();
    GuildRole asRole();
}
