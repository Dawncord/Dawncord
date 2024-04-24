package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.action.MessageCreateAction;
import org.dimas4ek.wrapper.action.MessageModifyAction;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.GuildMember;

import java.util.function.Consumer;

public interface ReplyEvent extends Event {
    CallbackEvent<MessageModifyAction> reply(String message, Consumer<MessageCreateAction> handler);

    CallbackEvent<MessageModifyAction> reply(String message);

    CallbackEvent<MessageModifyAction> reply(Consumer<MessageCreateAction> handler);

    CallbackEvent<MessageCreateAction> deferReply(boolean ephemeral);

    CallbackEvent<MessageCreateAction> deferReply();

    GuildMember getMember();

    GuildChannel getChannel();

    GuildChannel getChannelById(String channelId);

    GuildChannel getChannelById(long channelId);
}
