package org.dimas4ek.dawncord.event;

import org.dimas4ek.dawncord.action.MessageCreateAction;
import org.dimas4ek.dawncord.action.MessageModifyAction;
import org.dimas4ek.dawncord.action.ModalCreateAction;
import org.dimas4ek.dawncord.entities.channel.GuildChannel;
import org.dimas4ek.dawncord.entities.guild.GuildMember;

import java.util.function.Consumer;

public interface ReplyEvent extends Event {
    CallbackEvent<MessageModifyAction> reply(String message, Consumer<MessageCreateAction> handler);

    CallbackEvent<MessageModifyAction> reply(String message);

    CallbackEvent<MessageModifyAction> reply(Consumer<MessageCreateAction> handler);

    CallbackEvent<MessageCreateAction> deferReply(boolean ephemeral);

    CallbackEvent<MessageCreateAction> deferReply();

    CallbackEvent<MessageModifyAction> replyModal(Consumer<ModalCreateAction> handler);

    GuildMember getMember();

    GuildChannel getChannel();

    GuildChannel getChannelById(String channelId);

    GuildChannel getChannelById(long channelId);
}
