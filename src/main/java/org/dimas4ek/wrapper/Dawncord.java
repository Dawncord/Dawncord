package org.dimas4ek.wrapper;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.dimas4ek.wrapper.event.*;
import org.dimas4ek.wrapper.event.handler.*;
import org.dimas4ek.wrapper.listeners.EventListener;
import org.dimas4ek.wrapper.listeners.InteractionListener;
import org.dimas4ek.wrapper.listeners.MainListener;
import org.dimas4ek.wrapper.types.GatewayIntent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static org.dimas4ek.wrapper.utils.EventHandler.*;

public class Dawncord {
    private final ObjectMapper mapper = new ObjectMapper();
    private final WebSocket webSocket;
    //private static Map<GatewayEvent, Consumer<GatewayEvent>> eventHandlers = new HashMap<>();
    private final Map<Class<? extends Event>, Consumer<Event>> eventHandlers = new HashMap<>();
    private long intentsValue = 0;

    public Dawncord(String token) {
        WebSocketFactory factory = new WebSocketFactory();
        try {
            webSocket = factory.createSocket(Constants.GATEWAY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        webSocket.addListener(new MainListener());
        webSocket.addListener(new InteractionListener());
        webSocket.addListener(new EventListener(this));
        //webSocket.addListener(new MessageListener());
        //webSocket.addListener(new GuildListener());
        //webSocket.addListener(new ChannelListener());

        assignConstants(token);
    }

    public void setIntents(GatewayIntent... intents) {
        for (GatewayIntent intent : intents) {
            if (intent == GatewayIntent.ALL) {
                for (GatewayIntent i : GatewayIntent.values()) {
                    intentsValue |= i.getValue();
                }
                break;
            }
            intentsValue |= intent.getValue();
        }
    }

    private void assignConstants(String token) {
        Constants.BOT_TOKEN = token;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.API_URL + "/applications/@me")
                .addHeader("Authorization", "Bot " + token)
                .build();

        try (Response response = client.newCall(request).execute();
             ResponseBody body = response.body()) {
            if (response.isSuccessful() && body != null) {
                JsonNode node = mapper.readTree(body.string());
                Constants.APPLICATION_ID = node.get("id").asText();
                Constants.CLIENT_KEY = node.get("verify_key").asText();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends Event> void on(Class<T> type, Consumer<T> handler) {
        eventHandlers.put(type, event -> handler.accept(type.cast(event)));
    }

    private void processEvent(Event event) {
        Consumer<Event> handler = eventHandlers.get(event.getClass());

        if (handler != null) {
            handler.accept(event);
        }
    }

    public void onSlashCommand(String commandName, Consumer<SlashCommandEvent> handler) {
        slashCommandHandlers.put(commandName, handler);
    }

    public void onSlashCommand(Consumer<SlashCommandEvent> handler) {
        defaultSlashCommandHandler = handler;
    }

    private static void processSlashCommand(SlashCommandEvent slashCommandEvent) {
        String commandName = slashCommandEvent.getCommandName();
        Consumer<SlashCommandEvent> handler = slashCommandHandlers.get(commandName);
        if (handler != null) {
            handler.accept(slashCommandEvent);
        } else if (defaultSlashCommandHandler != null) {
            defaultSlashCommandHandler.accept(slashCommandEvent);
        }
    }

    public void onReady(Consumer<ReadyEvent> handler) {
        readyEventHandler = handler;
    }

    public AutoModEventHandler onAutoMod() {
        return autoModEventHandler;
    }

    public MessageEventHandler onMessage() {
        return messageEventHandler;
    }

    public GuildEventHandler onGuild() {
        return guildEventHandler;
    }

    public ChannelEventHandler onChannel() {
        return channelEventHandler;
    }

    public ThreadEventHandler onThread() {
        return threadEventHandler;
    }

    public IntegrationEventHandler onIntegration() {
        return integrationEventHandler;
    }

    public InviteEventHandler onInvite() {
        return inviteEventHandler;
    }

    public StageEventHandler onStage() {
        return stageEventHandler;
    }

    public void onPresenceUpdate(Consumer<PresenceEvent> handler) {
        presenceEventHandler = handler;
    }

    public void onTyping(Consumer<TypingEvent> handler) {
        typingEventHandler = handler;
    }

    public void onBotUpdate(Consumer<BotUpdateEvent> handler) {
        botUpdateEventHandler = handler;
    }

    public void onVoiceStateUpdate(Consumer<VoiceStateUpdateEvent> handler) {
        voiceStateEventHandler = handler;
    }

    public void onWebhookUpdate(Consumer<WebhookUpdateEvent> handler) {
        webhookUpdateEventHandler = handler;
    }

    /*private void processReadyEvent(ReadyEvent readyEvent) {
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
    }*/

    /*private <T> void processEvent(GatewayEvent type, T event, Class<?> handlerClass) {
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
    }*/

    public void start() {
        try {
            webSocket.connect();
        } catch (WebSocketException e) {
            throw new RuntimeException(e);
        }

        ObjectNode identify = mapper.createObjectNode()
                .put("op", 2)
                .set("d", mapper.createObjectNode()
                        .put("token", Constants.BOT_TOKEN)
                        .put("intents", intentsValue)
                        .set("properties", mapper.createObjectNode()
                                .put("os", "linux")
                                .put("browser", "discord-java-gateway")
                                .put("device", "discord-java-gateway")
                        )
                );

        webSocket.sendText(identify.toString());
    }
}
