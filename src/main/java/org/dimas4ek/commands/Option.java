package org.dimas4ek.commands;

import org.dimas4ek.enities.types.OptionType;
import org.dimas4ek.event.option.creation.OptionCreationEvent;
import org.dimas4ek.event.option.creation.OptionData;

import java.util.List;

public class Option {
    public static OptionCreationEvent addOption(OptionType type, String name, String description, boolean required) {
        return new OptionData(type, name, description, required);
    }
    
    public static OptionCreationEvent addOption(OptionData option) {
        return option;
    }
    
    public static OptionCreationEvent[] addOptions(List<OptionData> options) {
        return options.toArray(new OptionCreationEvent[0]);
    }
}