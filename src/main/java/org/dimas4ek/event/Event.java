package org.dimas4ek.event;

import org.dimas4ek.event.slashcommand.interaction.SlashCommandInteractionEvent;

import java.io.IOException;

public abstract class Event {
    /*public abstract String name();
    
    public abstract String description();*/
    
    public abstract void onEvent(SlashCommandInteractionEvent event) throws IOException;
}
