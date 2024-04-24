package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.action.MessageCreateAction;
import org.dimas4ek.wrapper.action.MessageModifyAction;
import org.dimas4ek.wrapper.command.option.OptionData;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildMember;
import org.dimas4ek.wrapper.interaction.SlashCommandInteractionData;
import org.dimas4ek.wrapper.utils.ActionExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SlashCommandEvent implements ReplyEvent {
    private final SlashCommandInteractionData data;

    public SlashCommandEvent(SlashCommandInteractionData data) {
        this.data = data;
    }

    @Override
    public Guild getGuild() {
        return data.getGuild();
    }

    public String getCommandName() {
        return data.getSlashCommand().getName();
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

    public List<OptionData> getOptions() {
        List<OptionData> optionDataList = new ArrayList<>();
        for (Map<String, Object> map : data.getOptions()) {
            OptionData optionData = new OptionData(map, getGuild());
            optionDataList.add(optionData);
        }

        return optionDataList;
    }

    public OptionData getOption(String name) {
        return getOptions().stream().filter(option -> option.getData().get("name").equals(name)).findAny().orElse(null);
    }
}
