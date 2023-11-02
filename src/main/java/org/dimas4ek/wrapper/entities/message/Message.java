package org.dimas4ek.wrapper.entities.message;

import org.dimas4ek.wrapper.action.MessageModifyAction;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.application.Activity;
import org.dimas4ek.wrapper.entities.application.Application;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.message.component.ActionRow;
import org.dimas4ek.wrapper.entities.message.embed.Embed;
import org.dimas4ek.wrapper.entities.message.sticker.Sticker;
import org.dimas4ek.wrapper.entities.role.GuildRole;
import org.dimas4ek.wrapper.entities.thread.Thread;
import org.dimas4ek.wrapper.types.MessageFlag;
import org.dimas4ek.wrapper.types.MessageType;

import java.time.ZonedDateTime;
import java.util.List;

public interface Message {
    String getId();

    long getIdLong();

    MessageType getType();

    GuildChannel getChannel();

    Guild getGuild();

    String getContent();

    User getFrom();

    List<MessageFlag> getFlags();

    List<Attachment> getAttachments();

    List<Embed> getEmbeds();

    List<ActionRow> getActionRows();

    List<User> getMentions();

    List<GuildRole> getMentionRoles();

    List<Reaction> getReactions();

    Reaction getReaction(String emojiIdOrName);

    Activity getActivity(); //TODO add something to activity or remove it

    Application getApplication(); //TODO add something

    Message getReferencedMessage();

    Thread getThread();

    List<Sticker> getStickers();

    boolean isPinned();

    boolean isMentionEveryone();

    boolean isTTS();

    ZonedDateTime getTimestamp();

    ZonedDateTime getEditedTimestamp();

    MessageModifyAction modify();

    void delete();
}
