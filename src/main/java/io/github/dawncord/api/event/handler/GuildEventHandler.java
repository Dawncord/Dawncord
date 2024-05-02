package io.github.dawncord.api.event.handler;

import io.github.dawncord.api.event.GuildDefaultEvent;
import io.github.dawncord.api.types.GatewayEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * An event handler for guild events.
 */
public class GuildEventHandler implements EventHandler {
    private static final Map<GatewayEvent, Consumer<GuildDefaultEvent>> eventHandlers = new HashMap<>();

    private Map<GatewayEvent, Consumer<GuildDefaultEvent>> getEventHandlers() {
        return eventHandlers;
    }

    /**
     * Associates the guild creation event with the provided handler.
     *
     * @param handler The handler for the guild creation event.
     */
    public void create(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_CREATE, handler);
    }

    /**
     * Associates the guild update event with the provided handler.
     *
     * @param handler The handler for the guild update event.
     */
    public void update(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_UPDATE, handler);
    }

    /**
     * Associates the guild deletion event with the provided handler.
     *
     * @param handler The handler for the guild deletion event.
     */
    public void delete(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_DELETE, handler);
    }

    /**
     * Associates the audit log entry creation event with the provided handler.
     *
     * @param handler The handler for the audit log entry creation event.
     */
    public void auditLogEntryCreate(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_AUDIT_LOG_ENTRY_CREATE, handler);
    }

    /**
     * Associates the addition of a ban event with the provided handler.
     *
     * @param handler The handler for the ban addition event.
     */
    public void banAdd(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_BAN_ADD, handler);
    }

    /**
     * Associates the removal of a ban event with the provided handler.
     *
     * @param handler The handler for the ban removal event.
     */
    public void banRemove(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_BAN_REMOVE, handler);
    }

    /**
     * Associates the emoji update event with the provided handler.
     *
     * @param handler The handler for the emoji update event.
     */
    public void emojiUpdate(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_EMOJIS_UPDATE, handler);
    }

    /**
     * Associates the sticker update event with the provided handler.
     *
     * @param handler The handler for the sticker update event.
     */
    public void stickerUpdate(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_STICKERS_UPDATE, handler);
    }

    /**
     * Associates the integration update event with the provided handler.
     *
     * @param handler The handler for the integration update event.
     */
    public void integrationUpdate(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_INTEGRATIONS_UPDATE, handler);
    }

    /**
     * Associates the member addition event with the provided handler.
     *
     * @param handler The handler for the member addition event.
     */
    public void memberAdd(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_MEMBER_ADD, handler);
    }

    /**
     * Associates the member update event with the provided handler.
     *
     * @param handler The handler for the member update event.
     */
    public void memberUpdate(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_MEMBER_UPDATE, handler);
    }

    /**
     * Associates the member removal event with the provided handler.
     *
     * @param handler The handler for the member removal event.
     */
    public void memberRemove(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_MEMBER_REMOVE, handler);
    }

    /**
     * Associates the member chunk event with the provided handler.
     *
     * @param handler The handler for the member chunk event.
     */
    public void memberChunk(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_MEMBERS_CHUNK, handler);
    }

    /**
     * Associates the role creation event with the provided handler.
     *
     * @param handler The handler for the role creation event.
     */
    public void roleCreate(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_ROLE_CREATE, handler);
    }

    /**
     * Associates the role update event with the provided handler.
     *
     * @param handler The handler for the role update event.
     */
    public void roleUpdate(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_ROLE_UPDATE, handler);
    }

    /**
     * Associates the role deletion event with the provided handler.
     *
     * @param handler The handler for the role deletion event.
     */
    public void roleDelete(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_ROLE_DELETE, handler);
    }

    /**
     * Associates the scheduled event creation event with the provided handler.
     *
     * @param handler The handler for the scheduled event creation event.
     */
    public void scheduledEventCreate(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_SCHEDULED_EVENT_CREATE, handler);
    }

    /**
     * Associates the scheduled event update event with the provided handler.
     *
     * @param handler The handler for the scheduled event update event.
     */
    public void scheduledEventUpdate(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_SCHEDULED_EVENT_UPDATE, handler);
    }

    /**
     * Associates the scheduled event deletion event with the provided handler.
     *
     * @param handler The handler for the scheduled event deletion event.
     */
    public void scheduledEventDelete(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_SCHEDULED_EVENT_DELETE, handler);
    }

    /**
     * Associates the scheduled event user addition event with the provided handler.
     *
     * @param handler The handler for the scheduled event user addition event.
     */
    public void scheduledEventUserAdd(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_SCHEDULED_EVENT_USER_ADD, handler);
    }

    /**
     * Associates the scheduled event user removal event with the provided handler.
     *
     * @param handler The handler for the scheduled event user removal event.
     */
    public void scheduledEventUserRemove(Consumer<GuildDefaultEvent> handler) {
        eventHandlers.put(GatewayEvent.GUILD_SCHEDULED_EVENT_USER_REMOVE, handler);
    }
}
