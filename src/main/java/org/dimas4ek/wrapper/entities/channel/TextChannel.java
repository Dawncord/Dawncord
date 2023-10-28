package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.action.MessageCreateAction;
import org.slf4j.helpers.CheckReturnValue;

public interface TextChannel extends MessageChannel {
    @CheckReturnValue
    MessageCreateAction sendMessage(String message);

    GuildCategory getCategory();
}
