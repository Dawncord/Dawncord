package org.dimas4ek.dawncord.event;

import org.dimas4ek.dawncord.ApiClient;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.action.MessageModifyAction;
import org.dimas4ek.dawncord.entities.guild.GuildImpl;
import org.dimas4ek.dawncord.entities.message.Message;
import org.dimas4ek.dawncord.entities.message.MessageImpl;
import org.dimas4ek.dawncord.utils.ActionExecutor;
import org.dimas4ek.dawncord.utils.JsonUtils;

import java.util.function.Consumer;

public class MessageCreateAfterEvent {
    private final String channelId;
    private final String messageId;
    private final String guildId;

    public MessageCreateAfterEvent(String channelId, String messageId, String guildId) {
        this.channelId = channelId;
        this.messageId = messageId;
        this.guildId = guildId;
    }

    public Message get() {
        return new MessageImpl(
                JsonUtils.fetch(Routes.Channel.Message.Get(channelId, messageId)),
                new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(guildId)))
        );
    }

    public void edit(Consumer<MessageModifyAction> handler) {
        ActionExecutor.modifyMessage(handler, get(), null);
    }

    public void delete() {
        ApiClient.delete(Routes.Channel.Message.Get(channelId, messageId));
    }
}
