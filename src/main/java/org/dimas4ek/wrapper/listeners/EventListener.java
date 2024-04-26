package org.dimas4ek.wrapper.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import org.dimas4ek.wrapper.Dawncord;
import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.entities.*;
import org.dimas4ek.wrapper.entities.activity.Activity;
import org.dimas4ek.wrapper.entities.activity.ActivityImpl;
import org.dimas4ek.wrapper.entities.application.Application;
import org.dimas4ek.wrapper.entities.application.ApplicationImpl;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.Invite;
import org.dimas4ek.wrapper.entities.channel.Stage;
import org.dimas4ek.wrapper.entities.channel.thread.Thread;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildImpl;
import org.dimas4ek.wrapper.entities.guild.GuildMember;
import org.dimas4ek.wrapper.entities.guild.audit.AuditLog;
import org.dimas4ek.wrapper.entities.guild.automod.AutoModRule;
import org.dimas4ek.wrapper.entities.guild.event.GuildScheduledEvent;
import org.dimas4ek.wrapper.entities.guild.integration.Integration;
import org.dimas4ek.wrapper.entities.guild.role.GuildRole;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.entities.message.MessageImpl;
import org.dimas4ek.wrapper.entities.message.sticker.Sticker;
import org.dimas4ek.wrapper.event.*;
import org.dimas4ek.wrapper.types.GatewayEvent;
import org.dimas4ek.wrapper.types.OnlineStatus;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.EventHandler;
import org.dimas4ek.wrapper.utils.Events;
import org.dimas4ek.wrapper.utils.JsonUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventListener extends WebSocketAdapter {
    private final EventHandler eventHandler = new EventHandler();
    //private final Dawncord dawncord;
    private final ObjectMapper mapper = new ObjectMapper();

    public EventListener(Dawncord dawncord) {
        //this.dawncord = dawncord;
    }

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

    //todo resumed reconnect event
    //todo invalid session event

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
            Method method = EventHandler.class.getDeclaredMethod("processReadyEvent", ReadyEvent.class);
            //Method method = Dawncord.class.getDeclaredMethod("processReadyEvent", ReadyEvent.class);
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
            String channelId = data.path("id").asText();
            GuildChannel channel = guild.getChannelById(channelId);
            if (channel != null) {
                ChannelEvent channelEvent = new ChannelEvent(guild, channel);
                invokeProcessEvent("processChannelEvent", type, channelEvent, ChannelEvent.class);
            }
        }
    }

    //todo fix guild events
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

            //todo guild member remove event
            //todo guild members chunk

            GuildMemberEvent guildEvent = new GuildMemberEvent(guild, member);
            invokeProcessEvent("processGuildMemberEvent", type, guildEvent, GuildMemberEvent.class);
        }
    }

    private void processGuildRoleEvent(JsonNode data, GatewayEvent type) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            GuildRole role = guild.getRoleById(data.path("role").path("id").asText());

            //todo guild remove role event

            GuildRoleEvent guildEvent = new GuildRoleEvent(guild, role);
            invokeProcessEvent("processGuildRoleEvent", type, guildEvent, GuildRoleEvent.class);
        }
    }

    private void processGuildScheduledEvent(JsonNode data, GatewayEvent type) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            GuildScheduledEvent guildScheduledEvent = guild.getGuildEventById(data.path("id").asText());

            //todo guild scheduled event remove

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

            //todo guild scheduled event user remove

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
            Method method = EventHandler.class.getDeclaredMethod("processBotUpdateEvent", BotUpdateEvent.class);
            //Method method = Dawncord.class.getDeclaredMethod("processBotUpdateEvent", BotUpdateEvent.class);
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

    /*private void processButtonEvent(JsonNode data) {
        String guildId = data.path("guild_id").asText();
        Guild guild = getGuildById(guildId);
        if (guild != null) {
            String customId = data.path("custom_id").asText();
            String componentType = data.path("component_type").asText();

            ButtonEvent buttonEvent = new ButtonEvent(style, customId, label, guild, guildChannel);
            invokeSingleProcessEvent("processButtonEvent", buttonEvent, ButtonEvent.class);
        }
    }*/

    private Guild getGuildById(String guildId) {
        if (guildId.isEmpty()) {
            return null;
        }
        return new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(guildId)));
    }

    private void invokeProcessEvent(String methodName, GatewayEvent type, Event event, Class<? extends Event> clazz) {
        try {
            Method method = EventHandler.class.getDeclaredMethod(methodName, GatewayEvent.class, clazz);
            //Method method = Dawncord.class.getDeclaredMethod(methodName, GatewayEvent.class, clazz);
            method.setAccessible(true);
            method.invoke(eventHandler, type, event);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException |
                 IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void invokeSingleProcessEvent(String methodName, Event event, Class<? extends Event> clazz) {
        try {
            Method method = EventHandler.class.getDeclaredMethod(methodName, clazz);
            //Method method = Dawncord.class.getDeclaredMethod(methodName, clazz);
            method.setAccessible(true);
            method.invoke(eventHandler, event);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException |
                 IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) {
        System.out.println("Closed: " + serverCloseFrame.getCloseReason());
    }

    @Override
    public void onError(WebSocket websocket, WebSocketException cause) {
        System.err.println("Error: " + cause.getMessage());
    }
}
