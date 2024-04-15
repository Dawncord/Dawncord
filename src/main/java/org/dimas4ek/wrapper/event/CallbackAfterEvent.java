package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.Constants;
import org.dimas4ek.wrapper.action.MessageCreateAction;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.entities.message.MessageImpl;
import org.dimas4ek.wrapper.interaction.InteractionData;
import org.dimas4ek.wrapper.utils.ActionExecutor;
import org.dimas4ek.wrapper.utils.JsonUtils;

import java.util.function.Consumer;

public class CallbackAfterEvent {
    private final InteractionData data;
    private final boolean ephemeral;

    CallbackAfterEvent(InteractionData data, boolean ephemeral) {
        this.data = data;
        this.ephemeral = ephemeral;
    }

    public Message get() {
        return new MessageImpl(
                JsonUtils.fetchEntity("/webhooks/" + Constants.APPLICATION_ID + "/" + data.getInteraction().getInteractionToken() + "/messages/@original"),
                data.getGuild()
        );
    }

    public void edit(Consumer<MessageCreateAction> handler) {
        ActionExecutor.deferReply(data, ephemeral, handler);
    }

    public void delete() {
        ActionExecutor.deferReply(data, ephemeral, null);
        ApiClient.delete("/webhooks/" + Constants.APPLICATION_ID + "/" + data.getInteraction().getInteractionToken() + "/messages/@original");
    }
}
