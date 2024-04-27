package org.dimas4ek.dawncord.entities.message;

import org.dimas4ek.dawncord.action.MessageModifyAction;
import org.dimas4ek.dawncord.action.ThreadCreateAction;
import org.dimas4ek.dawncord.entities.ISnowflake;
import org.dimas4ek.dawncord.entities.User;
import org.dimas4ek.dawncord.entities.application.Application;
import org.dimas4ek.dawncord.entities.channel.GuildChannel;
import org.dimas4ek.dawncord.entities.channel.thread.Thread;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.entities.guild.GuildMember;
import org.dimas4ek.dawncord.entities.guild.role.GuildRole;
import org.dimas4ek.dawncord.entities.message.component.ActionRow;
import org.dimas4ek.dawncord.entities.message.embed.Embed;
import org.dimas4ek.dawncord.entities.message.poll.Poll;
import org.dimas4ek.dawncord.entities.message.sticker.Sticker;
import org.dimas4ek.dawncord.event.CreateEvent;
import org.dimas4ek.dawncord.event.ModifyEvent;
import org.dimas4ek.dawncord.types.MessageFlag;
import org.dimas4ek.dawncord.types.MessageType;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Consumer;

public interface Message extends ISnowflake {
    MessageType getType();

    GuildChannel getChannel();

    Guild getGuild();

    CreateEvent<Thread> startThread(String name, Consumer<ThreadCreateAction> handler);

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

    ModifyEvent<Message> modify(Consumer<MessageModifyAction> handler);

    void delete();
}
