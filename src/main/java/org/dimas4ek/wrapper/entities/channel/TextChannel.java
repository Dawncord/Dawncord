package org.dimas4ek.wrapper.entities.channel;

public interface TextChannel extends MessageChannel {
    void sendMessage(String message);

    GuildCategory getCategory();
}
