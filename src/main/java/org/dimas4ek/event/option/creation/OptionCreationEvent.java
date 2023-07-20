package org.dimas4ek.event.option.creation;

import org.dimas4ek.enities.types.OptionType;

public interface OptionCreationEvent {
    OptionType getType();
    String getName();
    String getDescription();
    boolean isRequired();
}
