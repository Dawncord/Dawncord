package io.github.dawncord.api.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.command.option.Option;
import io.github.dawncord.api.types.Locale;
import io.github.dawncord.api.types.OptionType;
import io.github.dawncord.api.utils.SlashCommandUtils;

import java.util.*;

/**
 * A builder for constructing sub-commands.
 * SubCommandBuilder facilitates the creation of sub-commands with various options and localized details.
 *
 * @see SubCommand
 * @see OptionCommandBuilder
 */
public class SubCommandBuilder implements OptionCommandBuilder {
    private static final ObjectMapper mapper = new ObjectMapper();
    private String name;
    private String description;
    private final List<Option> optionList = new ArrayList<>();
    private final Map<Locale, String> localizedNameList = new EnumMap<>(Locale.class);
    private final Map<Locale, String> localizedDescriptionList = new EnumMap<>(Locale.class);

    /**
     * Constructs a new SubCommandBuilder instance with the specified name and description.
     *
     * @param name        The name of the sub-command.
     * @param description The description of the sub-command.
     */
    public SubCommandBuilder(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public SubCommandBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public SubCommandBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public SubCommandBuilder addOption(OptionType type, String name, String description) {
        optionList.add(new Option(type, name, description));
        return this;
    }

    @Override
    public SubCommandBuilder addOption(OptionType type, String name, String description, boolean isRequired) {
        optionList.add(new Option(type, name, description, isRequired));
        return this;
    }

    @Override
    public SubCommandBuilder addOption(OptionType type, String name, String description, boolean isRequired, boolean isAutocomplete) {
        optionList.add(new Option(type, name, description, isRequired, isAutocomplete));
        return this;
    }

    @Override
    public SubCommandBuilder addOption(Option option) {
        optionList.add(option);
        return this;
    }

    @Override
    public SubCommandBuilder addOptions(Option... options) {
        Collections.addAll(optionList, options);
        return this;
    }

    @Override
    public SubCommandBuilder addOptions(Collection<Option> options) {
        optionList.addAll(options);
        return this;
    }

    @Override
    public SubCommandBuilder setLocalizedName(Locale locale, String name) {
        localizedNameList.put(locale, name);
        return this;
    }

    @Override
    public SubCommandBuilder setLocalizedNames(Map<Locale, String> localizedNames) {
        localizedNameList.putAll(localizedNames);
        return this;
    }

    @Override
    public SubCommandBuilder setLocalizedDescription(Locale locale, String description) {
        localizedDescriptionList.put(locale, description);
        return this;
    }

    @Override
    public SubCommandBuilder setLocalizedDescriptions(Map<Locale, String> localizedDescriptions) {
        localizedDescriptionList.putAll(localizedDescriptions);
        return this;
    }

    @Override
    public SubCommand build() {
        ObjectNode subCommand = mapper.createObjectNode()
                .put("type", OptionType.SUB_COMMAND.getValue())
                .put("name", name)
                .put("description", description);
        SlashCommandUtils.createDefaults(subCommand, localizedNameList, localizedDescriptionList, optionList);
        return new SubCommand(subCommand);
    }
}
