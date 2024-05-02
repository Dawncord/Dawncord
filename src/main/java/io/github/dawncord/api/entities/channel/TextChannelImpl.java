package io.github.dawncord.api.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.action.MessageCreateAction;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.event.MessageCreateEvent;
import io.github.dawncord.api.utils.ActionExecutor;

import java.util.function.Consumer;

/**
 * Implementation of a text channel for sending and receiving text-based messages.
 */
public class TextChannelImpl extends MessageChannelImpl implements TextChannel {
    private final JsonNode channel;
    private final Guild guild;
    private GuildCategory category;

    /**
     * Constructs a new TextChannelImpl object.
     *
     * @param channel The JSON representation of the channel.
     * @param guild   The guild to which this channel belongs.
     */
    public TextChannelImpl(JsonNode channel, Guild guild) {
        super(channel, guild);
        this.channel = channel;
        this.guild = guild;
    }

    @Override
    public MessageCreateEvent sendMessage(String message, Consumer<MessageCreateAction> handler) {
        String messageId = ActionExecutor.createMessage(handler, message, getId(), null);
        return new MessageCreateEvent(getId(), messageId, guild.getId());
    }

    @Override
    public MessageCreateEvent sendMessage(String message) {
        return sendMessage(message, null);
    }

    @Override
    public MessageCreateEvent sendMessage(Consumer<MessageCreateAction> handler) {
        return sendMessage(null, handler);
    }

    @Override
    public GuildCategory getCategory() {
        if (category == null) {
            category = guild.getCategoryById(channel.get("parent_id").asText());
        }
        return category;
    }
}
