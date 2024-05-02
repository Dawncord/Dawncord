package io.github.dawncord.api.event;

import io.github.dawncord.api.interaction.InteractionData;

import java.util.function.Consumer;

/**
 * Represents an event triggered by a callback action.
 *
 * @param <T> The type of the callback event.
 */
public class CallbackEvent<T> {
    private final InteractionData data;
    private final boolean ephemeral;
    private final boolean defer;

    /**
     * Constructs a CallbackEvent with the specified interaction data, ephemeral status, and defer status.
     *
     * @param data      The interaction data associated with the callback event.
     * @param ephemeral A boolean indicating whether the response should be ephemeral (only visible to the user who triggered the interaction).
     * @param defer     A boolean indicating whether to defer the response (do not send a reply immediately).
     */
    public CallbackEvent(InteractionData data, boolean ephemeral, boolean defer) {
        this.data = data;
        this.ephemeral = ephemeral;
        this.defer = defer;
    }

    /**
     * Constructs a CallbackEvent with the specified interaction data and defer status.
     * The response is not ephemeral.
     *
     * @param data  The interaction data associated with the callback event.
     * @param defer A boolean indicating whether to defer the response (do not send a reply immediately).
     */
    public CallbackEvent(InteractionData data, boolean defer) {
        this.data = data;
        this.ephemeral = false;
        this.defer = defer;
    }

    /**
     * Executes the specified consumer function after the callback event.
     *
     * @param reply The consumer function to execute after the callback event.
     */
    public void then(Consumer<CallbackAfterEvent<T>> reply) {
        reply.accept(new CallbackAfterEvent<>(data, ephemeral, defer));
    }
}
