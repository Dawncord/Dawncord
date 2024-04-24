package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.action.MessageCreateAction;
import org.dimas4ek.wrapper.action.MessageModifyAction;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildMember;
import org.dimas4ek.wrapper.interaction.MessageComponentInteractionData;
import org.dimas4ek.wrapper.types.ButtonStyle;
import org.dimas4ek.wrapper.utils.ActionExecutor;

import java.util.function.Consumer;

public class ButtonEvent implements MessageComponentEvent {
    private final MessageComponentInteractionData data;
    private final ButtonStyle style;
    private final String url;
    private final String label;

    public ButtonEvent(MessageComponentInteractionData interactionData, ButtonStyle style, String url, String label) {
        this.style = style;
        this.data = interactionData;
        this.url = url;
        this.label = label;
    }

    public void edit(Consumer<MessageModifyAction> handler) {
        ActionExecutor.deferEdit(handler, data, getChannel().asText().getMessageById(data.getId()));
    }

    @Override
    public Guild getGuild() {
        return data.getGuild();
    }

    @Override
    public CallbackEvent<MessageModifyAction> reply(String message, Consumer<MessageCreateAction> handler) {
        ActionExecutor.createMessage(handler, message, data.getGuildChannel().getId(), data);
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
    public GuildMember getMember() {
        return data.getGuildMember();
    }

    @Override
    public GuildChannel getChannel() {
        return data.getGuildChannel();
    }

    @Override
    public GuildChannel getChannelById(String channelId) {
        return getGuild().getChannelById(channelId);
    }

    @Override
    public GuildChannel getChannelById(long channelId) {
        return getChannelById(String.valueOf(channelId));
    }

    @Override
    public String getCustomId() {
        return data.getCustomId();
    }

    public ButtonStyle getStyle() {
        return style;
    }

    public String getUrl() {
        return url;
    }

    public String getLabel() {
        return label;
    }
}

