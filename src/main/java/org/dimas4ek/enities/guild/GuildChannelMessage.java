package org.dimas4ek.enities.guild;

import org.dimas4ek.enities.user.User;

public interface GuildChannelMessage {
    String getId();
    String getContent();
    User getAuthor();
}
