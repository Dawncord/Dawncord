package io.github.dawncord.api.event;

import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.MessageCreateAction;
import io.github.dawncord.api.action.MessageModifyAction;
import io.github.dawncord.api.action.ModalCreateAction;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildImpl;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.entities.message.component.ButtonData;
import io.github.dawncord.api.entities.message.modal.Modal;
import io.github.dawncord.api.interaction.InteractionData;
import io.github.dawncord.api.interaction.MessageComponentInteractionData;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.function.Consumer;

/**
 * Represents an event triggered by interaction with a button component in a message.
 */
public class ButtonEvent implements MessageComponentEvent {
    private static MessageComponentInteractionData data;
    private final ButtonData buttonData;
    private static Guild guild;
    private static GuildChannel channel;
    private static GuildMember member;

    /**
     * Constructs a ButtonEvent with the specified interaction data and button data.
     *
     * @param interactionData The interaction data associated with the button event.
     * @param buttonData      The data of the button that triggered the event.
     */
    public ButtonEvent(MessageComponentInteractionData interactionData, ButtonData buttonData) {
        this.buttonData = buttonData;
        data = interactionData;
        guild = new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(data.getGuildId())));
        channel = guild.getChannelById(data.getChannelId());
        member = guild.getMemberById(data.getMemberId());

        Event.getLogger().debug("Button event [{}] -> {} in [{}:{}]:[{}:{}] from [{}:{}}",
                data.getCustomId(),
                Routes.Reply("{id}", "{token}"),
                guild.getId(), guild.getName(),
                channel.getId(), channel.getName(),
                member.getUser().getId(), member.getUser().getUsername());
    }

    /**
     * Retrieves the interaction data associated with the button event.
     *
     * @return The interaction data associated with the button event.
     */
    public static InteractionData getData() {
        return data;
    }

    /**
     * Updates the guild, channel, and member data associated with the button event.
     */
    public static void UpdateData() {
        guild = new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(data.getGuildId())));
        channel = guild.getChannelById(data.getChannelId());
        member = guild.getMemberById(data.getMemberId());
    }

    /**
     * Retrieves the data of the button that triggered the event.
     *
     * @return The data of the button that triggered the event.
     */
    public ButtonData getButton() {
        return buttonData;
    }

    @Override
    public CallbackEvent<MessageModifyAction> edit(Consumer<MessageModifyAction> handler) {
        ActionExecutor.deferEdit(handler, data, getChannel().asText().getMessageById(data.getId()));
        return new CallbackEvent<>(data, false, true);
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

