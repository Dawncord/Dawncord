package org.dimas4ek.wrapper.entities.message;

import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.message.component.ActionRow;
import org.dimas4ek.wrapper.entities.message.embed.Embed;

import java.time.ZonedDateTime;
import java.util.List;

public interface Message {
    String getId();
    String getType();
    GuildChannel getChannel();
    String getContent();
    User getFrom();
    List<Attachment> getAttachments();
    List<Embed> getEmbeds();
    List<User> getMentions();
    List<ActionRow> getActionRows();
    boolean isPinned();
    boolean isMentionEveryone();
    boolean isTTS();
    ZonedDateTime getTimestamp();
    ZonedDateTime getEditedTimestamp();
}
