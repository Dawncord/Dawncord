package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.message.Message;

/**
 * Represents an event triggered by the receipt of a message.
 */
public class MessageEvent implements Event {
    private final Message message;
    private final GuildChannel guildChannel;
    private final Guild guild;

    /**
     * Initializes a new message event.
     *
     * @param message      The message associated with the event.
     * @param guildChannel The guild channel where the message was received.
     * @param guild        The guild where the message was received.
     */
    public MessageEvent(Message message, GuildChannel guildChannel, Guild guild) {
        this.message = message;
        this.guildChannel = guildChannel;
        this.guild = guild;
    }

    /**
     * Retrieves the message associated with the event.
     *
     * @return The message associated with the event.
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Retrieves the guild channel where the message was received.
     *
     * @return The guild channel where the message was received.
     */
    public GuildChannel getChannel() {
        return guildChannel;
    }

    /**
     * Retrieves the guild channel by its ID.
     *
     * @param channelId The ID of the guild channel to retrieve.
     * @return The guild channel with the specified ID.
     */
    public GuildChannel getChannelById(String channelId) {
        return guild.getChannelById(channelId);
    }

    /**
     * Retrieves the guild channel by its ID.
     *
     * @param channelId The ID of the guild channel to retrieve.
     * @return The guild channel with the specified ID.
     */
    public GuildChannel getChannelById(long channelId) {
        return getChannelById(String.valueOf(channelId));
    }

    @Override
    public Guild getGuild() {
        return guild;
    }
}