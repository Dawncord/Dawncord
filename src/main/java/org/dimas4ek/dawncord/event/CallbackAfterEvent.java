package org.dimas4ek.dawncord.event;

import org.dimas4ek.dawncord.ApiClient;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.action.MessageCreateAction;
import org.dimas4ek.dawncord.action.MessageModifyAction;
import org.dimas4ek.dawncord.entities.guild.GuildImpl;
import org.dimas4ek.dawncord.entities.message.Message;
import org.dimas4ek.dawncord.entities.message.MessageImpl;
import org.dimas4ek.dawncord.interaction.InteractionData;
import org.dimas4ek.dawncord.utils.ActionExecutor;
import org.dimas4ek.dawncord.utils.JsonUtils;

import java.util.function.Consumer;

public class CallbackAfterEvent<T> {
    private final InteractionData data;
    private final boolean ephemeral;
    private final boolean defer;

    public CallbackAfterEvent(InteractionData data, boolean ephemeral, boolean defer) {
        this.data = data;
        this.ephemeral = ephemeral;
        this.defer = defer;
    }

    public Message get() {
        return new MessageImpl(
                JsonUtils.fetch(Routes.OriginalMessage(data.getInteraction().getInteractionToken())),
                new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(data.getGuildId())))
        );
    }

    public void edit(Consumer<T> handler) {
        if (defer) {
            ActionExecutor.deferReply((Consumer<MessageCreateAction>) handler, data, ephemeral);
        } else {
            ActionExecutor.modifyMessage((Consumer<MessageModifyAction>) handler, get(), data);
        }
    }

    public void delete() {
        if (defer) {
            edit(null);
        }
        ApiClient.delete(Routes.OriginalMessage(data.getInteraction().getInteractionToken()));
    }
}
