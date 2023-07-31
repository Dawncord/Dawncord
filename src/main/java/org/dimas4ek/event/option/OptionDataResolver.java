package org.dimas4ek.event.option;

import org.dimas4ek.enities.types.OptionType;

public interface OptionDataResolver {
    String getName();
    
    OptionType getType();
    
    String getValue();
}
