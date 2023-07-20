package org.dimas4ek.event.slashcommand.creation;

import org.dimas4ek.event.option.creation.OptionCreationEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SlashCommandCreationEventImpl implements SlashCommandCreationEvent {
    private final String name;
    private final String description;
    private List<OptionCreationEvent> options = new ArrayList<>();
    
    public SlashCommandCreationEventImpl(String name, String description) {
        this.name = name;
        this.description = description;
        this.options = null;
    }
    
    public SlashCommandCreationEventImpl(String name, String description, OptionCreationEvent option) {
        this.name = name;
        this.description = description;
        this.options.add(option);
    }
    
    public SlashCommandCreationEventImpl(String name, String description, OptionCreationEvent[] options) {
        this.name = name;
        this.description = description;
        this.options = new ArrayList<>();
        this.options.addAll(List.of(options));
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String getDescription() {
        return description;
    }
    
    @Override
    public List<OptionCreationEvent> getOptions() {
        if (this.options.size() == 1) {
            return Collections.singletonList(this.options.get(0));
        }
        return options;
    }
}
