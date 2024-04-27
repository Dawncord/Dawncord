package org.dimas4ek.dawncord.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.dawncord.command.option.Option;
import org.dimas4ek.dawncord.types.Locale;
import org.dimas4ek.dawncord.types.OptionType;
import org.dimas4ek.dawncord.utils.SlashCommandUtils;

import java.util.*;

public class SlashCommandBuilder implements OptionCommandBuilder {
    private static final ObjectMapper mapper = new ObjectMapper();
    private String name;
    private String description;
    private boolean nsfw;
    private final List<SubCommandGroup> subCommandGroupList = new ArrayList<>();
    private final List<SubCommand> subCommandList = new ArrayList<>();
    private final List<Option> optionList = new ArrayList<>();
    private final Map<Locale, String> localizedNameList = new EnumMap<>(Locale.class);
    private final Map<Locale, String> localizedDescriptionList = new EnumMap<>(Locale.class);

    public SlashCommandBuilder(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public SlashCommandBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public SlashCommandBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public SlashCommandBuilder addOption(OptionType type, String name, String description) {
        optionList.add(new Option(type, name, description));
        return this;
    }

    @Override
    public SlashCommandBuilder addOption(OptionType type, String name, String description, boolean isRequired) {
        optionList.add(new Option(type, name, description, isRequired));
        return this;
    }

    @Override
    public SlashCommandBuilder addOption(OptionType type, String name, String description, boolean isRequired, boolean isAutocomplete) {
        optionList.add(new Option(type, name, description, isRequired, isAutocomplete));
        return this;
    }

    @Override
    public SlashCommandBuilder addOption(Option option) {
        optionList.add(option);
        return this;
    }

    @Override
    public SlashCommandBuilder addOptions(Option... options) {
        Collections.addAll(optionList, options);
        return this;
    }

    @Override
    public SlashCommandBuilder addOptions(Collection<Option> options) {
        optionList.addAll(options);
        return this;
    }

    @Override
    public SlashCommandBuilder setLocalizedName(Locale locale, String name) {
        localizedNameList.put(locale, name);
        return this;
    }

    @Override
    public SlashCommandBuilder setLocalizedNames(Map<Locale, String> localizedNames) {
        localizedNameList.putAll(localizedNames);
        return this;
    }

    @Override
    public SlashCommandBuilder setLocalizedDescription(Locale locale, String description) {
        localizedDescriptionList.put(locale, description);
        return this;
    }

    @Override
    public SlashCommandBuilder setLocalizedDescriptions(Map<Locale, String> localizedDescriptions) {
        localizedDescriptionList.putAll(localizedDescriptions);
        return this;
    }

    public SlashCommandBuilder setNsfw(boolean nsfw) {
        this.nsfw = nsfw;
        return this;
    }

    public SlashCommandBuilder addSubCommandGroup(SubCommandGroup subCommandGroups) {
        subCommandGroupList.add(subCommandGroups);
        return this;
    }

    public SlashCommandBuilder addSubCommandGroups(SubCommandGroup... subCommandGroups) {
        Collections.addAll(subCommandGroupList, subCommandGroups);
        return this;
    }

    public SlashCommandBuilder addSubCommandGroup(List<SubCommandGroup> subCommandGroup) {
        subCommandGroupList.addAll(subCommandGroup);
        return this;
    }

    public SlashCommandBuilder addSubCommand(SubCommand subCommand) {
        subCommandList.add(subCommand);
        return this;
    }

    public SlashCommandBuilder addSubCommands(SubCommand... subCommands) {
        Collections.addAll(subCommandList, subCommands);
        return this;
    }

    public SlashCommandBuilder addSubCommands(List<SubCommand> subCommands) {
        subCommandList.addAll(subCommands);
        return this;
    }

    @Override
    public SlashCommand build() {
        ObjectNode slashCommand = mapper.createObjectNode()
                .put("name", name)
                .put("description", description)
                .put("nsfw", nsfw);
        SlashCommandUtils.createDefaults(slashCommand, localizedNameList, localizedDescriptionList, optionList);
        if (!subCommandList.isEmpty()) {
            SlashCommandUtils.createSubCommandsArray(slashCommand, subCommandList);
        }
        if (!subCommandGroupList.isEmpty()) {
            SlashCommandUtils.createSubCommandGroupArray(slashCommand, subCommandGroupList);
        }
        return new SlashCommand(slashCommand);
    }
}
