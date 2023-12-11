package org.dimas4ek.wrapper.command;

import org.dimas4ek.wrapper.command.option.Option;
import org.dimas4ek.wrapper.types.CommandType;
import org.dimas4ek.wrapper.types.Locale;
import org.dimas4ek.wrapper.types.PermissionType;

import java.util.List;
import java.util.Map;

public interface Command {
    String getName();

    String getDescription();

    String getApplicationId();

    long getApplicationIdLong();

    List<PermissionType> getMemberPermissions();

    CommandType getType();

    boolean isNsfw();

    String getVersion();

    List<Option> getOptions();

    Map<Locale, String> getNameLocalizations();

    Map<Locale, String> getDescriptionLocalizations();
}
