package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.action.MessageCreateAction;
import org.dimas4ek.wrapper.utils.ActionExecutor;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONObject;

import java.util.function.Consumer;

public class TextChannelImpl extends MessageChannelImpl implements TextChannel {
    private final JSONObject channel;

    public TextChannelImpl(JSONObject channel) {
        super(channel);
        this.channel = channel;
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
        return new GuildCategoryImpl(JsonUtils.fetchEntity("/channels/" + channel.getString("parent_id")));
    }
}
