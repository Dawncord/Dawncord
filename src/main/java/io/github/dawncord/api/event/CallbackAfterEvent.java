package io.github.dawncord.api.event;

import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.MessageCreateAction;
import io.github.dawncord.api.action.MessageModifyAction;
import io.github.dawncord.api.entities.guild.GuildImpl;
import io.github.dawncord.api.entities.message.Message;
import io.github.dawncord.api.entities.message.MessageImpl;
import io.github.dawncord.api.interaction.InteractionData;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.function.Consumer;

/**
 * Represents an event that occurs after a callback action has been processed.
 *
 * @param <T> The type of the callback event.
 */
public class CallbackAfterEvent<T> {
    private final InteractionData data;
    private final boolean ephemeral;
    private final boolean defer;

    /**
     * Constructs a CallbackAfterEvent with the specified interaction data, ephemeral status, and defer status.
     *
     * @param data      The interaction data associated with the callback event.
     * @param ephemeral A boolean indicating whether the response should be ephemeral (only visible to the user who triggered the interaction).
     * @param defer     A boolean indicating whether the response was deferred (not sent immediately).
     */
    public CallbackAfterEvent(InteractionData data, boolean ephemeral, boolean defer) {
        this.data = data;
        this.ephemeral = ephemeral;
        this.defer = defer;
    }

    /**
     * Retrieves the original message associated with the interaction.
     *
     * @return The original message associated with the interaction.
     */
    public Message get() {
        return new MessageImpl(
                JsonUtils.fetch(Routes.OriginalMessage(data.getInteraction().getInteractionToken())),
                new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(data.getGuildId())))
        );
    }

    /**
     * Edits the original message with the specified handler.
     *
     * @param handler The consumer function to handle the message modification.
     */
    public void edit(Consumer<T> handler) {
        if (defer) {
            ActionExecutor.deferReply((Consumer<MessageCreateAction>) handler, data, ephemeral);
        } else {
            ActionExecutor.modifyMessage((Consumer<MessageModifyAction>) handler, get(), data);
        }
    }

    /**
     * Deletes the original message associated with the interaction.
     */
    public void delete() {
        if (defer) {
            edit(null);
        }
        ApiClient.delete(Routes.OriginalMessage(data.getInteraction().getInteractionToken()));
    }
}
