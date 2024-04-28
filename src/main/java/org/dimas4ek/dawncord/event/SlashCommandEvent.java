package org.dimas4ek.dawncord.event;

import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.action.MessageCreateAction;
import org.dimas4ek.dawncord.action.MessageModifyAction;
import org.dimas4ek.dawncord.action.ModalCreateAction;
import org.dimas4ek.dawncord.command.option.OptionData;
import org.dimas4ek.dawncord.entities.channel.GuildChannel;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.entities.guild.GuildImpl;
import org.dimas4ek.dawncord.entities.guild.GuildMember;
import org.dimas4ek.dawncord.interaction.InteractionData;
import org.dimas4ek.dawncord.interaction.SlashCommandInteractionData;
import org.dimas4ek.dawncord.utils.ActionExecutor;
import org.dimas4ek.dawncord.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SlashCommandEvent implements ReplyEvent {
    private static SlashCommandInteractionData data;
    private static Guild guild;
    private static GuildChannel channel;
    private static GuildMember member;

    public SlashCommandEvent(SlashCommandInteractionData interactionData) {
        data = interactionData;
        guild = new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(data.getGuildId())));
        channel = guild.getChannelById(data.getChannelId());
        member = guild.getMemberById(data.getMemberId());
    }

    public static InteractionData getData() {
        return data;
    }

    public static void UpdateData() {
        guild = new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(data.getGuildId())));
        channel = guild.getChannelById(data.getChannelId());
        member = guild.getMemberById(data.getMemberId());
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    public String getCommandName() {
        return data.getSlashCommand().getName();
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
