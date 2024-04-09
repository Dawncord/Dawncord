package org.dimas4ek.wrapper.event.handler;

import lombok.Getter;
import org.dimas4ek.wrapper.event.GuildDefaultEvent;
import org.dimas4ek.wrapper.types.GatewayEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class GuildEventHandler {
    @Getter
    private static final Map<GatewayEvent, Consumer<GuildDefaultEvent>> eventHandlers = new HashMap<>();

    public void create(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_CREATE, handler);
    }

    public void update(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_UPDATE, handler);
    }

    public void delete(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_DELETE, handler);
    }

    public void auditLogEntryCreate(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_AUDIT_LOG_ENTRY_CREATE, handler);
    }

    public void banAdd(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_BAN_ADD, handler);
    }

    public void banRemove(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_BAN_REMOVE, handler);
    }

    public void emojiUpdate(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_EMOJIS_UPDATE, handler);
    }

    public void stickerUpdate(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_STICKERS_UPDATE, handler);
    }

    public void integrationUpdate(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_INTEGRATIONS_UPDATE, handler);
    }

    public void memberAdd(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_MEMBER_ADD, handler);
    }

    public void memberUpdate(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_MEMBER_UPDATE, handler);
    }

    public void memberRemove(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_MEMBER_REMOVE, handler);
    }

    public void memberChunk(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_MEMBERS_CHUNK, handler);
    }

    public void roleCreate(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_ROLE_CREATE, handler);
    }

    public void roleUpdate(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_ROLE_UPDATE, handler);
    }

    public void roleDelete(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_ROLE_DELETE, handler);
    }

    public void scheduledEventCreate(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_SCHEDULED_EVENT_CREATE, handler);
    }

    public void scheduledEventUpdate(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_SCHEDULED_EVENT_UPDATE, handler);
    }

    public void scheduledEventDelete(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_SCHEDULED_EVENT_DELETE, handler);
    }

    public void scheduledEventUserAdd(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_SCHEDULED_EVENT_USER_ADD, handler);
    }

    public void scheduledEventUserRemove(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_SCHEDULED_EVENT_USER_REMOVE, handler);
    }
}
