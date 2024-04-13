package org.dimas4ek.wrapper.command;

import org.dimas4ek.wrapper.types.Locale;

import java.util.Map;

public interface CommandBuilder {
    CommandBuilder setName(String name);

    CommandBuilder setDescription(String description);

    CommandBuilder setLocalizedName(Locale locale, String name);

    CommandBuilder setLocalizedNames(Map<Locale, String> localizedNames);

    CommandBuilder setLocalizedDescription(Locale locale, String description);

    CommandBuilder setLocalizedDescriptions(Map<Locale, String> localizedDescriptions);

    Command build();
}
