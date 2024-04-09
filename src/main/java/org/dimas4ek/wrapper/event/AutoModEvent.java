package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.automod.AutoModRule;

public class AutoModEvent implements Event {
    private final Guild guild;
    private final AutoModRule rule;

    public AutoModEvent(Guild guild, AutoModRule rule) {
        this.guild = guild;
        this.rule = rule;
    }

    public AutoModRule getRule() {
        return rule;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }
}
