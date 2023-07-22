package org.dimas4ek.event.option.creation;

import org.dimas4ek.enities.guild.OptionChoice;
import org.dimas4ek.enities.types.OptionType;

import java.util.List;

public interface OptionCreationEvent {
    OptionType getType();
    String getName();
    String getDescription();
    boolean isRequired();
    List<OptionChoice> getChoices();
}
