package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.entities.guild.audit.AuditLog;

/**
 * Represents an audit event in a guild.
 */
public record GuildAuditEvent(Guild getGuild, GuildMember member, AuditLog.Entry entry) implements Event {
    /**
     * Constructs a GuildAuditEvent with the specified guild, member, and audit log entry.
     *
     * @param getGuild The guild associated with the audit event.
     * @param member   The guild member associated with the audit event.
     * @param entry    The audit log entry associated with the audit event.
     */
    public GuildAuditEvent {
    }

    /**
     * Retrieves the guild member associated with the audit event.
     *
     * @return The guild member associated with the audit event.
     */
    @Override
    public GuildMember member() {
        return member;
    }

    /**
     * Retrieves the audit log entry associated with the audit event.
     *
     * @return The audit log entry associated with the audit event.
     */
    @Override
    public AuditLog.Entry entry() {
        return entry;
    }
}
