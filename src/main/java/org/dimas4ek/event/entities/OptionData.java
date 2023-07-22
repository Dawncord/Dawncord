package org.dimas4ek.event.entities;

import org.dimas4ek.enities.guild.OptionChoice;
import org.dimas4ek.enities.types.OptionType;
import org.dimas4ek.event.option.creation.OptionCreationEvent;

import java.util.List;

public class OptionData implements OptionCreationEvent {
    private final OptionType type;
    private final String name;
    private final String description;
    private final boolean required;
    private final List<OptionChoice> choices;
    
    public OptionData(OptionType type, String name, String description, boolean required) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.required = required;
        this.choices = null;
    }
    
    public OptionData(OptionType type, String name, String description, boolean required, List<OptionChoice> choices) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.required = required;
        this.choices = choices;
    }
    
    @Override
    public OptionType getType() {
        return type;
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
    public boolean isRequired() {
        return required;
    }
    
    @Override
    public List<OptionChoice> getChoices() {
        return choices;
    }
}
