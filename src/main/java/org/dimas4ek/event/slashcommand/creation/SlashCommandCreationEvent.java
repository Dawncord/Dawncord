package org.dimas4ek.event.slashcommand.creation;

import org.dimas4ek.event.option.creation.OptionCreationEvent;

import java.util.List;

public interface SlashCommandCreationEvent {
    String getName();
    String getDescription();
    List<OptionCreationEvent> getOptions();
}
