package io.github.dawncord.api.event;

import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.MessageCreateAction;
import io.github.dawncord.api.action.MessageModifyAction;
import io.github.dawncord.api.action.ModalCreateAction;
import io.github.dawncord.api.command.option.OptionData;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildImpl;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.entities.message.modal.Modal;
import io.github.dawncord.api.interaction.InteractionData;
import io.github.dawncord.api.interaction.SlashCommandInteractionData;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.JsonUtils;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Represents an event triggered by a slash command interaction.
 */
public class SlashCommandEvent implements ReplyEvent {
    private static SlashCommandInteractionData data;
    private static Guild guild;
    private static GuildChannel channel;
    private static GuildMember member;

    /**
     * Constructs a SlashCommandEvent with the specified interaction data.
     *
     * @param interactionData The interaction data of the slash command.
     */
    public SlashCommandEvent(SlashCommandInteractionData interactionData) {
        data = interactionData;
        guild = new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(data.getGuildId())));
        channel = guild.getChannelById(data.getChannelId());
        member = guild.getMemberById(data.getMemberId());

        Event.getLogger().debug("Slash command event [{}] -> {} in [{}:{}]:[{}:{}] from [{}:{}}",
                data.getSlashCommand().getName(),
                Routes.Reply("{id}", "{token}"),
                guild.getId(), guild.getName(),
                channel.getId(), channel.getName(),
                member.getUser().getId(), member.getUser().getUsername());
    }

    /**
     * Retrieves the interaction data associated with this slash command event.
     *
     * @return The interaction data.
     */
    public static InteractionData getData() {
        return data;
    }

    /**
     * Updates the guild, channel, and member associated with this event.
     */
    public static void UpdateData() {
        guild = new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(data.getGuildId())));
        channel = guild.getChannelById(data.getChannelId());
        member = guild.getMemberById(data.getMemberId());
    }

    /**
     * Retrieves the name of the slash command triggered in this event.
     *
     * @return The name of the slash command.
     */
    public String getCommandName() {
        return data.getSlashCommand().getName();
    }

    /**
     * Retrieves the full name of the slash command triggered in this event.
     *
     * @return The full name of the slash command.
     */
    public String getFullCommandName() {
        String name = getCommandName();
        if (getSubCommand() != null) {
            name = name + " " + getSubCommand();
        }
        if (getSubCommandGroup() != null) {
            name = name + " " + getSubCommandGroup();
        }
        return name;
    }

    /**
     * Retrieves the sub-command associated with the slash command triggered in this event.
     *
     * @return The sub-command associated with the slash command.
     */
    @Nullable
    public String getSubCommand() {
        return data.getSlashCommand().getSubCommand();
    }

    /**
     * Retrieves the sub-command group associated with the slash command triggered in this event.
     *
     * @return The sub-command group associated with the slash command.
     */
    @Nullable
    public String getSubCommandGroup() {
        return data.getSlashCommand().getSubCommandGroup();
    }

    /**
     * Retrieves the list of options provided with the slash command.
     *
     * @return The list of options.
     */
    public List<OptionData> getOptions() {
        List<OptionData> optionDataList = new ArrayList<>();
        for (Map<String, Object> map : data.getOptions()) {
            OptionData optionData = new OptionData(map, getGuild());
            optionDataList.add(optionData);
        }

        return optionDataList;
    }

    /**
     * Retrieves the option with the specified name.
     *
     * @param name The name of the option to retrieve.
     * @return The option with the specified name, or null if not found.
     */
    @Nullable
    public OptionData getOption(String name) {
        return getOptions().stream().filter(option -> option.getData().get("name").equals(name)).findAny().orElse(null);
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
