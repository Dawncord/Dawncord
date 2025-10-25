package io.github.dawncord.api.action.command.slashcommand;

import io.github.dawncord.api.action.Action;
import io.github.dawncord.api.action.command.CommandAction;
import io.github.dawncord.api.action.command.subcommand.SubCommandCreateAction;
import io.github.dawncord.api.action.command.subcommand.SubCommandGroupCreateAction;
import io.github.dawncord.api.command.SubCommand;
import io.github.dawncord.api.command.SubCommandGroup;
import io.github.dawncord.api.command.option.Option;
import io.github.dawncord.api.types.Locale;
import io.github.dawncord.api.types.OptionType;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.SlashCommandUtils;

import java.util.*;
import java.util.function.Consumer;

public abstract class SlashCommandAction extends CommandAction {
    protected final List<SubCommandGroup> subCommandGroupList = new ArrayList<>();

    protected SlashCommandAction(String name, String description) {
        super(name, description);
    }
    
    protected SlashCommandAction() {
        super();
    }
    
    //todo 
    // ALL these methods should be split by classes or interfaces
    // slash - options, sub, subgroups
    // sub - options
    // subgroups - sub

    /**
     * Adds a new option to the list of options for the slash command.
     *
     * @param type        the type of the option
     * @param name        the name of the option
     * @param description the description of the option
     * @return the modified SlashCommandAction object
     */
    public SlashCommandAction addOption(OptionType type, String name, String description) {
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
     * @return the modified SlashCommandAction object
     */
    public SlashCommandAction addOption(OptionType type, String name, String description, boolean isRequired) {
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
     * @return the modified SlashCommandAction object
     */
    public SlashCommandAction addOption(OptionType type, String name, String description, boolean isRequired, boolean isAutocomplete) {
        addOption(new Option(type, name, description, isRequired, isAutocomplete));
        return this;
    }

    /**
     * Adds an option to the list of options for the slash command.
     *
     * @param option the option to add
     * @return the modified SlashCommandAction object
     */
    public SlashCommandAction addOption(Option option) {
        optionList.add(option);
        hasChanges = true;
        return this;
    }

    /**
     * Adds the given options to the list of options for the slash command.
     *
     * @param options the options to add
     * @return the modified SlashCommandAction object
     */
    public SlashCommandAction addOptions(Option... options) {
        Collections.addAll(optionList, options);
        hasChanges = true;
        return this;
    }

    /**
     * Adds the given options to the list of options for the slash command.
     *
     * @param options the options to add
     * @return the modified SlashCommandAction object
     */
    public SlashCommandAction addOptions(List<Option> options) {
        optionList.addAll(options);
        hasChanges = true;
        return this;
    }

    /**
     * Adds a sub command for the slash command.
     *
     * @param subCommand the sub command to be added
     * @return the modified SlashCommandAction object
     */
    public SlashCommandAction addSubCommand(SubCommand subCommand) {
        subCommandList.add(subCommand);
        return this;
    }

    /**
     * Adds multiple sub commands for the slash command.
     *
     * @param subCommands the sub commands to be added
     * @return the modified SlashCommandAction object
     */
    public SlashCommandAction addSubCommands(SubCommand... subCommands) {
        Collections.addAll(subCommandList, subCommands);
        return this;
    }

    /**
     * Adds a list of subcommands for the slash command.
     *
     * @param subCommands the list of subcommands to be added
     * @return the modified SlashCommandAction object
     */
    public SlashCommandAction addSubCommands(List<SubCommand> subCommands) {
        subCommandList.addAll(subCommands);
        return this;
    }

    /**
     * Adds a new subcommand for the slash command.
     *
     * @param name        the name of the subcommand
     * @param description the description of the subcommand
     * @param handler     the handler function for the subcommand
     * @return the modified SlashCommandAction object
     */
    public SlashCommandAction addSubCommand(String name, String description, Consumer<SubCommandCreateAction> handler) {
        ActionExecutor.createSubCommand(handler, name, description, this.subCommandList);
        return this;
    }

    /**
     * Adds a sub command group for the slash command.
     *
     * @param subCommandGroup the sub command group to be added
     * @return the modified SlashCommandAction object
     */
    public SlashCommandAction addSubCommandGroup(SubCommandGroup subCommandGroup) {
        subCommandGroupList.add(subCommandGroup);
        return this;
    }

    /**
     * Adds the given subcommand groups to the list of subcommand groups for the slash command.
     *
     * @param subCommandGroups the subcommand groups to be added
     * @return the modified SlashCommandAction object
     */
    public SlashCommandAction addSubCommandGroups(SubCommandGroup... subCommandGroups) {
        Collections.addAll(subCommandGroupList, subCommandGroups);
        return this;
    }

    /**
     * Adds a list of subcommand groups for the slash command..
     *
     * @param subCommandGroups the list of subcommand groups to be added
     * @return the modified SlashCommandAction object
     */
    public SlashCommandAction addSubCommandGroups(List<SubCommandGroup> subCommandGroups) {
        subCommandGroupList.addAll(subCommandGroups);
        return this;
    }

    /**
     * Adds a subcommand group for the slash command.
     *
     * @param name        the name of the subcommand group
     * @param description the description of the subcommand group
     * @param handler     the handler for the subcommand group
     * @return the modified SlashCommandAction object
     */
    public SlashCommandAction addSubCommandGroup(String name, String description, Consumer<SubCommandGroupCreateAction> handler) {
        ActionExecutor.createSubCommandGroup(handler, name, description, this.subCommandGroupList);
        return this;
    }
    
    @Override
    protected void submit() {
        if (!hasChanges) return;
        
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
    }
}
