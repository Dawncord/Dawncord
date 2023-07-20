package org.dimas4ek.event.option.creation;

import org.dimas4ek.enities.types.OptionType;

public class OptionData implements OptionCreationEvent {
    private final OptionType type;
    private final String name;
    private final String description;
    private final boolean required;
    
    public OptionData(OptionType type, String name, String description, boolean required) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.required = required;
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
}
