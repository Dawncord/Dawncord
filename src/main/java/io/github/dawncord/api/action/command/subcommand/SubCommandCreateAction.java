package io.github.dawncord.api.action.command.subcommand;

import io.github.dawncord.api.action.command.CommandAction;
import io.github.dawncord.api.command.SubCommand;
import io.github.dawncord.api.command.option.Option;
import io.github.dawncord.api.types.OptionType;
import io.github.dawncord.api.utils.SlashCommandUtils;

import java.util.Collections;
import java.util.List;

/**
 * Represents an action for creating a subcommand.
 *
 * @see SubCommand
 */
public class SubCommandCreateAction extends CommandAction {
    /**
     * Create a new {@link SubCommandCreateAction}
     *
     * @param name           The name of the subcommand.
     * @param description    The description of the subcommand.
     * @param subCommandList The list of subcommands.
     */
    public SubCommandCreateAction(String name, String description, List<SubCommand> subCommandList) {
        super(name, description);
        this.subCommandList = subCommandList;
    }

    /**
     * Adds a new option to the list of options for the slash command.
     *
     * @param type        the type of the option
     * @param name        the name of the option
     * @param description the description of the option
     * @return the modified SubCommandCreateAction object
     */
    public SubCommandCreateAction addOption(OptionType type, String name, String description) {
        addOption(type, name, description, false, false);
        return this;
    }

    /**
     * Adds a new option to the list of options for the slash command.
     *
     * @param type        the type of the option
     * @param name        the name of the option
     * @param description the description of the option
     * @param isRequired  whether the option is required or not
     * @return the modified SubCommandCreateAction object
     */
    public SubCommandCreateAction addOption(OptionType type, String name, String description, boolean isRequired) {
        addOption(type, name, description, isRequired, false);
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
     * @return the modified SubCommandCreateAction object
     */
    public SubCommandCreateAction addOption(OptionType type, String name, String description, boolean isRequired, boolean isAutocomplete) {
        addOption(new Option(type, name, description, isRequired, isAutocomplete));
        return this;
    }

    /**
     * Adds an option to the list of options for the slash command.
     *
     * @param option the option to add
     * @return the modified SubCommandCreateAction object
     */
    public SubCommandCreateAction addOption(Option option) {
        optionList.add(option);
        hasChanges = true;
        return this;
    }

    /**
     * Adds the given options to the list of options for the slash command.
     *
     * @param options the options to add
     * @return the modified SubCommandCreateAction object
     */
    public SubCommandCreateAction addOptions(Option... options) {
        Collections.addAll(optionList, options);
        hasChanges = true;
        return this;
    }

    /**
     * Adds the given options to the list of options for the slash command.
     *
     * @param options the options to add
     * @return the modified SubCommandCreateAction object
     */
    public SubCommandCreateAction addOptions(List<Option> options) {
        optionList.addAll(options);
        hasChanges = true;
        return this;
    }

    @Override
    protected void submit() {
        SlashCommandUtils.createDefaults(jsonObject, localizedNameList, localizedDescriptionList, optionList);
        subCommandList.add(new SubCommand(jsonObject));
    }
}
