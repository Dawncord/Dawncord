package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.entities.guild.audit.AuditLog;

/**
 * Represents an audit event in a guild.
 */
public class GuildAuditEvent implements Event {
    private final Guild guild;
    private final GuildMember member;
    private final AuditLog.Entry entry;

    /**
     * Constructs a GuildAuditEvent with the specified guild, member, and audit log entry.
     *
     * @param guild  The guild associated with the audit event.
     * @param member The guild member associated with the audit event.
     * @param entry  The audit log entry associated with the audit event.
     */
    public GuildAuditEvent(Guild guild, GuildMember member, AuditLog.Entry entry) {
        this.guild = guild;
        this.member = member;
        this.entry = entry;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    /**
     * Retrieves the guild member associated with the audit event.
     *
     * @return The guild member associated with the audit event.
     */
    public GuildMember getMember() {
        return member;
    }

    /**
     * Retrieves the audit log entry associated with the audit event.
     *
     * @return The audit log entry associated with the audit event.
     */
    public AuditLog.Entry getEntry() {
        return entry;
    }
}
