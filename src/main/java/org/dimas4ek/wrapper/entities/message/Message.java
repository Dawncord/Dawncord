package org.dimas4ek.wrapper.entities.message;

import org.dimas4ek.wrapper.action.MessageModifyAction;
import org.dimas4ek.wrapper.action.ThreadCreateAction;
import org.dimas4ek.wrapper.entities.ISnowflake;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.application.Application;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.thread.Thread;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildMember;
import org.dimas4ek.wrapper.entities.guild.role.GuildRole;
import org.dimas4ek.wrapper.entities.message.component.ActionRow;
import org.dimas4ek.wrapper.entities.message.embed.Embed;
import org.dimas4ek.wrapper.entities.message.poll.Poll;
import org.dimas4ek.wrapper.entities.message.sticker.Sticker;
import org.dimas4ek.wrapper.types.MessageFlag;
import org.dimas4ek.wrapper.types.MessageType;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Consumer;

public interface Message extends ISnowflake {
    MessageType getType();

    GuildChannel getChannel();

    Guild getGuild();

    void startThread(String name, Consumer<ThreadCreateAction> handler);

    void startThread(String name);

    String getContent();

    User getFrom();

    List<MessageFlag> getFlags();

    List<Attachment> getAttachments();

    List<Embed> getEmbeds();

    List<ActionRow> getComponents();

    List<User> getMentions();

    List<GuildRole> getMentionRoles();

    List<Reaction> getReactions();

    Reaction getReaction(String emojiIdOrName);

    void deleteReactions(String emojiIdOrName);

    void deleteReactions();

    Application getApplication();

    Message getReferencedMessage();

    Thread getThread();

    List<Sticker> getStickers();

    Poll getPoll();

    List<GuildMember> getPollVoters(String answerId);

    List<GuildMember> getPollVoters(long answerId);

    boolean isPinned();

    boolean isMentionEveryone();

    boolean isTTS();

    ZonedDateTime getTimestamp();

    ZonedDateTime getEditedTimestamp();

    void modify(Consumer<MessageModifyAction> handler);

    void delete();
}
