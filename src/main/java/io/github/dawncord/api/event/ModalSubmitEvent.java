package io.github.dawncord.api.event;

import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.ModalCreateAction;
import io.github.dawncord.api.action.message.MessageCreateAction;
import io.github.dawncord.api.action.message.MessageModifyAction;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.entities.message.modal.Modal;
import io.github.dawncord.api.entities.message.modal.ModalData;
import io.github.dawncord.api.interaction.InteractionData;
import io.github.dawncord.api.interaction.ModalInteractionData;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.function.Consumer;

/**
 * Represents an event triggered when a modal is submitted.
 */
public class ModalSubmitEvent implements ReplyEvent {
    private static ModalInteractionData data;
    private static Guild guild;
    private static GuildChannel channel;
    private static GuildMember member;
    private final ModalData modalData;

    /**
     * Constructs a ModalSubmitEvent with the specified modal interaction data and modal data.
     *
     * @param interactionData The interaction data associated with the modal submission.
     * @param modalData       The data submitted by the modal.
     */
    public ModalSubmitEvent(ModalInteractionData interactionData, ModalData modalData) {
        this.modalData = modalData;
        data = interactionData;
        guild = new Guild(JsonUtils.fetch(Routes.Guild.Get(data.guildId())));
        channel = guild.getChannelById(data.channelId());
        member = guild.getMemberById(data.memberId());
    }

    /**
     * Retrieves the interaction data associated with the modal submission.
     *
     * @return The interaction data.
     */
    public static InteractionData getData() {
        return data;
    }

    /**
     * Updates the guild, channel, and member data associated with the modal submission.
     */
    public static void UpdateData() {
        guild = new Guild(JsonUtils.fetch(Routes.Guild.Get(data.guildId())));
        channel = guild.getChannelById(data.channelId());
        member = guild.getMemberById(data.memberId());
    }

    /**
     * Retrieves the data submitted by the modal.
     *
     * @return The modal data.
     */
    public ModalData getModal() {
        return modalData;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    @Override
    public CallbackEvent<MessageModifyAction> reply(String message, Consumer<MessageCreateAction> handler) {
        ActionExecutor.createMessage(handler, message, channel.getId(), data);
        return new CallbackEvent<>(data, false);
    }

    @Override
    public CallbackEvent<MessageModifyAction> reply(String message) {
        return reply(message, null);
    }

    @Override
    public CallbackEvent<MessageModifyAction> reply(Consumer<MessageCreateAction> handler) {
        return reply(null, handler);
    }

    @Override
    public CallbackEvent<MessageCreateAction> deferReply(boolean ephemeral) {
        ActionExecutor.deferReply(null, data, ephemeral);
        return new CallbackEvent<>(data, ephemeral, true);
    }

    @Override
    public CallbackEvent<MessageCreateAction> deferReply() {
        return deferReply(false);
    }

    @Override
    public CallbackEvent<MessageModifyAction> replyModal(Consumer<ModalCreateAction> handler) {
        ActionExecutor.replyModal(handler, data);
        return new CallbackEvent<>(data, false, false);
    }

    @Override
    public CallbackEvent<MessageModifyAction> replyModal(Modal modal) {
        ActionExecutor.replyModal(modal, data);
        return new CallbackEvent<>(data, false, false);
    }

    @Override
    public GuildMember getMember() {
        return member;
    }

    @Override
    public GuildChannel getChannel() {
        return channel;
    }

    @Override
    public GuildChannel getChannelById(String channelId) {
        return getGuild().getChannelById(channelId);
    }

    @Override
    public GuildChannel getChannelById(long channelId) {
        return getChannelById(String.valueOf(channelId));
    }
}
