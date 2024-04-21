package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.Constants;
import org.dimas4ek.wrapper.action.MessageCreateAction;
import org.dimas4ek.wrapper.action.MessageModifyAction;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.entities.message.MessageImpl;
import org.dimas4ek.wrapper.interaction.InteractionData;
import org.dimas4ek.wrapper.utils.ActionExecutor;
import org.dimas4ek.wrapper.utils.JsonUtils;

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
                JsonUtils.fetchEntity("/webhooks/" + Constants.APPLICATION_ID + "/" + data.getInteraction().getInteractionToken() + "/messages/@original"),
                data.getGuild()
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
        ApiClient.delete("/webhooks/" + Constants.APPLICATION_ID + "/" + data.getInteraction().getInteractionToken() + "/messages/@original");
    }
}
