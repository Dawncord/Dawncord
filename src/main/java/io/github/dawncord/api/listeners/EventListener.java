package io.github.dawncord.api.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.*;
import io.github.dawncord.api.entities.activity.Activity;
import io.github.dawncord.api.entities.activity.ActivityImpl;
import io.github.dawncord.api.entities.application.Application;
import io.github.dawncord.api.entities.application.ApplicationImpl;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.channel.Invite;
import io.github.dawncord.api.entities.channel.Stage;
import io.github.dawncord.api.entities.channel.thread.Thread;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildImpl;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.entities.guild.audit.AuditLog;
import io.github.dawncord.api.entities.guild.automod.AutoModRule;
import io.github.dawncord.api.entities.guild.event.GuildScheduledEvent;
import io.github.dawncord.api.entities.guild.integration.Integration;
import io.github.dawncord.api.entities.guild.role.GuildRole;
import io.github.dawncord.api.entities.message.Message;
import io.github.dawncord.api.entities.message.MessageImpl;
import io.github.dawncord.api.entities.message.sticker.Sticker;
import io.github.dawncord.api.event.*;
import io.github.dawncord.api.types.GatewayEvent;
import io.github.dawncord.api.types.OnlineStatus;
import io.github.dawncord.api.utils.EnumUtils;
import io.github.dawncord.api.utils.EventProcessor;
import io.github.dawncord.api.utils.Events;
import io.github.dawncord.api.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Listens to events received over a WebSocket connection.
 */
public class EventListener extends WebSocketAdapter {
    private static final Logger logger = LoggerFactory.getLogger(EventListener.class);

    private final EventProcessor eventHandler = new EventProcessor();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        JsonNode json = mapper.readTree(text);
        int op = json.get("op").asInt();

        if (op == 0) {
            JsonNode data = json.get("d");
            GatewayEvent type = GatewayEvent.valueOf(json.get("t").asText());

            processEvent(data, type);
        }
    }

    private void processEvent(JsonNode data, GatewayEvent type) {
        if (Events.isReadyEvent(type)) {
            processReadyEvent(data);
        } else if (Events.isChannelEvent(type)) {
            processChannelEvent(data, type);
        } else if (Events.isGuildEvent(type)) {
            processGuildEvent(data, type);
        } else if (Events.isMessageEvent(type)) {
            processMessageEvent(data, type);
        } else if (Events.isAutoModEvent(type)) {
            processAutoModEvent(data, type);
        } else if (Events.isThreadEvent(type)) {
            processThreadEvent(data, type);
        } else if (Events.isIntegrationEvent(type)) {
            processIntegrationEvent(data, type);
        } else if (Events.isInviteEvent(type)) {
            processInviteEvent(data, type);
        } else if (Events.isStageInstanceEvent(type)) {
            processStageEvent(data, type);
        } else if (Events.isPresenceEvent(type)) {
            processPresenceEvent(data);
        } else if (Events.isTypingEvent(type)) {
            processTypingEvent(data);
        } else if (Events.isBotUpdateEvent(type)) {
            processBotUpdateEvent(data);
        } else if (Events.isVoiceEvent(type)) {
            processVoiceStateUpdateEvent(data);
        } else if (Events.isWebhookEvent(type)) {
            processWebhookUpdateEvent(data);
        }
    }

    private void processReadyEvent(JsonNode data) {
        String version = data.path("v").asText();
        User user = new UserImpl(JsonUtils.fetch(Routes.User(data.path("user").path("id").asText())));
        String sessionType = data.path("session_type").asText();
        String sessionId = data.path("session_id").asText();
        String resumeUrl = data.path("resume_gateway_url").asText();
        List<Guild> guilds = new ArrayList<>();
        data.path("guilds").forEach(node -> guilds.add(new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(node.path("id").asText())))));
        Application application = new ApplicationImpl(JsonUtils.fetch(Routes.Application()));

        ReadyEvent readyEvent = new ReadyEvent(version, user, sessionType, sessionId, resumeUrl, guilds, application);
        try {
            Method method = EventProcessor.class.getDeclaredMethod("processReadyEvent", ReadyEvent.class);
            method.setAccessible(true);
            method.invoke(eventHandler, readyEvent);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException |
                 IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void processChannelEvent(JsonNode data, GatewayEvent type) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            ChannelEvent channelEvent;
            if (type != GatewayEvent.CHANNEL_DELETE) {
                String channelId = data.path("id").asText();
                GuildChannel channel = guild.getChannelById(channelId);
                channelEvent = new ChannelEvent(guild, channel);
            } else {
                channelEvent = new ChannelEvent(guild, null);
            }
            invokeProcessEvent("processChannelEvent", type, channelEvent, ChannelEvent.class);
        }
    }

    private void processGuildEvent(JsonNode data, GatewayEvent type) {
        String guildId = data.path("guild_id").asText();
        if (guildId.isEmpty()) {
            guildId = data.path("id").asText();
        }
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            GuildDefaultEvent guildEvent = new GuildDefaultEvent(guild);
            invokeProcessEvent("processGuildEvent", type, guildEvent, GuildDefaultEvent.class);
        }
    }

    private void processGuildAuditEvent(JsonNode data) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            GuildMember member = guild.getMemberById(data.path("user_id").asText());
            AuditLog.Entry entry = guild.getAuditLog().getEntryById(data.path("id").asText());

            GuildAuditEvent guildEvent = new GuildAuditEvent(guild, member, entry);
            invokeSingleProcessEvent("processGuildAuditEvent", guildEvent, GuildAuditEvent.class);
        }
    }

    private void processGuildBanEvent(JsonNode data, GatewayEvent type) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            User user = new UserImpl(JsonUtils.fetch(Routes.User(data.path("user").path("id").asText())));

            GuildBanEvent guildEvent = new GuildBanEvent(guild, user);
            invokeProcessEvent("processGuildBanEvent", type, guildEvent, GuildBanEvent.class);
        }
    }

    private void processGuildEmojiEvent(JsonNode data) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            List<CustomEmoji> emojis = guild.getEmojis();

            GuildEmojiEvent guildEvent = new GuildEmojiEvent(guild, emojis);
            invokeSingleProcessEvent("processGuildEmojiEvent", guildEvent, GuildEmojiEvent.class);
        }
    }

    private void processGuildStickerEvent(JsonNode data) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            List<Sticker> stickers = guild.getStickers();

            GuildStickerEvent guildEvent = new GuildStickerEvent(guild, stickers);
            invokeSingleProcessEvent("processGuildStickerEvent", guildEvent, GuildStickerEvent.class);
        }
    }

    private void processGuildMemberEvent(JsonNode data, GatewayEvent type) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            GuildMember member = guild.getMemberById(data.path("user").path("id").asText());

            GuildMemberEvent guildEvent = new GuildMemberEvent(guild, member);
            invokeProcessEvent("processGuildMemberEvent", type, guildEvent, GuildMemberEvent.class);
        }
    }

    private void processGuildRoleEvent(JsonNode data, GatewayEvent type) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            GuildRole role = guild.getRoleById(data.path("role").path("id").asText());

            GuildRoleEvent guildEvent = new GuildRoleEvent(guild, role);
            invokeProcessEvent("processGuildRoleEvent", type, guildEvent, GuildRoleEvent.class);
        }
    }

    private void processGuildScheduledEvent(JsonNode data, GatewayEvent type) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            GuildScheduledEvent guildScheduledEvent = guild.getGuildEventById(data.path("id").asText());

            GuildScheduledEventEvent guildScheduledEventEvent = new GuildScheduledEventEvent(guild, guildScheduledEvent);
            invokeProcessEvent("processGuildScheduledEvent", type, guildScheduledEventEvent, GuildScheduledEventEvent.class);
        }
    }

    private void processGuildScheduledEventUserEvent(JsonNode data, GatewayEvent type) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            GuildMember member = guild.getMemberById(data.path("user_id").asText());
            GuildScheduledEvent guildScheduledEvent = guild.getGuildEventById(data.path("guild_scheduled_event_id").asText());

            GuildScheduledEventUserEvent guildScheduledEventUserEvent = new GuildScheduledEventUserEvent(guild, member, guildScheduledEvent);
            invokeProcessEvent("processGuildScheduledEventUserEvent", type, guildScheduledEventUserEvent, GuildScheduledEventUserEvent.class);
        }
    }

    private void processMessageEvent(JsonNode data, GatewayEvent type) {
        String guildId = data.path("guild_id").asText();
        String channelId = data.path("channel_id").asText();
        String messageId = data.path("id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            GuildChannel channel = guild.getChannelById(channelId);
            Message message = new MessageImpl(JsonUtils.fetch(Routes.Channel.Message.Get(channelId, messageId)), guild);

            MessageEvent messageEvent = new MessageEvent(message, channel, guild);
            invokeProcessEvent("processMessageEvent", type, messageEvent, MessageEvent.class);
        }
    }

    private void processAutoModEvent(JsonNode data, GatewayEvent type) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            AutoModRule rule = guild.getAutoModRuleById(data.path("id").asText());

            AutoModEvent autoModEvent = new AutoModEvent(guild, rule);
            invokeProcessEvent("processAutoModEvent", type, autoModEvent, AutoModEvent.class);
        }
    }

    private void processThreadEvent(JsonNode data, GatewayEvent type) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            Thread thread = guild.getThreadById(data.path("id").asText());

            ThreadEvent threadEvent = new ThreadEvent(guild, thread);
            invokeProcessEvent("processThreadEvent", type, threadEvent, ThreadEvent.class);
        }
    }

    //todo entitlement event

    private void processIntegrationEvent(JsonNode data, GatewayEvent type) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            Integration integration = guild.getIntegrationById(data.path("id").asText());

            IntegrationEvent integrationEvent = new IntegrationEvent(guild, integration);
            invokeProcessEvent("processIntegrationEvent", type, integrationEvent, IntegrationEvent.class);
        }
    }

    private void processInviteEvent(JsonNode data, GatewayEvent type) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            Invite invite = guild.getInvite(data.path("code").asText());

            InviteEvent inviteEvent = new InviteEvent(guild, invite);
            invokeProcessEvent("processInviteEvent", type, inviteEvent, InviteEvent.class);
        }
    }

    private void processStageEvent(JsonNode data, GatewayEvent type) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            Stage stage = guild.getStageByChannelId(data.path("channel_id").asText());

            StageEvent stageEvent = new StageEvent(guild, stage);
            invokeProcessEvent("processStageEvent", type, stageEvent, StageEvent.class);
        }
    }

    private void processPresenceEvent(JsonNode data) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            GuildMember member = guild.getMemberById(data.path("user").path("id").asText());
            OnlineStatus status = EnumUtils.getEnumObject(data, "status", OnlineStatus.class);
            ClientStatus clientStatus = new ClientStatus(data.path("client_status"));
            List<Activity> activities = new ArrayList<>();
            data.path("activities").forEach(node -> activities.add(new ActivityImpl(node)));

            PresenceEvent presenceEvent = new PresenceEvent(guild, member, status, clientStatus, activities);
            invokeSingleProcessEvent("processPresenceEvent", presenceEvent, PresenceEvent.class);
        }
    }

    private void processTypingEvent(JsonNode data) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            GuildChannel channel = guild.getChannelById(data.path("channel_id").asText());
            GuildMember member = guild.getMemberById(data.path("user_id").asText());

            TypingEvent typingEvent = new TypingEvent(guild, channel, member);
            invokeSingleProcessEvent("processTypingEvent", typingEvent, TypingEvent.class);
        }
    }

    private void processBotUpdateEvent(JsonNode data) {
        String userId = data.path("id").asText();
        User user = new UserImpl(JsonUtils.fetch(Routes.User(userId)));
        BotUpdateEvent botUpdateEvent = new BotUpdateEvent(user);
        try {
            Method method = EventProcessor.class.getDeclaredMethod("processBotUpdateEvent", BotUpdateEvent.class);
            method.setAccessible(true);
            method.invoke(eventHandler, botUpdateEvent);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException |
                 IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void processVoiceStateUpdateEvent(JsonNode data) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            VoiceState voiceState = new VoiceState(data, guild);

            VoiceStateUpdateEvent voiceEvent = new VoiceStateUpdateEvent(guild, voiceState);
            invokeSingleProcessEvent("processVoiceStateUpdateEvent", voiceEvent, VoiceStateUpdateEvent.class);
        }
    }

    private void processWebhookUpdateEvent(JsonNode data) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            for (Webhook webhook : guild.getWebhooks()) {
                if (webhook.getChannel().getId().equals(data.path("channel_id").asText())) {
                    WebhookUpdateEvent webhookUpdateEvent = new WebhookUpdateEvent(guild, webhook);
                    invokeSingleProcessEvent("processWebhookUpdateEvent", webhookUpdateEvent, WebhookUpdateEvent.class);
                    return;
                }
            }
        }
    }

    private Guild getGuildById(String guildId) {
        if (guildId.isEmpty()) {
            return null;
        }
        return new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(guildId)));
    }

    private void invokeProcessEvent(String methodName, GatewayEvent type, Event event, Class<? extends Event> clazz) {
        try {
            Method method = EventProcessor.class.getDeclaredMethod(methodName, GatewayEvent.class, clazz);
            method.setAccessible(true);
            method.invoke(eventHandler, type, event);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException |
                 IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void invokeSingleProcessEvent(String methodName, Event event, Class<? extends Event> clazz) {
        try {
            Method method = EventProcessor.class.getDeclaredMethod(methodName, clazz);
            method.setAccessible(true);
            method.invoke(eventHandler, event);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException |
                 IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
