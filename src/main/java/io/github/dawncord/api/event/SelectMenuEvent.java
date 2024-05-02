package io.github.dawncord.api.event;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.MessageCreateAction;
import io.github.dawncord.api.action.MessageModifyAction;
import io.github.dawncord.api.action.ModalCreateAction;
import io.github.dawncord.api.entities.Mentionable;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildImpl;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.entities.guild.role.GuildRole;
import io.github.dawncord.api.entities.message.component.SelectMenuData;
import io.github.dawncord.api.entities.message.modal.Modal;
import io.github.dawncord.api.interaction.InteractionData;
import io.github.dawncord.api.interaction.MessageComponentInteractionData;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents an event related to a select menu interaction in a message.
 */
public class SelectMenuEvent implements MessageComponentEvent {
    private static MessageComponentInteractionData data;
    private final SelectMenuData selectMenuData;
    private final JsonNode resolved;
    private final List<String> values;
    private static Guild guild;
    private static GuildChannel channel;
    private static GuildMember member;

    /**
     * Constructs a SelectMenuEvent with the specified interaction data, select menu data, resolved data, and selected values.
     *
     * @param interactionData The interaction data associated with the select menu event.
     * @param selectMenuData  The data associated with the select menu component.
     * @param resolved        The resolved data of the interaction.
     * @param values          The selected values of the select menu.
     */
    public SelectMenuEvent(MessageComponentInteractionData interactionData, SelectMenuData selectMenuData, JsonNode resolved, List<String> values) {
        this.selectMenuData = selectMenuData;
        this.resolved = resolved;
        this.values = values;
        data = interactionData;
        guild = new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(data.getGuildId())));
        channel = guild.getChannelById(data.getChannelId());
        member = guild.getMemberById(data.getMemberId());

        Event.getLogger().debug("Select menu event [{}] -> {} in [{}:{}]:[{}:{}] from [{}:{}}",
                data.getCustomId(),
                Routes.Reply("{id}", "{token}"),
                guild.getId(), guild.getName(),
                channel.getId(), channel.getName(),
                member.getUser().getId(), member.getUser().getUsername());
    }

    /**
     * Retrieves the interaction data associated with the select menu event.
     *
     * @return The interaction data.
     */
    public static InteractionData getData() {
        return data;
    }

    /**
     * Updates the guild, channel, and member data associated with the event.
     */
    public static void UpdateData() {
        guild = new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(data.getGuildId())));
        channel = guild.getChannelById(data.getChannelId());
        member = guild.getMemberById(data.getMemberId());
    }

    /**
     * Retrieves the data associated with the select menu component.
     *
     * @return The select menu data.
     */
    public SelectMenuData getSelectMenu() {
        return selectMenuData;
    }

    /**
     * Retrieves the resolved data of the interaction.
     *
     * @return The resolved data.
     */
    public Resolved getValues() {
        return new Resolved(resolved, values, getGuild());
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    @Override
    public CallbackEvent<MessageModifyAction> edit(Consumer<MessageModifyAction> handler) {
        ActionExecutor.deferEdit(handler, data, getChannel().asText().getMessageById(data.getId()));
        return new CallbackEvent<>(data, false, true);
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

    /**
     * Represents resolved data of an interaction, providing methods to convert resolved data into specific types.
     */
    public static class Resolved {
        private final JsonNode resolved;
        private final List<String> values;
        private final Guild guild;

        /**
         * Constructs a Resolved object with the specified resolved data, values, and guild.
         *
         * @param resolved The resolved data JSON node.
         * @param values   The selected values.
         * @param guild    The guild associated with the resolved data.
         */
        public Resolved(JsonNode resolved, List<String> values, Guild guild) {
            this.resolved = resolved;
            this.values = values;
            this.guild = guild;
        }

        /**
         * Converts the resolved data into a list of guild members.
         *
         * @return The list of guild members.
         */
        public List<GuildMember> asMembers() {
            List<GuildMember> guildMembers = new ArrayList<>();
            if (resolved.has("members")) {
                resolved.get("members").fieldNames().forEachRemaining(id -> guildMembers.add(guild.getMemberById(id)));
            }
            return guildMembers;
        }

        /**
         * Converts the resolved data into a list of guild channels.
         *
         * @return The list of guild channels.
         */
        public List<GuildChannel> asChannels() {
            List<GuildChannel> guildChannels = new ArrayList<>();
            if (resolved.has("members")) {
                resolved.get("channels").fieldNames().forEachRemaining(id -> guildChannels.add(guild.getChannelById(id)));
            }
            return guildChannels;
        }

        /**
         * Converts the resolved data into a list of guild roles.
         *
         * @return The list of guild roles.
         */
        public List<GuildRole> asRoles() {
            List<GuildRole> guildRoles = new ArrayList<>();
            if (resolved.has("members")) {
                resolved.get("roles").fieldNames().forEachRemaining(id -> guildRoles.add(guild.getRoleById(id)));
            }
            return guildRoles;
        }

        /**
         * Converts the resolved data into a list of mentionable objects.
         *
         * @return The list of mentionable objects.
         */
        public List<Mentionable> asMentions() {
            List<Mentionable> mentionables = new ArrayList<>();
            values.forEach(value -> mentionables.add(new Mentionable(value, resolved)));
            return mentionables;
        }

        /**
         * Retrieves the resolved data as a list of strings.
         *
         * @return The list of strings.
         */
        public List<String> asString() {
            return values;
        }
    }
}
