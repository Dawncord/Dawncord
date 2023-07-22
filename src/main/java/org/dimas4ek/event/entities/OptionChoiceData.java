package org.dimas4ek.event.entities;

import org.dimas4ek.enities.guild.OptionChoice;

public class OptionChoiceData implements OptionChoice {
    private final String name;
    private final String value;
    
    public OptionChoiceData(String name, String value) {
        this.name = name;
        this.value = value;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String getValue() {
        return value;
    }
}
