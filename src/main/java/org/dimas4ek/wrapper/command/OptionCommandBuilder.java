package org.dimas4ek.wrapper.command;

import org.dimas4ek.wrapper.command.option.Option;
import org.dimas4ek.wrapper.types.OptionType;

import java.util.Collection;

public interface OptionCommandBuilder extends CommandBuilder {
    OptionCommandBuilder addOption(OptionType type, String name, String description);

    OptionCommandBuilder addOption(OptionType type, String name, String description, boolean isRequired);

    OptionCommandBuilder addOption(OptionType type, String name, String description, boolean isRequired, boolean isAutocomplete);

    OptionCommandBuilder addOption(Option option);

    OptionCommandBuilder addOptions(Option... options);

    OptionCommandBuilder addOptions(Collection<Option> options);
}