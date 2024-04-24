package org.dimas4ek.wrapper.event;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.action.MessageCreateAction;
import org.dimas4ek.wrapper.action.MessageModifyAction;
import org.dimas4ek.wrapper.entities.Mentionable;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildMember;
import org.dimas4ek.wrapper.entities.guild.role.GuildRole;
import org.dimas4ek.wrapper.entities.message.component.SelectMenuData;
import org.dimas4ek.wrapper.interaction.MessageComponentInteractionData;
import org.dimas4ek.wrapper.utils.ActionExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SelectMenuEvent implements MessageComponentEvent {
    private final MessageComponentInteractionData data;
    private final SelectMenuData selectMenuData;
    private final JsonNode resolved;
    private final List<String> values;

    public SelectMenuEvent(MessageComponentInteractionData data, SelectMenuData selectMenuData, JsonNode resolved, List<String> values) {
        this.data = data;
        this.selectMenuData = selectMenuData;
        this.resolved = resolved;
        this.values = values;
    }

    @Override
    public Guild getGuild() {
        return data.getGuild();
    }

    @Override
    public String getCustomId() {
        return data.getCustomId();
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
