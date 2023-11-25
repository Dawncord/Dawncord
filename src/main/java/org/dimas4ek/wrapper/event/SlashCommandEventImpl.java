package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.action.ApplicationModifyAction;
import org.dimas4ek.wrapper.action.MessageCreateAction;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.Webhook;
import org.dimas4ek.wrapper.entities.WebhookImpl;
import org.dimas4ek.wrapper.entities.application.Application;
import org.dimas4ek.wrapper.entities.application.ApplicationImpl;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.GuildChannelImpl;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildMember;
import org.dimas4ek.wrapper.interaction.InteractionData;
import org.dimas4ek.wrapper.slashcommand.option.OptionData;
import org.dimas4ek.wrapper.utils.ActionExecutor;
import org.dimas4ek.wrapper.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SlashCommandEventImpl implements SlashCommandEvent {
    private final InteractionData data;

    public SlashCommandEventImpl(InteractionData data) {
        this.data = data;
    }

    @Override
    public String getCommandName() {
        return data.getSlashCommand().getName();
    }

    @Override
    public void reply(String message, Consumer<MessageCreateAction> handler) {
        ActionExecutor.createMessage(handler, message, data.getResponse().getGuildChannel().getId(), data);
    }

    @Override
    public void reply(String message) {
        reply(message, null);
    }

    @Override
    public void reply(Consumer<MessageCreateAction> handler) {
        reply(null, handler);
    }

    @Override
    public GuildMember getMember() {
        return data.getResponse().getGuildMember();
    }

    @Override
    public User getUser() {
        return getMember().getUser();
    }

    @Override
    public Guild getGuild() {
        return data.getResponse().getGuild();
    }

    @Override
    public GuildChannel getChannel() {
        return data.getResponse().getGuildChannel();
    }

    @Override
    public GuildChannel getChannelById(String channelId) {
        return new GuildChannelImpl(JsonUtils.fetchEntity("/channels/" + channelId));
    }

    @Override
    public GuildChannel getChannelById(long channelId) {
        return getChannelById(String.valueOf(channelId));
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> optionDataList = new ArrayList<>();
        for (Map<String, Object> map : data.getOptions()) {
            OptionData optionData = new OptionData(map);
            optionDataList.add(optionData);
        }

        return optionDataList;
    }

    @Override
    public OptionData getOption(String name) {
        return getOptions().stream().filter(option -> option.getData().get("name").equals(name)).findAny().orElse(null);
    }

    @Override
    public Application getApplication() {
        return new ApplicationImpl(JsonUtils.fetchEntity("/applications/@me"));
    }

    @Override
    public void editApplication(Consumer<ApplicationModifyAction> handler) {
        ActionExecutor.modifyApplication(handler);
    }

    @Override
    public Webhook getWebhookById(String webhookId) {
        return new WebhookImpl(JsonUtils.fetchEntity("/webhooks/" + webhookId));
    }

    @Override
    public Webhook getWebhookById(long webhookId) {
        return getWebhookById(String.valueOf(webhookId));
    }

    @Override
    public Webhook getWebhookByToken(String webhookId, String webhookToken) {
        return new WebhookImpl(JsonUtils.fetchEntity("/webhooks/" + webhookId + "/" + webhookToken));
    }
}
