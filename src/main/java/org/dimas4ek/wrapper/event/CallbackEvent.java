package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.interaction.InteractionData;

import java.util.function.Consumer;

public class CallbackEvent<T> {
    private final InteractionData data;
    private final boolean ephemeral;
    private final boolean defer;

    public CallbackEvent(InteractionData data, boolean ephemeral, boolean defer) {
        this.data = data;
        this.ephemeral = ephemeral;
        this.defer = defer;
    }

    public CallbackEvent(InteractionData data, boolean defer) {
        this.data = data;
        this.ephemeral = false;
        this.defer = defer;
    }

    public void then(Consumer<CallbackAfterEvent<T>> reply) {
        reply.accept(new CallbackAfterEvent<>(data, ephemeral, defer));
    }
}
