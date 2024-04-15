package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.action.MessageCreateAction;
import org.dimas4ek.wrapper.command.option.OptionData;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildMember;
import org.dimas4ek.wrapper.interaction.InteractionData;
import org.dimas4ek.wrapper.utils.ActionExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SlashCommandEvent implements Event {
    private final InteractionData data;

    public SlashCommandEvent(InteractionData data) {
        this.data = data;
    }

    @Override
    public Guild getGuild() {
        return data.getGuild();
    }

    public String getCommandName() {
        return data.getSlashCommand().getName();
    }

    public void reply(String message, Consumer<MessageCreateAction> handler) {
        ActionExecutor.createMessage(handler, message, data.getGuildChannel().getId(), data);
    }

    public void reply(String message) {
        reply(message, null);
    }

    public void reply(Consumer<MessageCreateAction> handler) {
        reply(null, handler);
    }

    public CallbackEvent deferReply() {
        return deferReply(false);
    }

    public CallbackEvent deferReply(boolean ephemeral) {
        return new CallbackEvent(data, ephemeral);
    }

    public GuildMember getMember() {
        return data.getGuildMember();
    }

    public User getUser() {
        return getMember().getUser();
    }

    public GuildChannel getChannel() {
        return data.getGuildChannel();
    }

    public GuildChannel getChannelById(String channelId) {
        return getGuild().getChannelById(channelId);
    }

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
