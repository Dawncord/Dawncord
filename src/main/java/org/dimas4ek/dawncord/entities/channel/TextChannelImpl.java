package org.dimas4ek.dawncord.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.action.MessageCreateAction;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.event.MessageCreateEvent;
import org.dimas4ek.dawncord.utils.ActionExecutor;

import java.util.function.Consumer;

public class TextChannelImpl extends MessageChannelImpl implements TextChannel {
    private final JsonNode channel;
    private final Guild guild;
    private GuildCategory category;

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
