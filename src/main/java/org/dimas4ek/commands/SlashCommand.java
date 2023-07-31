package org.dimas4ek.commands;

import org.dimas4ek.event.option.OptionCreationEvent;
import org.dimas4ek.event.slashcommand.creation.SlashCommandCreationEvent;
import org.dimas4ek.event.slashcommand.creation.SlashCommandCreationEventImpl;

public class SlashCommand {
    public static SlashCommandCreationEvent create(String name, String description) {
        return new SlashCommandCreationEventImpl(name, description);
    }
    
    public static SlashCommandCreationEvent create(String name, String description, OptionCreationEvent option) {
        return new SlashCommandCreationEventImpl(name, description, option);
    }
    
    public static SlashCommandCreationEvent create(String name, String description, OptionCreationEvent... options) {
        return new SlashCommandCreationEventImpl(name, description, options);
    }
}
