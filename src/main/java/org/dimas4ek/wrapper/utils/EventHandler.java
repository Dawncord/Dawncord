package org.dimas4ek.wrapper.utils;

import org.dimas4ek.wrapper.event.*;
import org.dimas4ek.wrapper.event.handler.*;
import org.dimas4ek.wrapper.types.GatewayEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class EventHandler {
    public static final Map<String, Consumer<SlashCommandEvent>> slashCommandEventHandlers = new HashMap<>();
    public static Consumer<SlashCommandEvent> defaultSlashCommandEventHandler;
    public static final Map<String, Consumer<ButtonEvent>> buttonEventHandlers = new HashMap<>();
    public static Consumer<ButtonEvent> defaultButtonComponentEventHandler;
    public static final Map<String, Consumer<SelectMenuEvent>> selectMenuEventHandlers = new HashMap<>();
    public static Consumer<SelectMenuEvent> defaultSelectMenuEventHandler;

    public static final AutoModEventHandler autoModEventHandler = new AutoModEventHandler();
    public static final MessageEventHandler messageEventHandler = new MessageEventHandler();
    public static final GuildEventHandler guildEventHandler = new GuildEventHandler();
    public static final ChannelEventHandler channelEventHandler = new ChannelEventHandler();
    public static final ThreadEventHandler threadEventHandler = new ThreadEventHandler();
    public static final IntegrationEventHandler integrationEventHandler = new IntegrationEventHandler();
    public static final InviteEventHandler inviteEventHandler = new InviteEventHandler();
    public static final StageEventHandler stageEventHandler = new StageEventHandler();

    public static Consumer<ReadyEvent> readyEventHandler;
    public static Consumer<PresenceEvent> presenceEventHandler;
    public static Consumer<TypingEvent> typingEventHandler;
    public static Consumer<BotUpdateEvent> botUpdateEventHandler;
    public static Consumer<VoiceStateUpdateEvent> voiceStateEventHandler;
    public static Consumer<WebhookUpdateEvent> webhookUpdateEventHandler;

    private void processReadyEvent(ReadyEvent readyEvent) {
        processSingleEvent(readyEvent, readyEventHandler);
    }

    private void processAutoModEvent(GatewayEvent type, AutoModEvent autoModEvent) {
        processEvent(type, autoModEvent, AutoModEventHandler.class);
    }

    private void processMessageEvent(GatewayEvent type, MessageEvent messageEvent) {
        processEvent(type, messageEvent, MessageEventHandler.class);
    }

    private void processGuildEvent(GatewayEvent type, GuildDefaultEvent guildEvent) {
        processEvent(type, guildEvent, GuildEventHandler.class);
    }

    private void processChannelEvent(GatewayEvent type, ChannelEvent channelEvent) {
        processEvent(type, channelEvent, ChannelEventHandler.class);
    }

    private void processThreadEvent(GatewayEvent type, ThreadEvent threadEvent) {
        processEvent(type, threadEvent, ThreadEventHandler.class);
    }

    private void processIntegrationEvent(GatewayEvent type, IntegrationEvent integrationEvent) {
        processEvent(type, integrationEvent, IntegrationEventHandler.class);
    }

    private void processInviteEvent(GatewayEvent type, InviteEvent inviteEvent) {
        processEvent(type, inviteEvent, InviteEventHandler.class);
    }

    private void processStageEvent(GatewayEvent type, StageEvent stageEvent) {
        processEvent(type, stageEvent, StageEventHandler.class);
    }

    private void processPresenceEvent(PresenceEvent presenceEvent) {
        processSingleEvent(presenceEvent, presenceEventHandler);
    }

    private void processTypingEvent(TypingEvent typingEvent) {
        processSingleEvent(typingEvent, typingEventHandler);
    }

    private void processBotUpdateEvent(BotUpdateEvent botUpdateEvent) {
        processSingleEvent(botUpdateEvent, botUpdateEventHandler);
    }

    private void processVoiceStateUpdateEvent(VoiceStateUpdateEvent voiceStateEvent) {
        processSingleEvent(voiceStateEvent, voiceStateEventHandler);
    }

    private void processWebhookUpdateEvent(WebhookUpdateEvent webhookUpdateEvent) {
        processSingleEvent(webhookUpdateEvent, webhookUpdateEventHandler);
    }

    private <T> void processEvent(GatewayEvent type, T event, Class<?> handlerClass) {
        if (event != null) {
            try {
                Method method = handlerClass.getDeclaredMethod("getEventHandlers");
                method.setAccessible(true);
                Map<GatewayEvent, Consumer<T>> eventHandlers = (Map<GatewayEvent, Consumer<T>>) method.invoke(null);
                if (eventHandlers.get(type) != null) {
                    eventHandlers.get(type).accept(event);
                }
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private <T> void processSingleEvent(T event, Consumer<T> handler) {
        if (event != null) {
            handler.accept(event);
        }
    }
}
