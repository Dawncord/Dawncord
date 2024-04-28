package org.dimas4ek.dawncord.command;

import org.dimas4ek.dawncord.types.Locale;

import java.util.Map;

public interface Command {
    String getName();

    String getDescription();

    Map<Locale, String> getNameLocalizations();

    Map<Locale, String> getDescriptionLocalizations();

}
