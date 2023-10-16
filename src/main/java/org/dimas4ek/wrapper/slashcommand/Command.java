package org.dimas4ek.wrapper.slashcommand;

import org.dimas4ek.wrapper.types.Locale;

import java.util.List;
import java.util.Map;

public interface Command {
    String getName();

    String getDescription();

    List<Option> getOptions();

    Map<Locale, String> getNameLocalizations();

    Map<Locale, String> getDescriptionLocalizations();
}
