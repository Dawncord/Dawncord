package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.command.SlashCommand;
import io.github.dawncord.api.command.SubCommand;
import io.github.dawncord.api.command.SubCommandGroup;
import io.github.dawncord.api.command.option.Option;
import io.github.dawncord.api.types.Locale;
import io.github.dawncord.api.types.OptionType;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.SlashCommandUtils;

import java.util.*;
import java.util.function.Consumer;

/**
 * Represents an action for creating a slash command.
 *
 * @see SlashCommand
 */
public class SlashCommandCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final List<Option> optionList = new ArrayList<>();
    private final List<SubCommand> subCommandList = new ArrayList<>();
    private final List<SubCommandGroup> subCommandGroupList = new ArrayList<>();
    private final Map<Locale, String> localizedNameList = new EnumMap<>(Locale.class);
    private final Map<Locale, String> localizedDescriptionList = new EnumMap<>(Locale.class);

    /**
     * Create a new {@link SlashCommandCreateAction}
     *
     * @param name        The name of the slash command.
     * @param description The description of the slash command.
     */
    public SlashCommandCreateAction(String name, String description) {
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.put("name", name);
        this.jsonObject.put("description", description);
    }

    private SlashCommandCreateAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        return this;
    }

    /**
     * Sets the name for the slash command.
     *
     * @param name the name to set
     * @return the modified SlashCommandCreateAction object
     */
    public SlashCommandCreateAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the description for the slash command.
     *
     * @param description the description to set
     * @return the modified SlashCommandCreateAction object
     */
    public SlashCommandCreateAction setDescription(String description) {
        return setProperty("description", description);
    }

    /**
     * Adds a new option to the list of options for the slash command.
     *
     * @param type        the type of the option
     * @param name        the name of the option
     * @param description the description of the option
     * @return the modified SlashCommandCreateAction object
     */
    public SlashCommandCreateAction addOption(OptionType type, String name, String description) {
        optionList.add(new Option(type, name, description));
        return this;
    }

    /**
     * Adds a new option to the list of options for the slash command.
     *
     * @param type        the type of the option
     * @param name        the name of the option
     * @param description the description of the option
     * @param isRequired  whether the option is required or not
     * @return the modified SlashCommandCreateAction object
     */
    public SlashCommandCreateAction addOption(OptionType type, String name, String description, boolean isRequired) {
        optionList.add(new Option(type, name, description, isRequired));
        return this;
    }

    /**
     * Adds a new option to the list of options for the slash command.
     *
     * @param type           the type of the option
     * @param name           the name of the option
     * @param description    the description of the option
     * @param isRequired     whether the option is required or not
     * @param isAutocomplete whether the option is an autocomplete option or not
     * @return the modified SlashCommandCreateAction object
     */
    public SlashCommandCreateAction addOption(OptionType type, String name, String description, boolean isRequired, boolean isAutocomplete) {
        optionList.add(new Option(type, name, description, isRequired, isAutocomplete));
        return this;
    }

    /**
     * Adds an option to the list of options for the slash command.
     *
     * @param option the option to add
     * @return the modified SlashCommandCreateAction object
     */
    public SlashCommandCreateAction addOption(Option option) {
        optionList.add(option);
        return this;
    }

    /**
     * Adds the given options to the list of options for the slash command.
     *
     * @param options the options to add
     * @return the modified SlashCommandCreateAction object
     */
    public SlashCommandCreateAction addOptions(Option... options) {
        Collections.addAll(optionList, options);
        return this;
    }

    /**
     * Adds the given options to the list of options for the slash command.
     *
     * @param options the options to add
     * @return the modified SlashCommandCreateAction object
     */
    public SlashCommandCreateAction addOptions(List<Option> options) {
        optionList.addAll(options);
        return this;
    }

    /**
     * Adds a localized name for the slash command.
     *
     * @param locale the locale for the localized name
     * @param name   the localized name to add
     * @return the modified SlashCommandCreateAction object
     */
    public SlashCommandCreateAction addLocalizedName(Locale locale, String name) {
        localizedNameList.put(locale, name);
        return this;
    }

    /**
     * Sets the localized names for different locales for the slash command.
     *
     * @param nameLocalizations a map of locale to localized names
     * @return the modified SlashCommandCreateAction object
     */
    public SlashCommandCreateAction setNameLocalizations(Map<Locale, String> nameLocalizations) {
        localizedNameList.putAll(nameLocalizations);
        return this;
    }

    /**
     * Adds a localized description for the slash command.
     *
     * @param locale      the locale of the description
     * @param description the description to add
     * @return the modified SlashCommandCreateAction object
     */
    public SlashCommandCreateAction addLocalizedDescription(Locale locale, String description) {
        localizedDescriptionList.put(locale, description);
        return this;
    }

    /**
     * Sets the localized descriptions for different locales for the slash command.
     *
     * @param descriptionLocalizations a map of locale to localized descriptions
     * @return the modified SlashCommandCreateAction object
     */
    public SlashCommandCreateAction setDescriptionLocalizations(Map<Locale, String> descriptionLocalizations) {
        localizedDescriptionList.putAll(descriptionLocalizations);
        return this;
    }

    /**
     * Adds a sub command for the slash command.
     *
     * @param subCommand the sub command to be added
     * @return the modified SlashCommandCreateAction object
     */
    public SlashCommandCreateAction addSubCommand(SubCommand subCommand) {
        subCommandList.add(subCommand);
        return this;
    }

    /**
     * Adds multiple sub commands for the slash command.
     *
     * @param subCommands the sub commands to be added
     * @return the modified SlashCommandCreateAction object
     */
    public SlashCommandCreateAction addSubCommands(SubCommand... subCommands) {
        Collections.addAll(subCommandList, subCommands);
        return this;
    }

    /**
     * Adds a list of subcommands for the slash command.
     *
     * @param subCommands the list of subcommands to be added
     * @return the modified SlashCommandCreateAction object
     */
    public SlashCommandCreateAction addSubCommands(List<SubCommand> subCommands) {
        subCommandList.addAll(subCommands);
        return this;
    }

    /**
     * Adds a new subcommand for the slash command.
     *
     * @param name        the name of the subcommand
     * @param description the description of the subcommand
     * @param handler     the handler function for the subcommand
     * @return the modified SlashCommandCreateAction object
     */
    public SlashCommandCreateAction addSubCommand(String name, String description, Consumer<SubCommandCreateAction> handler) {
        ActionExecutor.createSubCommand(handler, name, description, this.subCommandList);
        return this;
    }

    /**
     * Adds a sub command group for the slash command.
     *
     * @param subCommandGroup the sub command group to be added
     * @return the modified SlashCommandCreateAction object
     */
    public SlashCommandCreateAction addSubCommandGroup(SubCommandGroup subCommandGroup) {
        subCommandGroupList.add(subCommandGroup);
        return this;
    }

    /**
     * Adds the given subcommand groups to the list of subcommand groups for the slash command.
     *
     * @param subCommandGroups the subcommand groups to be added
     * @return the modified SlashCommandCreateAction object
     */
    public SlashCommandCreateAction addSubCommandGroups(SubCommandGroup... subCommandGroups) {
        Collections.addAll(subCommandGroupList, subCommandGroups);
        return this;
    }

    /**
     * Adds a list of subcommand groups for the slash command..
     *
     * @param subCommandGroups the list of subcommand groups to be added
     * @return the modified SlashCommandCreateAction object
     */
    public SlashCommandCreateAction addSubCommandGroups(List<SubCommandGroup> subCommandGroups) {
        subCommandGroupList.addAll(subCommandGroups);
        return this;
    }

    /**
     * Adds a subcommand group for the slash command.
     *
     * @param name        the name of the subcommand group
     * @param description the description of the subcommand group
     * @param handler     the handler for the subcommand group
     * @return the modified SlashCommandCreateAction object
     */
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
