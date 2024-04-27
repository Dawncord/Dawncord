package org.dimas4ek.dawncord.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.dawncord.ApiClient;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.command.SubCommand;
import org.dimas4ek.dawncord.command.SubCommandGroup;
import org.dimas4ek.dawncord.command.option.Option;
import org.dimas4ek.dawncord.types.Locale;
import org.dimas4ek.dawncord.types.OptionType;
import org.dimas4ek.dawncord.utils.ActionExecutor;
import org.dimas4ek.dawncord.utils.SlashCommandUtils;

import java.util.*;
import java.util.function.Consumer;

public class SlashCommandCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final List<Option> optionList = new ArrayList<>();
    private final List<SubCommand> subCommandList = new ArrayList<>();
    private final List<SubCommandGroup> subCommandGroupList = new ArrayList<>();
    private final Map<Locale, String> localizedNameList = new EnumMap<>(Locale.class);
    private final Map<Locale, String> localizedDescriptionList = new EnumMap<>(Locale.class);

    public SlashCommandCreateAction(String name, String description) {
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.put("name", name);
        this.jsonObject.put("description", description);
    }

    public SlashCommandCreateAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        return this;
    }

    public SlashCommandCreateAction setName(String name) {
        return setProperty("name", name);
    }

    public SlashCommandCreateAction setDescription(String description) {
        return setProperty("description", description);
    }

    public SlashCommandCreateAction addOption(OptionType type, String name, String description) {
        optionList.add(new Option(type, name, description));
        return this;
    }

    public SlashCommandCreateAction addOption(OptionType type, String name, String description, boolean isRequired) {
        optionList.add(new Option(type, name, description, isRequired));
        return this;
    }

    public SlashCommandCreateAction addOption(OptionType type, String name, String description, boolean isRequired, boolean isAutocomplete) {
        optionList.add(new Option(type, name, description, isRequired, isAutocomplete));
        return this;
    }

    public SlashCommandCreateAction addOption(Option option) {
        optionList.add(option);
        return this;
    }

    public SlashCommandCreateAction addOptions(Option... options) {
        Collections.addAll(optionList, options);
        return this;
    }

    public SlashCommandCreateAction addOptions(List<Option> options) {
        optionList.addAll(options);
        return this;
    }

    public SlashCommandCreateAction addLocalizedName(Locale locale, String name) {
        localizedNameList.put(locale, name);
        return this;
    }

    public SlashCommandCreateAction setNameLocalizations(Map<Locale, String> nameLocalizations) {
        localizedNameList.putAll(nameLocalizations);
        return this;
    }

    public SlashCommandCreateAction addLocalizedDescription(Locale locale, String description) {
        localizedDescriptionList.put(locale, description);
        return this;
    }

    public SlashCommandCreateAction setDescriptionLocalizations(Map<Locale, String> descriptionLocalizations) {
        localizedDescriptionList.putAll(descriptionLocalizations);
        return this;
    }

    public SlashCommandCreateAction addSubCommand(SubCommand subCommand) {
        subCommandList.add(subCommand);
        return this;
    }

    public SlashCommandCreateAction addSubCommands(SubCommand... subCommands) {
        Collections.addAll(subCommandList, subCommands);
        return this;
    }

    public SlashCommandCreateAction addSubCommands(List<SubCommand> subCommands) {
        subCommandList.addAll(subCommands);
        return this;
    }

    public SlashCommandCreateAction addSubCommand(String name, String description, Consumer<SubCommandCreateAction> handler) {
        ActionExecutor.createSubCommand(handler, name, description, this.subCommandList);
        return this;
    }

    public SlashCommandCreateAction addSubCommandGroup(SubCommandGroup subCommandGroup) {
        subCommandGroupList.add(subCommandGroup);
        return this;
    }

    public SlashCommandCreateAction addSubCommandGroups(SubCommandGroup... subCommandGroups) {
        Collections.addAll(subCommandGroupList, subCommandGroups);
        return this;
    }

    public SlashCommandCreateAction addSubCommandGroups(List<SubCommandGroup> subCommandGroups) {
        subCommandGroupList.addAll(subCommandGroups);
        return this;
    }

    public SlashCommandCreateAction addSubCommandGroup(String name, String description, Consumer<SubCommandGroupCreateAction> handler) {
        ActionExecutor.createSubCommandGroup(handler, name, description, this.subCommandGroupList);
        return this;
    }

    private void submit() {
        SlashCommandUtils.createDefaults(jsonObject, localizedNameList, localizedDescriptionList, optionList);
        if (optionList.isEmpty()) {
            if (!subCommandList.isEmpty()) {
                SlashCommandUtils.createSubCommandsArray(jsonObject, subCommandList);
            }
            if (!subCommandGroupList.isEmpty()) {
                SlashCommandUtils.createSubCommandGroupArray(jsonObject, subCommandGroupList);
            }
        } else {
            if (!subCommandList.isEmpty() || !subCommandGroupList.isEmpty()) {
                throw new IllegalArgumentException("Cannot have options and subcommands in the same command");
            }
        }
        ApiClient.post(jsonObject, Routes.SlashCommand.All());
        jsonObject.removeAll();
    }
}
