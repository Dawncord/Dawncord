package org.dimas4ek.wrapper.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.action.MessageCreateAction;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.utils.ActionExecutor;

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
    public void sendMessage(String message, Consumer<MessageCreateAction> handler) {
        ActionExecutor.createMessage(handler, message, getId(), null);
    }

    @Override
    public void sendMessage(String message) {
        ActionExecutor.createMessage(null, message, getId(), null);
    }

    @Override
    public GuildCategory getCategory() {
        if (category == null) {
            category = guild.getCategoryById(channel.get("parent_id").asText());
        }
        return category;
    }
}
