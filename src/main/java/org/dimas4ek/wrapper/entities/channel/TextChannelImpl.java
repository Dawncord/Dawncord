package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.action.MessageCreateAction;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONObject;

public class TextChannelImpl extends MessageChannelImpl implements TextChannel {
    private final JSONObject channel;

    public TextChannelImpl(JSONObject channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public MessageCreateAction sendMessage(String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", message);
        return new MessageCreateAction(jsonObject, channel);
    }

    @Override
    public GuildCategory getCategory() {
        return (GuildCategory) new ChannelImpl(JsonUtils.fetchEntity("/channels/" + channel.getString("parent_id")));
    }
}
