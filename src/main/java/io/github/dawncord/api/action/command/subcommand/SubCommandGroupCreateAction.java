package io.github.dawncord.api.action.command.subcommand;

import io.github.dawncord.api.action.command.CommandAction;
import io.github.dawncord.api.command.SubCommand;
import io.github.dawncord.api.command.SubCommandGroup;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.SlashCommandUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents an action for creating a subcommand group.
 *
 * @see SubCommandGroup
 */
public class SubCommandGroupCreateAction extends CommandAction {
    private final List<SubCommandGroup> subCommandGroupList;
    
    /**
     * Create a new {@link SubCommandGroupCreateAction}
     *
     * @param name                The name of the subcommand group.
     * @param description         The description of the subcommand group.
     * @param subCommandGroupList The list of subcommand groups.
     */
    public SubCommandGroupCreateAction(String name, String description, List<SubCommandGroup> subCommandGroupList) {
        super(name, description);
        this.subCommandGroupList = subCommandGroupList;
    }

    /**
     * Adds a sub command for the slash command.
     *
     * @param subCommand the sub command to be added
     * @return the modified SubCommandGroupCreateAction object
     */
    public SubCommandGroupCreateAction addSubCommand(SubCommand subCommand) {
        subCommandList.add(subCommand);
        return this;
    }

    /**
     * Adds multiple sub commands for the slash command.
     *
     * @param subCommands the sub commands to be added
     * @return the modified SubCommandGroupCreateAction object
     */
    public SubCommandGroupCreateAction addSubCommands(SubCommand... subCommands) {
        Collections.addAll(subCommandList, subCommands);
        return this;
    }

    /**
     * Adds a list of subcommands for the slash command.
     *
     * @param subCommands the list of subcommands to be added
     * @return the modified SubCommandGroupCreateAction object
     */
    public SubCommandGroupCreateAction addSubCommands(List<SubCommand> subCommands) {
        subCommandList.addAll(subCommands);
        return this;
    }

    /**
     * Adds a new subcommand for the slash command.
     *
     * @param name        the name of the subcommand
     * @param description the description of the subcommand
     * @param handler     the handler function for the subcommand
     * @return the modified SubCommandGroupCreateAction object
     */
    public SubCommandGroupCreateAction addSubCommand(String name, String description, Consumer<SubCommandCreateAction> handler) {
        ActionExecutor.createSubCommand(handler, name, description, this.subCommandList);
        return this;
    }

    @Override
    protected void submit() {
        SlashCommandUtils.createLocalizedLists(jsonObject, localizedNameList, localizedDescriptionList);
        if (!subCommandList.isEmpty()) {
            SlashCommandUtils.createSubCommandsArray(jsonObject, subCommandList);
        }
        subCommandGroupList.add(new SubCommandGroup(jsonObject));
    }
}
