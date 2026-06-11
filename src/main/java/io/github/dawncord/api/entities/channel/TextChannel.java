package io.github.dawncord.api.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.action.message.MessageCreateAction;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.event.MessageCreateEvent;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.LazyLoader;

import java.util.function.Consumer;

/**
 * Represents a text channel for sending and receiving text-based messages.
 */
public class TextChannel extends MessageChannel {
    private final LazyLoader loader;
    private final JsonNode channel;
    private final Guild guild;
    private GuildCategory category;

    /**
     * Constructs a new TextChannel object.
     *
     * @param channel The JSON representation of the channel.
     * @param guild   The guild to which this channel belongs.
     */
    public TextChannel(JsonNode channel, Guild guild) {
        super(channel, guild);
        this.channel = channel;
        this.guild = guild;
        loader = new LazyLoader(channel);
    }

    public MessageCreateEvent sendMessage(String message, Consumer<MessageCreateAction> handler) {
        String messageId = ActionExecutor.createMessage(handler, message, getId(), null);
        return new MessageCreateEvent(getId(), messageId, guild.getId());
    }

    public MessageCreateEvent sendMessage(String message) {
        return sendMessage(message, null);
    }

    public MessageCreateEvent sendMessage(Consumer<MessageCreateAction> handler) {
        return sendMessage(null, handler);
    }

    public GuildCategory getCategory() {
        category = loader.loadIfExists(category, "parent_id", () -> guild.getCategoryById(channel.get("parent_id").asText()));
        return category;
    }
}
