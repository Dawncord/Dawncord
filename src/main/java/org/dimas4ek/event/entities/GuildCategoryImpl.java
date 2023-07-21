package org.dimas4ek.event.entities;

import org.dimas4ek.enities.guild.GuildCategory;
import org.dimas4ek.enities.guild.GuildChannel;

public class GuildCategoryImpl implements GuildCategory {
    private final GuildChannel channel;
    
    public GuildCategoryImpl(GuildChannel channel) {
        this.channel = channel;
    }
    
    @Override
    public String getName() {
        return channel.getName();
    }
    
    @Override
    public String getId() {
        return channel.getId();
    }
}
