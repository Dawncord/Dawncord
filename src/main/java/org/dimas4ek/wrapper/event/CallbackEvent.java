package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.interaction.InteractionData;

import java.util.function.Consumer;

public class CallbackEvent {
    private final InteractionData data;
    private final boolean ephemeral;

    public CallbackEvent(InteractionData data, boolean ephemeral) {
        this.data = data;
        this.ephemeral = ephemeral;
    }

    public void then(Consumer<CallbackAfterEvent> reply) {
        reply.accept(new CallbackAfterEvent(data, ephemeral));
    }
}
