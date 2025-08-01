package io.github.dawncord.api.entities.message;

import io.github.dawncord.api.action.MessageModifyAction;
import io.github.dawncord.api.action.ThreadCreateAction;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.application.Application;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.channel.thread.Thread;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.entities.guild.role.GuildRole;
import io.github.dawncord.api.entities.message.component.ActionRow;
import io.github.dawncord.api.entities.message.embed.Embed;
import io.github.dawncord.api.entities.message.poll.Poll;
import io.github.dawncord.api.entities.message.sticker.Sticker;
import io.github.dawncord.api.event.CreateEvent;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.types.MessageFlag;
import io.github.dawncord.api.types.MessageType;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a message in a guild channel.
 */
public interface Message extends ISnowflake {

    /**
     * Retrieves the type of the message.
     *
     * @return The type of the message.
     */
    MessageType getType();

    /**
     * Retrieves the channel where the message was sent.
     *
     * @return The channel where the message was sent.
     */
    GuildChannel getChannel();

    /**
     * Retrieves the guild where the message was sent.
     *
     * @return The guild where the message was sent.
     */
    Guild getGuild();

    /**
     * Starts a new thread from this message with the given name.
     *
     * @param name    The name of the thread.
     * @param handler The action to be performed on the created thread.
     * @return The created thread.
     */
    CreateEvent<Thread> startThread(String name, Consumer<ThreadCreateAction> handler);

    /**
     * Starts a new thread from this message with the given name.
     *
     * @param name The name of the thread.
     */
    void startThread(String name);

    /**
     * Retrieves the content of the message.
     *
     * @return The content of the message.
     */
    String getContent();

    /**
     * Retrieves the user who sent the message.
     *
     * @return The user who sent the message.
     */
    User getFrom();

    /**
     * Retrieves the flags attached to the message.
     *
     * @return The flags attached to the message.
     */
    List<MessageFlag> getFlags();

    /**
     * Retrieves the attachments in the message.
     *
     * @return The attachments in the message.
     */
    List<Attachment> getAttachments();

    /**
     * Retrieves the embeds in the message.
     *
     * @return The embeds in the message.
     */
    List<Embed> getEmbeds();

    /**
     * Retrieves the action rows (components) in the message.
     *
     * @return The action rows (components) in the message.
     */
    List<ActionRow> getComponents();

    /**
     * Retrieves the users mentioned in the message.
     *
     * @return The users mentioned in the message.
     */
    List<User> getMentions();

    /**
     * Retrieves the roles mentioned in the message.
     *
     * @return The roles mentioned in the message.
     */
    List<GuildRole> getMentionRoles();

    /**
     * Retrieves the reactions on the message.
     *
     * @return The reactions on the message.
     */
    List<Reaction> getReactions();

    /**
     * Retrieves the reaction for the given emoji ID or name.
     *
     * @param emojiIdOrName The ID or name of the emoji.
     * @return The reaction for the given emoji.
     */
    Reaction getReaction(String emojiIdOrName);

    /**
     * Deletes reactions for the given emoji ID or name on the message.
     *
     * @param emojiIdOrName The ID or name of the emoji.
     */
    void deleteReactions(String emojiIdOrName);

    /**
     * Deletes all reactions on the message.
     */
    void deleteReactions();

    /**
     * Retrieves the application that created the message.
     *
     * @return The application that created the message.
     */
    Application getApplication();

    /**
     * Retrieves the referenced message if this is a reply.
     *
     * @return The referenced message, if any.
     */
    Message getReferencedMessage();

    /**
     * Retrieves the thread associated with this message.
     *
     * @return The thread associated with this message.
     */
    Thread getThread();

    /**
     * Retrieves the stickers attached to the message.
     *
     * @return The stickers attached to the message.
     */
    List<Sticker> getStickers();

    /**
     * Retrieves the poll attached to the message.
     *
     * @return The poll attached to the message.
     */
    Poll getPoll();

    /**
     * Retrieves the voters in the poll for the specified answer ID.
     *
     * @param answerId The ID of the answer.
     * @return The voters in the poll for the specified answer ID.
     */
    List<GuildMember> getPollVoters(String answerId);

    /**
     * Retrieves the voters in the poll for the specified answer ID.
     *
     * @param answerId The ID of the answer.
     * @return The voters in the poll for the specified answer ID.
     */
    List<GuildMember> getPollVoters(long answerId);

    /**
     * Checks if the message is pinned.
     *
     * @return True if the message is pinned, false otherwise.
     */
    boolean isPinned();

    /**
     * Checks if the message mentions everyone.
     *
     * @return True if the message mentions everyone, false otherwise.
     */
    boolean isMentionEveryone();

    /**
     * Checks if the message is a text-to-speech message.
     *
     * @return True if the message is a text-to-speech message, false otherwise.
     */
    boolean isTTS();

    /**
     * Retrieves the timestamp when the message was last edited.
     *
     * @return The timestamp when the message was last edited.
     */
    ZonedDateTime getTimeEdited();

    /**
     * Sets the reference to another message.
     *
     * @param message The message to set as reference
     */
    void setReference(Message message);

    /**
     * Modifies the message using the provided handler.
     *
     * @param handler The action to be performed to modify the message.
     * @return The modify event for the message.
     */
    ModifyEvent<Message> modify(Consumer<MessageModifyAction> handler);

    /**
     * Deletes the message.
     */
    void delete();
}
