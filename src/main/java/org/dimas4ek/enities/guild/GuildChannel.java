package org.dimas4ek.enities.guild;

public interface GuildChannel {
    String getId();
    String getName();
    String getType();
    void sendMessage(String message);
}
