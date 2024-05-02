package io.github.dawncord.api.utils;

import io.github.dawncord.api.event.*;
import io.github.dawncord.api.event.handler.*;
import io.github.dawncord.api.types.GatewayEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Class responsible for managing event handlers for various types of events.
 */
public class EventProcessor {
    // Event handlers for SlashCommandEvents
    public static final Map<String, Consumer<SlashCommandEvent>> slashCommandEventHandlers = new HashMap<>();
    public static Consumer<SlashCommandEvent> defaultSlashCommandEventHandler;

    // Event handlers for ButtonEvents
    public static final Map<String, Consumer<ButtonEvent>> buttonEventHandlers = new HashMap<>();
    public static Consumer<ButtonEvent> defaultButtonComponentEventHandler;

    // Event handlers for SelectMenuEvents
    public static final Map<String, Consumer<SelectMenuEvent>> selectMenuEventHandlers = new HashMap<>();
    public static Consumer<SelectMenuEvent> defaultSelectMenuEventHandler;

    // Event handlers for ModalSubmitEvents
    public static final Map<String, Consumer<ModalSubmitEvent>> modalSubmitEventHandlers = new HashMap<>();
    public static Consumer<ModalSubmitEvent> defaultModalSubmitEventHandler;

    // Event handlers for various types of events
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
        processEvent(type, autoModEvent, AutoModEventHandler.class, autoModEventHandler);
    }

    private void processMessageEvent(GatewayEvent type, MessageEvent messageEvent) {
        processEvent(type, messageEvent, MessageEventHandler.class, messageEventHandler);
    }

    private void processGuildEvent(GatewayEvent type, GuildDefaultEvent guildEvent) {
        processEvent(type, guildEvent, GuildEventHandler.class, guildEventHandler);
    }

    private void processChannelEvent(GatewayEvent type, ChannelEvent channelEvent) {
        processEvent(type, channelEvent, ChannelEventHandler.class, channelEventHandler);
    }

    private void processThreadEvent(GatewayEvent type, ThreadEvent threadEvent) {
        processEvent(type, threadEvent, ThreadEventHandler.class, threadEventHandler);
    }

    private void processIntegrationEvent(GatewayEvent type, IntegrationEvent integrationEvent) {
        processEvent(type, integrationEvent, IntegrationEventHandler.class, integrationEventHandler);
    }

    private void processInviteEvent(GatewayEvent type, InviteEvent inviteEvent) {
        processEvent(type, inviteEvent, InviteEventHandler.class, inviteEventHandler);
    }

    private void processStageEvent(GatewayEvent type, StageEvent stageEvent) {
        processEvent(type, stageEvent, StageEventHandler.class, stageEventHandler);
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

    private <T> void processEvent(GatewayEvent type, T event, Class<?> handlerClass, EventHandler eventHandler) {
        if (event != null) {
            try {
                Method method = handlerClass.getDeclaredMethod("getEventHandlers");
                method.setAccessible(true);
                Map<GatewayEvent, Consumer<T>> eventHandlers = (Map<GatewayEvent, Consumer<T>>) method.invoke(eventHandler);
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
