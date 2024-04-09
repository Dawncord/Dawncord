package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildMember;
import org.dimas4ek.wrapper.entities.guild.audit.AuditLog;

public class GuildAuditEvent implements Event {
    private final Guild guild;
    private final GuildMember member;
    private final AuditLog.Entry entry;

    public GuildAuditEvent(Guild guild, GuildMember member, AuditLog.Entry entry) {
        this.guild = guild;
        this.member = member;
        this.entry = entry;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    public GuildMember getMember() {
        return member;
    }

    public AuditLog.Entry getEntry() {
        return entry;
    }
}
