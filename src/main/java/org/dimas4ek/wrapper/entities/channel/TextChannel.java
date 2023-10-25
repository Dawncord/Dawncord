package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.action.MessageCreateAction;

public interface TextChannel extends MessageChannel {
    MessageCreateAction sendMessage(String message);

    GuildCategory getCategory();
}
