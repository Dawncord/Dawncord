package org.dimas4ek.dawncord.event;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.action.MessageCreateAction;
import org.dimas4ek.dawncord.action.MessageModifyAction;
import org.dimas4ek.dawncord.entities.Mentionable;
import org.dimas4ek.dawncord.entities.channel.GuildChannel;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.entities.guild.GuildImpl;
import org.dimas4ek.dawncord.entities.guild.GuildMember;
import org.dimas4ek.dawncord.entities.guild.role.GuildRole;
import org.dimas4ek.dawncord.entities.message.component.SelectMenuData;
import org.dimas4ek.dawncord.interaction.InteractionData;
import org.dimas4ek.dawncord.interaction.MessageComponentInteractionData;
import org.dimas4ek.dawncord.utils.ActionExecutor;
import org.dimas4ek.dawncord.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SelectMenuEvent implements MessageComponentEvent {
    private static MessageComponentInteractionData data;
    private final SelectMenuData selectMenuData;
    private final JsonNode resolved;
    private final List<String> values;
    private static Guild guild;
    private static GuildChannel channel;
    private static GuildMember member;

    public SelectMenuEvent(MessageComponentInteractionData interactionData, SelectMenuData selectMenuData, JsonNode resolved, List<String> values) {
        this.selectMenuData = selectMenuData;
        this.resolved = resolved;
        this.values = values;
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

    public SelectMenuData getSelectMenu() {
        return selectMenuData;
    }

    public Resolved getValues() {
        return new Resolved(resolved, values, getGuild());
    }

    public static class Resolved {
        private final JsonNode resolved;
        private final List<String> values;
        private final Guild guild;

        public Resolved(JsonNode resolved, List<String> values, Guild guild) {
            this.resolved = resolved;
            this.values = values;
            this.guild = guild;
        }

        public List<GuildMember> asMembers() {
            List<GuildMember> guildMembers = new ArrayList<>();
            if (resolved.has("members")) {
                resolved.get("members").fieldNames().forEachRemaining(id -> guildMembers.add(guild.getMemberById(id)));
            }
            return guildMembers;
        }

        public List<GuildChannel> asChannels() {
            List<GuildChannel> guildMembers = new ArrayList<>();
            if (resolved.has("members")) {
                resolved.get("channels").fieldNames().forEachRemaining(id -> guildMembers.add(guild.getChannelById(id)));
            }
            return guildMembers;
        }

        public List<GuildRole> asRoles() {
            List<GuildRole> guildMembers = new ArrayList<>();
            if (resolved.has("members")) {
                resolved.get("roles").fieldNames().forEachRemaining(id -> guildMembers.add(guild.getRoleById(id)));
            }
            return guildMembers;
        }

        public List<Mentionable> asMentions() {
            List<Mentionable> mentionables = new ArrayList<>();
            values.forEach(value -> mentionables.add(new Mentionable(value, resolved)));
            return mentionables;
        }

        public List<String> asString() {
            return values;
        }
    }
}
