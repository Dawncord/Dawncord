package org.dimas4ek.commands;

import org.dimas4ek.event.SlashCommandInteractionEvent;

import java.io.IOException;

public abstract class SlashCommand {
    public abstract String name();
    
    public abstract String description();
    
    public abstract void onEvent(SlashCommandInteractionEvent event) throws IOException;
}
