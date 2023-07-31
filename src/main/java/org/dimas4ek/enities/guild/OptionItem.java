package org.dimas4ek.enities.guild;

import org.dimas4ek.enities.types.OptionType;
import org.dimas4ek.event.option.OptionPresenter;

import java.util.List;

public interface OptionItem extends OptionPresenter {
    Guild getGuild();
    OptionType getType();
    String getName();
    String getDescription();
    boolean required();
    List<OptionChoice> getChoices();
}
